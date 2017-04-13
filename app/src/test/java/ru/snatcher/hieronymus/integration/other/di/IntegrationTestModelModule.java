package ru.snatcher.hieronymus.integration.other.di;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.mockwebserver.MockWebServer;
import ru.snatcher.hieronymus.integration.other.IntegrationApiModule;
import ru.snatcher.hieronymus.model.api.ApiTranslateInterface;
import ru.snatcher.hieronymus.other.Constants;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * {@link IntegrationTestModelModule}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
@Module
public class IntegrationTestModelModule {

	@Provides
	@Singleton
	ApiTranslateInterface provideApiInterface(MockWebServer mockWebServer) {
		try {
			return new IntegrationApiModule().getApiInterface(mockWebServer);
		} catch (IOException e) {
			throw new RuntimeException("Can't create ApiInterface");
		}
	}

	@Provides
	@Singleton
	MockWebServer provideMockWebServer() {
		return new MockWebServer();
	}

	@Provides
	@Singleton
	@Named(Constants.UI_THREAD)
	Scheduler provideSchedulerUI() {
		return Schedulers.immediate();
	}

	@Provides
	@Singleton
	@Named(Constants.IO_THREAD)
	Scheduler provideSchedulerIO() {
		return Schedulers.immediate();
	}
}