package ru.snatcher.hieronymus.other.di;

import dagger.Module;
import dagger.Provides;
import ru.snatcher.hieronymus.presenter.MainPresenterImpl;

/**
 * {@link ViewModule}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

@Module
class ViewModule {

	@Provides
	MainPresenterImpl provideLanguagePresenterImpl() {
		return new MainPresenterImpl();
	}
}