package ru.snatcher.hieronymus.other.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.snatcher.hieronymus.model.ModelImplTest;
import ru.snatcher.hieronymus.presenter.TranslatorPresenterTest;
import ru.snatcher.hieronymus.presenter.mapper.TranslatorMapperTest;
import ru.snatcher.hieronymus.view.fragment.BaseFragmentTest;
import ru.snatcher.hieronymus.view.fragment.TranslatorFragmentTest;

/**
 * {@link TestComponent}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

@Singleton
@Component(modules = {ModelTestModule.class, PresenterTestModule.class, ViewTestModule.class, DataTestModule.class})
public interface TestComponent extends AppComponent {


	void inject(ModelImplTest dataRepositoryImplTest);

	void inject(TranslatorPresenterTest pTranslatorPresenterTest);

	void inject(TranslatorMapperTest pTranslatorMapperTest);

	void inject(TranslatorFragmentTest pTranslatorFragmentTest);

	void inject(BaseFragmentTest baseFragmentTest);
}
