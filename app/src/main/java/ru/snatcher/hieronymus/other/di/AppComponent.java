package ru.snatcher.hieronymus.other.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.snatcher.hieronymus.model.ModelImpl;
import ru.snatcher.hieronymus.presenter.BasePresenter;
import ru.snatcher.hieronymus.presenter.translator.TranslatorPresenter;

/**
 * {@link AppComponent}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
@Singleton
@Component(modules = {ModelModule.class, PresenterModule.class, ViewModule.class})
public interface AppComponent {

	void inject(ModelImpl dataRepository);

	void inject(BasePresenter basePresenter);

	void inject(TranslatorPresenter pMainPresenter);
}