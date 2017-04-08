package ru.snatcher.hieronymus.other.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.snatcher.hieronymus.model.ModelImpl;
import ru.snatcher.hieronymus.presenter.BasePresenter;
import ru.snatcher.hieronymus.presenter.HistoryPresenterImpl;
import ru.snatcher.hieronymus.presenter.MainPresenterImpl;
import ru.snatcher.hieronymus.view.fragment.dashboard.HistoryFragment;

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

	void inject(MainPresenterImpl pMainPresenter);

	void inject(HistoryPresenterImpl pDashboardPresenter);

	void inject(HistoryFragment pHistoryFragment);
}