package ru.snatcher.hieronymus.model;

import javax.inject.Inject;
import javax.inject.Named;

import ru.snatcher.hieronymus.db.Language;
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
		App.getAppComponent().inject(this);
		fSchedulersTransformer = o -> ((Observable) o).subscribeOn(fIoThread)
				.observeOn(fUiThread)
				.unsubscribeOn(fIoThread);
	}

	@Override
	public Observable<LanguageDTO> getLangs(String pKey) {
		return fApiInterface
				.getLangs(pKey, "ru")
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

	@SuppressWarnings("unchecked")
	private <T> Observable.Transformer<T, T> applySchedulers() {
		return (Observable.Transformer<T, T>) fSchedulersTransformer;
	}
}
