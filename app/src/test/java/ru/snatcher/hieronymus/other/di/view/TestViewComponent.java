package ru.snatcher.hieronymus.other.di.view;

import javax.inject.Singleton;

import dagger.Component;
import ru.snatcher.hieronymus.view.fragment.TranslatorFragmentTest;

/**
 * {@link TestViewComponent}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

@Singleton
@Component(modules = {TestViewDynamicModule.class})
public interface TestViewComponent extends ViewComponent {

	void inject(TranslatorFragmentTest in);

}