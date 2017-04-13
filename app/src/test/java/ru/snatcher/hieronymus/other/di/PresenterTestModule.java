package ru.snatcher.hieronymus.other.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.snatcher.hieronymus.model.Model;
import rx.subscriptions.CompositeSubscription;

import static org.mockito.Mockito.mock;

/**
 * {@link PresenterTestModule}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

@Module
public class PresenterTestModule {

	@Provides
	@Singleton
	Model provideDataRepository() {
		return mock(Model.class);
	}

	@Provides
	CompositeSubscription provideCompositeSubscription() {
		return new CompositeSubscription();
	}

}
