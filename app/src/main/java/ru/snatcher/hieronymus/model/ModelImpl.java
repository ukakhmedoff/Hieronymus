package ru.snatcher.hieronymus.model;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ru.snatcher.hieronymus.model.api.ApiTranslateInterface;
import ru.snatcher.hieronymus.model.db.Language;
import ru.snatcher.hieronymus.model.db.Translate;
import ru.snatcher.hieronymus.model.db.TranslateDao;
import ru.snatcher.hieronymus.model.entity.LanguageDTO;
import ru.snatcher.hieronymus.model.entity.TranslateDTO;
import ru.snatcher.hieronymus.other.App;
import ru.snatcher.hieronymus.other.Constants;
import rx.Observable;
import rx.Scheduler;

import static ru.snatcher.hieronymus.other.Utils.getDaoSession;

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
	public final Observable<LanguageDTO> getRemoteLangs(String pKey, String pUiLang) {
		return fApiInterface
				.getLangs(pKey, pUiLang)
				.compose(applySchedulers());
	}

	@Override
	public final Observable<TranslateDTO> getTranslatedText(String pKey, final String pTextToTranslate, final String pLangs) {
		return fApiInterface
				.getTranslatedText(pKey, pTextToTranslate, pLangs)
				.compose(applySchedulers());
	}

	@Override
	public final void saveTranslate(final Translate pTranslate, final Context pContext) {
		getDaoSession(pContext).getTranslateDao().insertOrReplace(pTranslate);
	}

	@Override
	public final void saveLanguages(final List<Language> pLanguages, final Context pContext) {
		for (Language lvLanguage : pLanguages)
			getDaoSession(pContext).getLanguageDao().insertOrReplace(lvLanguage);
	}

	@Override
	public final List<Translate> getTranslates(final boolean pFavourite, final Context pContext) {
		TranslateDao lvTranslateDao = getDaoSession(pContext).getTranslateDao();

		if (pFavourite)
			return lvTranslateDao.queryBuilder().where(TranslateDao.Properties.IsBookmark.eq(true)).orderAsc(TranslateDao.Properties.TranslatedText).list();
		else return lvTranslateDao.loadAll();
	}

	@Override
	public final List<Language> getLocalLanguages(final Context pContext) {
		return getDaoSession(pContext).getLanguageDao().loadAll();
	}

	@Override
	public final boolean getTranslateIsFavourite(final Translate pTranslate, final Context pContext) {
		TranslateDao lvTranslateDao = getDaoSession(pContext).getTranslateDao();

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
