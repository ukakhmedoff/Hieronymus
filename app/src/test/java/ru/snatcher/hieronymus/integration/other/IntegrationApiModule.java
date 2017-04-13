package ru.snatcher.hieronymus.integration.other;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import ru.snatcher.hieronymus.model.api.ApiTranslateInterface;
import ru.snatcher.hieronymus.model.api.ApiTranslateModule;
import ru.snatcher.hieronymus.other.TestConstants;
import ru.snatcher.hieronymus.other.TestUtils;

/**
 * {@link IntegrationApiModule}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class IntegrationApiModule {

	public ApiTranslateInterface getApiInterface(MockWebServer mockWebServer) throws IOException {
		mockWebServer.start();
		TestUtils testUtils = new TestUtils();
		final Dispatcher dispatcher = new Dispatcher() {

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
		};

		mockWebServer.setDispatcher(dispatcher);
		HttpUrl baseUrl = mockWebServer.url("/");
		return ApiTranslateModule.getApiInterfaceInstance();
	}
}

