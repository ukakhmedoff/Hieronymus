package ru.snatcher.hieronymus.tools;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import ru.snatcher.hieronymus.model.api.ApiTranslateInterface;
import ru.snatcher.hieronymus.model.entity.LanguageDTO;
import ru.snatcher.hieronymus.model.entity.TranslateDTO;
import rx.Observable;

import static org.mockito.Mockito.when;

/**
 * {@link ApiConfig}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

public class ApiConfig {

	@Inject
	ApiTranslateInterface apiInterface;

	@Inject
	LanguageDTO fLanguageDTO;

	@Inject
	TranslateDTO fTranslateDTO;

	@Inject
	public ApiConfig() {
	}

	public void setCorrectAnswer() {

		when(apiInterface.getLangs(TestConstants.TEST_KEY, "ru"))
				.thenReturn(getObservableWithDelay((fLanguageDTO)));

		when(apiInterface.getTranslatedText(TestConstants.TEST_KEY, TestConstants.TEST_TO_TRANSLATE, TestConstants.TEST_LANGS))
				.thenReturn(getObservableWithDelay(fTranslateDTO));


	}

	public void setErrorAnswer() {
		when(apiInterface.getLangs(TestConstants.TEST_KEY, "ru"))
				.thenReturn(getErrorObservableWithDelay());

		when(apiInterface.getTranslatedText(TestConstants.TEST_KEY, TestConstants.TEST_TO_TRANSLATE, TestConstants.TEST_LANGS))
				.thenReturn(getErrorObservableWithDelay());
	}

	private <T> Observable<T> getObservableWithDelay(final T value) {
		return Observable.timer(TestConstants.API_DELAY, TimeUnit.MILLISECONDS)
				.concatMap(aLong -> Observable.just(value));
	}

	private <T> Observable<T> getErrorObservableWithDelay() {
		return Observable.timer(TestConstants.API_DELAY, TimeUnit.MILLISECONDS)
				.concatMap(aLong -> Observable.error(new Throwable(TestConstants.TEST_ERROR)));
	}

}