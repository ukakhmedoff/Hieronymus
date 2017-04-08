package ru.snatcher.hieronymus.presenter;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.snatcher.hieronymus.db.Language;
import ru.snatcher.hieronymus.db.LanguageDao;
import ru.snatcher.hieronymus.db.Translate;
import ru.snatcher.hieronymus.db.TranslateDao;
import ru.snatcher.hieronymus.other.App;
import ru.snatcher.hieronymus.presenter.mapper.LanguageMapper;
import ru.snatcher.hieronymus.presenter.mapper.TranslateMapper;
import ru.snatcher.hieronymus.view.fragment.BaseView;
import ru.snatcher.hieronymus.view.fragment.main.TranslatorFragmentView;
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

	private TranslatorFragmentView fTranslatorFragmentView;

	private List<Language> fLanguages = new ArrayList<>();

	private Translate fTranslate;

	@Inject
	public MainPresenterImpl() {
	}

	public MainPresenterImpl(final TranslatorFragmentView pTranslatorFragmentView) {
		App.getAppComponent().inject(this);
		fTranslatorFragmentView = pTranslatorFragmentView;
	}

	@Override
	protected BaseView getView() {
		return fTranslatorFragmentView;
	}

	public void onCreateView(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if (savedInstanceState.getSerializable(BUNDLE_TRANSLATE_LIST_KEY) != null)
				fTranslate = (Translate) savedInstanceState.getSerializable(BUNDLE_TRANSLATE_LIST_KEY);
		}
		if (isTranslatesNotEmpty()) fTranslatorFragmentView.showTranslate(fTranslate);
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
				fTranslatorFragmentView.showError(e.getLocalizedMessage());
			}

			@Override
			public void onNext(List<Language> pLanguages) {
				if (pLanguages != null) {
					fLanguages = pLanguages;
					fTranslatorFragmentView.showLanguagesList(fLanguages);
					fTranslatorFragmentView.saveLanguages(fLanguages);
				} else {
					fTranslatorFragmentView.showError("Check your Internet connection!");
				}
			}
		});
		addSubscription(lvSubscription);
	}

	@Override
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
									fTranslatorFragmentView.saveTranslate(fTranslate);
								} else {
									fTranslatorFragmentView.showError("Что-то пошло не так :(, скорее всего что-то не так с ключом!");
								}
							}
						});
		addSubscription(lvSubscription);
	}

	@Override
	public void saveTranslate(final Translate pTranslate, App pApp) {

		TranslateDao lvTranslateDao = pApp.getDaoSession().getTranslateDao();
		lvTranslateDao.insert(pTranslate);

	}

	@Override
	public void saveLanguages(final List<Language> pLanguages, final App pApp) {
		LanguageDao lvLanguageDao = pApp.getDaoSession().getLanguageDao();
		for (Language lvLanguage : pLanguages) lvLanguageDao.insert(lvLanguage);
	}
}