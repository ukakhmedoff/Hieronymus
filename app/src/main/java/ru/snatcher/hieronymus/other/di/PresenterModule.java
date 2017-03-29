package ru.snatcher.hieronymus.other.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.snatcher.hieronymus.model.Model;
import ru.snatcher.hieronymus.model.ModelImpl;
import rx.subscriptions.CompositeSubscription;

/**
 * {@link PresenterModule}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
@Module
class PresenterModule {

    @Provides
    @Singleton
    Model provideDataRepository() {
        return new ModelImpl();
    }

    @Provides
    CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }

}