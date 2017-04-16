package ru.snatcher.hieronymus.other.di;

import dagger.Module;
import dagger.Provides;
import ru.snatcher.hieronymus.presenter.dashboard.HistoryPresenter;
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
	TranslatorPresenter provideTranslatorPresenterImpl() {
		return new TranslatorPresenter();
	}

	@Provides
	HistoryPresenter provideHistoryPresenterImpl() {
		return new HistoryPresenter();
	}
}