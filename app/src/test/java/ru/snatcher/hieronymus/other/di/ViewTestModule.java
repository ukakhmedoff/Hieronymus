package ru.snatcher.hieronymus.other.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.snatcher.hieronymus.presenter.TranslatorPresenter;

import static org.mockito.Mockito.mock;

/**
 * {@link ViewTestModule}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

@Module
public class ViewTestModule {

	@Provides
	@Singleton
	TranslatorPresenter provideTranslatorPresenter() {
		return mock(TranslatorPresenter.class);
	}

}
