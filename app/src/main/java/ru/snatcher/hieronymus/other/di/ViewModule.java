package ru.snatcher.hieronymus.other.di;

import dagger.Module;
import dagger.Provides;
import ru.snatcher.hieronymus.presenter.translator.TranslatorPresenter;

/**
 * {@link ViewModule}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
@Module
public class ViewModule {

	@Provides
	TranslatorPresenter provideLanguagePresenterImpl() {
		return new TranslatorPresenter();
	}
}