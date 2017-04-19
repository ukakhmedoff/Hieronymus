package ru.snatcher.hieronymus.model;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import ru.snatcher.hieronymus.model.api.ApiTranslateInterface;
import ru.snatcher.hieronymus.model.entity.LanguageDTO;
import ru.snatcher.hieronymus.model.entity.TranslateDTO;
import ru.snatcher.hieronymus.other.BaseTest;
import ru.snatcher.hieronymus.other.TestConstants;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * {@link ModelImplTest}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class ModelImplTest extends BaseTest {

	@Inject
	protected ApiTranslateInterface apiInterface;

	private Model model;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		component.inject(this);
		model = new ModelImpl();
	}

	@Test
	public void testGetLanguages() {
		LanguageDTO lvLanguageDTO = testUtils.getGson().fromJson(testUtils.readString("json/langs.json"), LanguageDTO.class);

		when(apiInterface.getLangs(TestConstants.TEST_KEY, "ru")).thenReturn(Observable.just(lvLanguageDTO));

		TestSubscriber<LanguageDTO> testSubscriber = new TestSubscriber<>();
		model.getRemoteLangs(TestConstants.TEST_KEY, "ru").subscribe(testSubscriber);

		testSubscriber.assertNoErrors();
		testSubscriber.assertValueCount(1);

		LanguageDTO actual = testSubscriber.getOnNextEvents().get(0);

		assertEquals(93, actual.getLangs().size());
	}


	@Test
	public void testGetTranslate() {
		TranslateDTO lvTranslateDTOs = testUtils.getGson().fromJson(testUtils.readString("json/translate.json"), TranslateDTO.class);

		when(apiInterface.getTranslatedText(TestConstants.TEST_KEY, TestConstants.TEST_TO_TRANSLATE, TestConstants.TEST_LANGS)).thenReturn(Observable.just(lvTranslateDTOs));


		TestSubscriber<TranslateDTO> testSubscriber = new TestSubscriber<>();
		model.getTranslatedText(TestConstants.TEST_KEY, TestConstants.TEST_TO_TRANSLATE, TestConstants.TEST_LANGS).subscribe(testSubscriber);

		testSubscriber.assertNoErrors();
		testSubscriber.assertValueCount(1);

		TranslateDTO actual = testSubscriber.getOnNextEvents().get(0);

		assertEquals(1, actual.getText().size());

	}
}