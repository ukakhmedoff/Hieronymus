package ru.snatcher.hieronymus.presenter.translator;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.snatcher.hieronymus.broadcast.NetworkChangeReceiver;
import ru.snatcher.hieronymus.db.Language;
import ru.snatcher.hieronymus.db.Translate;
import ru.snatcher.hieronymus.other.App;
import ru.snatcher.hieronymus.presenter.BasePresenter;
import ru.snatcher.hieronymus.presenter.mapper.LanguageMapper;
import ru.snatcher.hieronymus.presenter.mapper.TranslateMapper;
import ru.snatcher.hieronymus.view.BaseView;
import ru.snatcher.hieronymus.view.fragment.translator.TranslatorFragmentView;
import rx.Observer;
import rx.Subscription;

/**
 * {@link TranslatorPresenter} is presenter for {@link TranslatorFragmentView}
 * <p>
 * Here we get view, langs, translates. Map these. Save translate, langs. And check network connection change.
 *
 * @author Usman Akhmedoff.
 * @version 1.2
 */
public class TranslatorPresenter extends BasePresenter implements NetworkChangeReceiver.ConnectionReceiverListener {

	@Inject
	LanguageMapper fLanguageMapper;

	@Inject
	TranslateMapper fTranslateMapper;

	private TranslatorFragmentView fTranslatorFragmentView;

	private List<Language> fLanguages = new ArrayList<>();

	private Translate fTranslate;

	@Inject
	public TranslatorPresenter() {
	}

	public TranslatorPresenter(final TranslatorFragmentView pTranslatorFragmentView) {
		App.getComponent().inject(this);
		fTranslatorFragmentView = pTranslatorFragmentView;
	}

	@Override
	public BaseView getView() {
		return fTranslatorFragmentView;
	}

	public void onCreateView(Bundle pSavedInstanceState) {
		if (pSavedInstanceState != null)
			if (pSavedInstanceState.getSerializable(BUNDLE_TRANSLATE_LIST_KEY) != null)
				fTranslate = (Translate) pSavedInstanceState.getSerializable(BUNDLE_TRANSLATE_LIST_KEY);

		if (isTranslatesNotEmpty()) fTranslatorFragmentView.showTranslate(fTranslate);
	}

	private boolean isTranslatesNotEmpty() {
		return fTranslate != null && fLanguages != null && !fLanguages.isEmpty();
	}

	public String onLanguageSelected(Language pLanguage) {
		return fModel.getLangKey(pLanguage);
	}

	private void getRemoteLanguages(String pKey, String pUiLang) {
		Subscription lvSubscription = fModel.getRemoteLangs(pKey, pUiLang).map(fLanguageMapper).subscribe(new Observer<List<Language>>() {

			@Override
			public void onCompleted() {
			}

			@Override
			public void onError(Throwable e) {
				fTranslatorFragmentView.showError(e.getLocalizedMessage());
			}

			@Override
			public void onNext(List<Language> pLanguages) {
				if (pLanguages != null) {
					fLanguages = pLanguages;
					fTranslatorFragmentView.showLanguageList(fLanguages);
				} else {
					fTranslatorFragmentView.showError("Check your Internet connection!");
				}

			}
		});
		addSubscription(lvSubscription);
	}

	public void getTranslatesRemote(final String pKey, final String pToTranslateText, String pLangs) {
		Subscription lvSubscription =
				fModel
						.getTranslatedText(pKey, pToTranslateText, pLangs)
						.map(fTranslateMapper)
						.subscribe(new Observer<Translate>() {

							@Override
							public void onCompleted() {
							}

							@Override
							public void onError(Throwable e) {
								fTranslatorFragmentView.showError(e.getLocalizedMessage());
							}

							@Override
							public void onNext(Translate pTranslate) {
								if (pTranslate != null) {
									fTranslate = pTranslate;
									fTranslatorFragmentView.showTranslate(fTranslate);
								} else
									fTranslatorFragmentView.showError("Что-то пошло не так :(, скорее всего что-то не так с ключом!");
							}
						});
		addSubscription(lvSubscription);
	}

	public void saveLanguages(final List<Language> pLanguages, final App pApp) {
		fModel.saveLanguages(pLanguages, pApp);
	}

	private void getLocalLanguages(App pApp) {
		fTranslatorFragmentView.showLanguageList(fModel.getLocalLanguages(pApp));
	}

	public void getLangs(String pKey, String pUiLang, App pApp) {
		if (NetworkChangeReceiver.isConnected()) getRemoteLanguages(pKey, pUiLang);
		else getLocalLanguages(pApp);
	}

	public void onNetworkConnectionChanged(final boolean isConnected) {
		if (isConnected) fTranslatorFragmentView.getLangs();
	}

	void onSaveInstanceState(Bundle pOutState) {

	}
}