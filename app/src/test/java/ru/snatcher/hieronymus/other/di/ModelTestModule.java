package ru.snatcher.hieronymus.other.di;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.snatcher.hieronymus.model.api.ApiTranslateInterface;
import ru.snatcher.hieronymus.other.Constants;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;

/**
 * {@link ModelTestModule}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

@Module
public class ModelTestModule {

	@Provides
	@Singleton
	ApiTranslateInterface provideApiInterface() {
		return mock(ApiTranslateInterface.class);
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