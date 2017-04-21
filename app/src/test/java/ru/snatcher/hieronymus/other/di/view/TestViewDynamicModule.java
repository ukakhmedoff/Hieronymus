package ru.snatcher.hieronymus.other.di.view;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.snatcher.hieronymus.presenter.TranslatorPresenter;

import static org.mockito.Mockito.mock;

/**
 * {@link TestViewDynamicModule}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

@Module
public class TestViewDynamicModule {

	@Provides
	@Singleton
	TranslatorPresenter provideRepoListPresenter() {
		return mock(TranslatorPresenter.class);
	}
}
