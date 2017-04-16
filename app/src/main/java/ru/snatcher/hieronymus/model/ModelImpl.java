package ru.snatcher.hieronymus.model;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ru.snatcher.hieronymus.db.Language;
import ru.snatcher.hieronymus.db.Translate;
import ru.snatcher.hieronymus.db.TranslateDao;
import ru.snatcher.hieronymus.model.api.ApiTranslateInterface;
import ru.snatcher.hieronymus.model.entity.LanguageDTO;
import ru.snatcher.hieronymus.model.entity.TranslateDTO;
import ru.snatcher.hieronymus.other.App;
import ru.snatcher.hieronymus.other.Constants;
import rx.Observable;
import rx.Scheduler;

/**
 * {@link ModelImpl} is implementation for {@link Model}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class ModelImpl implements Model {
	private final Observable.Transformer fSchedulersTransformer;

	@Inject
	ApiTranslateInterface fApiInterface;

	@Inject
	@Named(Constants.UI_THREAD)
	Scheduler fUiThread;

	@Inject
	@Named(Constants.IO_THREAD)
	Scheduler fIoThread;

	public ModelImpl() {
		App.getComponent().inject(this);
		fSchedulersTransformer = o -> ((Observable) o).subscribeOn(fIoThread)
				.observeOn(fUiThread)
				.unsubscribeOn(fIoThread);
	}

	@Override
	public Observable<LanguageDTO> getRemoteLangs(String pKey, String pUiLang) {
		return fApiInterface
				.getLangs(pKey, pUiLang)
				.compose(applySchedulers());
	}

	@Override
	public Observable<TranslateDTO> getTranslatedText(String pKey, final String pTextToTranslate, final String pLangs) {
		return fApiInterface
				.getTranslatedText(pKey, pTextToTranslate, pLangs)
				.compose(applySchedulers());
	}

	@Override
	public String getLangKey(Language pLanguage) {
		return pLanguage.getLangKey();
	}

	@Override
	public void saveTranslate(final Translate pTranslate, final App pApp) {
		pApp.getDaoSession().getTranslateDao().insertOrReplace(pTranslate);
	}

	@Override
	public void saveLanguages(final List<Language> pLanguages, final App pApp) {
		for (Language lvLanguage : pLanguages)
			pApp.getDaoSession().getLanguageDao().insertOrReplace(lvLanguage);
	}

	@Override
	public List<Translate> getTranslates(final boolean pFavourite, final App pApp) {
		TranslateDao lvTranslateDao = pApp.getDaoSession().getTranslateDao();

		if (pFavourite)
			return lvTranslateDao.queryBuilder().where(TranslateDao.Properties.IsBookmark.eq(true)).orderAsc(TranslateDao.Properties.TranslatedText).list();
		else return lvTranslateDao.loadAll();
	}

	@Override
	public List<Language> getLocalLanguages(final App pApp) {
		return pApp.getDaoSession().getLanguageDao().loadAll();
	}

	@Override
	public boolean getTranslateIsFavourite(final Translate pTranslate, final App pApp) {
		TranslateDao lvTranslateDao = pApp.getDaoSession().getTranslateDao();

		Translate lvTranslate = lvTranslateDao.queryBuilder()
				.where(TranslateDao.Properties.TranslatedText.eq(pTranslate.getTranslatedText())).unique();
		if (lvTranslate != null)
			return lvTranslate.getIsBookmark();
		else return false;
	}

	@SuppressWarnings("unchecked")
	private <T> Observable.Transformer<T, T> applySchedulers() {
		return (Observable.Transformer<T, T>) fSchedulersTransformer;
	}
}
