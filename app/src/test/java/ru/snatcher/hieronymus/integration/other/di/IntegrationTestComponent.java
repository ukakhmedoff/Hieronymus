package ru.snatcher.hieronymus.integration.other.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.snatcher.hieronymus.integration.model.ModelTest;
import ru.snatcher.hieronymus.integration.other.IntegrationBaseTest;
import ru.snatcher.hieronymus.integration.view.TranslatorFragmentTest;
import ru.snatcher.hieronymus.other.di.AppComponent;
import ru.snatcher.hieronymus.other.di.DataTestModule;
import ru.snatcher.hieronymus.other.di.PresenterModule;
import ru.snatcher.hieronymus.other.di.ViewModule;
import ru.snatcher.hieronymus.presenter.TranslatorPresenterTest;

/**
 * {@link IntegrationTestComponent}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

@Singleton
@Component(modules = {IntegrationTestModelModule.class, PresenterModule.class, ViewModule.class, DataTestModule.class})
public interface IntegrationTestComponent extends AppComponent {

	void inject(ModelTest modelTest);

	void inject(TranslatorPresenterTest pTranslatorPresenterTest);

	void inject(TranslatorFragmentTest pTranslatorFragmentTest);

	void inject(IntegrationBaseTest integrationBaseTest);

}
