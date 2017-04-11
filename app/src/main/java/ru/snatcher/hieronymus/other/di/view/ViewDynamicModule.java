package ru.snatcher.hieronymus.other.di.view;

import dagger.Module;
import dagger.Provides;
import ru.snatcher.hieronymus.presenter.translator.TranslatorPresenterImpl;
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
	TranslatorPresenterImpl provideMainPresenter() {
		return new TranslatorPresenterImpl(fTranslatorFragmentView);
	}


}
