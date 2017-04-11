package ru.snatcher.hieronymus.other.di.view;

import javax.inject.Singleton;

import dagger.Component;
import ru.snatcher.hieronymus.view.fragment.translator.TranslatorFragment;

/**
 * {@link ViewComponent}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
@Singleton
@Component(modules = {ViewDynamicModule.class})
public interface ViewComponent {

	void inject(TranslatorFragment pTranslatorFragment);

}
