package ru.snatcher.hieronymus.model.api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import ru.snatcher.hieronymus.model.entity.LanguageDTO;
import ru.snatcher.hieronymus.model.entity.TranslateDTO;
import ru.snatcher.hieronymus.other.BaseTest;
import ru.snatcher.hieronymus.other.TestConstants;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * {@link ApiInterfaceTest}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class ApiInterfaceTest extends BaseTest {

	private MockWebServer server;
	private ApiTranslateInterface apiInterface;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		server = new MockWebServer();
		server.start();
		final Dispatcher dispatcher = new Dispatcher() {

			@Override
			public MockResponse dispatch(RecordedRequest request) throws InterruptedException {

				if (request.getPath().equals("/getRemoteLangs?key=" + TestConstants.TEST_KEY + "&ui=ru")) {
					return new MockResponse().setResponseCode(200)
							.setBody(testUtils.readString("json/langs.json"));
				} else if (request.getPath().equals("/translate?key=" + TestConstants.TEST_KEY + "&text=" + TestConstants.TEST_TO_TRANSLATE + "&lang=" + TestConstants.TEST_LANGS)) {
					return new MockResponse().setResponseCode(200)
							.setBody(testUtils.readString("json/translate.json"));
				}
				return new MockResponse().setResponseCode(404);
			}
		};

		server.setDispatcher(dispatcher);
		apiInterface = ApiTranslateModule.getApiInterfaceInstance();
	}


	@Test
	public void testGetLanguages() throws Exception {

		TestSubscriber<LanguageDTO> testSubscriber = new TestSubscriber<>();
		apiInterface.getLangs(TestConstants.TEST_KEY, "ru").subscribe(testSubscriber);

		testSubscriber.assertNoErrors();
		testSubscriber.assertValueCount(1);

		LanguageDTO actual = testSubscriber.getOnNextEvents().get(0);

		assertEquals(93, actual.getLangs().size());
	}

	@Test
	public void testGetLangsIncorrect() throws Exception {
		try {
			apiInterface.getLangs("IncorrectRequest", "ru").subscribe();
			fail();
		} catch (Exception expected) {
			assertEquals("HTTP 403 FORBIDDEN", expected.getMessage());
		}
	}


	@Test
	public void testGetTranslate() {
		TestSubscriber<TranslateDTO> testSubscriber = new TestSubscriber<>();
		apiInterface.getTranslatedText(TestConstants.TEST_KEY, TestConstants.TEST_TO_TRANSLATE, TestConstants.TEST_LANGS).subscribe(testSubscriber);

		testSubscriber.assertNoErrors();
		testSubscriber.assertValueCount(1);

		TranslateDTO actual = testSubscriber.getOnNextEvents().get(0);

		assertEquals(1, actual.getText().size());
	}

	@Test
	public void testGetTranslateIncorrect() throws Exception {
		try {
			apiInterface.getTranslatedText("BBB", "AAA", "asdas").subscribe();
			fail();
		} catch (Exception expected) {
			assertEquals("HTTP 403 Forbidden", expected.getMessage());
		}
	}

	@After
	public void tearDown() throws Exception {
		server.shutdown();
	}
}
