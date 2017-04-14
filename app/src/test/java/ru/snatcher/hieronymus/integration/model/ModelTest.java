package ru.snatcher.hieronymus.integration.model;


import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import ru.snatcher.hieronymus.integration.other.IntegrationBaseTest;
import ru.snatcher.hieronymus.model.Model;
import ru.snatcher.hieronymus.model.api.ApiTranslateInterface;
import ru.snatcher.hieronymus.model.entity.LanguageDTO;
import ru.snatcher.hieronymus.model.entity.TranslateDTO;
import ru.snatcher.hieronymus.other.TestConstants;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;

/**
 * {@link ModelTest}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class ModelTest extends IntegrationBaseTest {

	@Inject
	protected ApiTranslateInterface apiInterface;

	@Inject
	protected Model model;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		component.inject(this);
	}

	@Test
	public void testGetLanguageList() {

		TestSubscriber<LanguageDTO> testSubscriber = new TestSubscriber<>();
		model.getLangs(TestConstants.TEST_KEY, "ru").subscribe(testSubscriber);

		testSubscriber.assertNoErrors();
		testSubscriber.assertValueCount(1);

		LanguageDTO actual = testSubscriber.getOnNextEvents().get(0);

		assertEquals(93, actual.getLangs().size());
	}

	@Test
	public void testGetTranslate() {

		TestSubscriber<TranslateDTO> testSubscriber = new TestSubscriber<>();
		model.getTranslatedText(TestConstants.TEST_KEY, TestConstants.TEST_TO_TRANSLATE, TestConstants.TEST_LANGS).subscribe(testSubscriber);

		testSubscriber.assertNoErrors();
		testSubscriber.assertValueCount(1);

		TranslateDTO actual = testSubscriber.getOnNextEvents().get(0);

		assertEquals(1, actual.getText().size());

	}
}
