package ru.snatcher.hieronymus.other.di.view;

import dagger.Module;
import dagger.Provides;
import ru.snatcher.hieronymus.presenter.HistoryPresenterImpl;
import ru.snatcher.hieronymus.presenter.MainPresenterImpl;
import ru.snatcher.hieronymus.view.fragment.dashboard.HistoryFragmentView;
import ru.snatcher.hieronymus.view.fragment.main.TranslatorFragmentView;

/**
 * {@link ViewDynamicModule}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
@Module
public class ViewDynamicModule {

	private TranslatorFragmentView fTranslatorFragmentView;
	private HistoryFragmentView fHistoryFragmentView;

	public ViewDynamicModule(TranslatorFragmentView pTranslatorFragmentView) {
		this.fTranslatorFragmentView = pTranslatorFragmentView;
	}

	public ViewDynamicModule(final HistoryFragmentView pHistoryFragmentView) {
		fHistoryFragmentView = pHistoryFragmentView;
	}

	@Provides
	MainPresenterImpl provideMainPresenter() {
		return new MainPresenterImpl(fTranslatorFragmentView);
	}

	@Provides
	HistoryPresenterImpl provideHistoryPresenter() {
		return new HistoryPresenterImpl(fHistoryFragmentView);
	}

}
