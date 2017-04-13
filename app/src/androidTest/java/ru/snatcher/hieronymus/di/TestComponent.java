package ru.snatcher.hieronymus.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.snatcher.hieronymus.TranslatorFragmentTest;
import ru.snatcher.hieronymus.other.di.AppComponent;
import ru.snatcher.hieronymus.other.di.PresenterModule;
import ru.snatcher.hieronymus.other.di.ViewModule;
import ru.snatcher.hieronymus.tools.ApiConfig;

/**
 * {@link TestComponent}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
@Singleton
@Component(modules = {DataTestModule.class,
		TestModelModule.class,
		PresenterModule.class,
		ViewModule.class})
public interface TestComponent extends AppComponent {

	void inject(ApiConfig apiConfig);

	void inject(TranslatorFragmentTest pTranslatorFragmentTests);

}
