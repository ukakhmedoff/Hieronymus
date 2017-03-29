package ru.snatcher.hieronymus.other.di;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.snatcher.hieronymus.model.api.ApiTranslateInterface;
import ru.snatcher.hieronymus.model.api.ApiTranslateModule;
import ru.snatcher.hieronymus.other.Constants;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * {@link ModelModule}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

@Module
class ModelModule {

    @Provides
    @Singleton
    ApiTranslateInterface provideApiInterface() {
        return ApiTranslateModule.getApiInterfaceInstance();
    }

    @Provides
    @Singleton
    @Named(Constants.UI_THREAD)
    Scheduler provideSchedulerUI() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    @Named(Constants.IO_THREAD)
    Scheduler provideSchedulerIO() {
        return Schedulers.io();
    }

}