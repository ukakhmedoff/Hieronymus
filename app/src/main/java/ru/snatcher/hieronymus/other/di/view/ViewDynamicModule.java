package ru.snatcher.hieronymus.other.di.view;

import dagger.Module;
import dagger.Provides;
import ru.snatcher.hieronymus.presenter.TranslatorPresenter;
import ru.snatcher.hieronymus.view.fragment.translator.TranslatorFragmentView;

/**
 * {@link ViewDynamicModule}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
@Module
public class ViewDynamicModule {

	private TranslatorFragmentView fTranslatorFragmentView;

	public ViewDynamicModule(TranslatorFragmentView pTranslatorFragmentView) {
		this.fTranslatorFragmentView = pTranslatorFragmentView;
	}

	@Provides
	TranslatorPresenter provideMainPresenter() {
		return new TranslatorPresenter(fTranslatorFragmentView);
	}
}
