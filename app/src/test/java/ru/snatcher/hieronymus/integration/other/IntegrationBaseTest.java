package ru.snatcher.hieronymus.integration.other;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import ru.snatcher.hieronymus.BuildConfig;
import ru.snatcher.hieronymus.integration.other.di.IntegrationTestComponent;
import ru.snatcher.hieronymus.other.App;
import ru.snatcher.hieronymus.other.TestConstants;
import ru.snatcher.hieronymus.other.TestUtils;

/**
 * {@link IntegrationBaseTest}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
@RunWith(RobolectricTestRunner.class)
@Config(application = IntegrationTestApp.class,
		constants = BuildConfig.class,
		sdk = 21)
@Ignore
public class IntegrationBaseTest {

	public IntegrationTestComponent component;
	public TestUtils testUtils;

	@Inject
	protected MockWebServer mockWebServer;

	@Before
	public void setUp() throws Exception {
		component = (IntegrationTestComponent) App.getComponent();
		testUtils = new TestUtils();
		component.inject(this);
	}

	@After
	public void tearDown() throws Exception {
		mockWebServer.shutdown();
	}

	protected void setErrorAnswerWebServer() {
		mockWebServer.setDispatcher(new Dispatcher() {
			@Override
			public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
				return new MockResponse().setResponseCode(500);
			}
		});
	}

	protected void setCustomAnswer(boolean enableBranches, boolean enableContributors) {
		mockWebServer.setDispatcher(new Dispatcher() {
			@Override
			public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
				if (request.getPath().equals("/getLangs?key=" + TestConstants.TEST_KEY + "&ui=ru")) {
					return new MockResponse().setResponseCode(200)
							.setBody(testUtils.readString("json/langs.json"));
				} else if (request.getPath().equals("/translate?key=" + TestConstants.TEST_KEY + "&text=" + TestConstants.TEST_TO_TRANSLATE + "&lang=" + TestConstants.TEST_LANGS)) {
					return new MockResponse().setResponseCode(200)
							.setBody(testUtils.readString("json/translate.json"));
				}
				return new MockResponse().setResponseCode(404);
			}
		});

	}

}
