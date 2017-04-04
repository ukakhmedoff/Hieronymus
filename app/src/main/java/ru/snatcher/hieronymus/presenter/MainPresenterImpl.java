package ru.snatcher.hieronymus.presenter;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.snatcher.hieronymus.other.App;
import ru.snatcher.hieronymus.presenter.mapper.LanguageMapper;
import ru.snatcher.hieronymus.presenter.mapper.TranslateMapper;
import ru.snatcher.hieronymus.presenter.vo.Language;
import ru.snatcher.hieronymus.presenter.vo.Translate;
import ru.snatcher.hieronymus.view.fragment.BaseView;
import ru.snatcher.hieronymus.view.fragment.MainFragmentView;
import rx.Observer;
import rx.Subscription;

/**
 * {@link MainPresenterImpl} is implementation for {@link MainPresenter}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class MainPresenterImpl extends BasePresenter implements MainPresenter {

	private static final String BUNDLE_TRANSLATE_LIST_KEY = "BUNDLE_TRANSLATE_LIST_KEY";

	@Inject
	LanguageMapper fLanguageMapper;

	@Inject
	TranslateMapper fTranslateMapper;

	private MainFragmentView fMainFragmentView;

	private List<Language> fLanguages = new ArrayList<>();

	private Translate fTranslate;

	@Inject
	public MainPresenterImpl() {
	}

	public MainPresenterImpl(final MainFragmentView pMainFragmentView) {
		App.getAppComponent().inject(this);
		fMainFragmentView = pMainFragmentView;
	}

	@Override
	protected BaseView getView() {
		return fMainFragmentView;
	}

	public void onCreateView(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if (savedInstanceState.getSerializable(BUNDLE_TRANSLATE_LIST_KEY) != null)
				fTranslate = (Translate) savedInstanceState.getSerializable(BUNDLE_TRANSLATE_LIST_KEY);
		}
		if (isTranslatesNotEmpty()) fMainFragmentView.showTranslate(fTranslate);
	}

	private boolean isTranslatesNotEmpty() {
		return fTranslate != null && fLanguages != null && !fLanguages.isEmpty();
	}

	@Override
	public void onStop() {
	}

	@Override
	public String onLanguageSelected(Language pLanguage) {
		return fModel.getLangKey(pLanguage);
	}

	@Override
	public void getLangs(String pKey) {
		Subscription lvSubscription = fModel.getLangs(pKey).map(fLanguageMapper).subscribe(new Observer<List<Language>>() {

			@Override
			public void onCompleted() {
			}

			@Override
			public void onError(Throwable e) {
				fMainFragmentView.showError(e.getLocalizedMessage());
			}

			@Override
			public void onNext(List<Language> pLanguages) {
				if (pLanguages != null) {
					fLanguages = pLanguages;
					fMainFragmentView.showLanguagesList(fLanguages);
				} else {
					fMainFragmentView.showError("Check your Internet connection!");
				}
			}
		});
		addSubscription(lvSubscription);
	}

	@Override
	public void getTranslates(final String pKey, final String pToTranslateText, String pLangs) {
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
								fMainFragmentView.showError(e.getLocalizedMessage());
							}

							@Override
							public void onNext(Translate pTranslate) {
								if (pTranslate != null) {
									fTranslate = pTranslate;
									fMainFragmentView.showTranslate(fTranslate);
									fMainFragmentView.saveTranslate(fTranslate);
								} else {
									fMainFragmentView.showError("Что-то пошло не так :(, скорее всего что-то не так с ключом!");
								}
							}
						});
		addSubscription(lvSubscription);
	}
}