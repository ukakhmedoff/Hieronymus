package ru.snatcher.hieronymus.other.di.view;

import dagger.Module;
import dagger.Provides;
import ru.snatcher.hieronymus.presenter.MainPresenterImpl;
import ru.snatcher.hieronymus.view.fragment.MainFragmentView;

/**
 * {@link ViewDynamicModule}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
@Module
public class ViewDynamicModule {

    private MainFragmentView view;

    public ViewDynamicModule(MainFragmentView view) {
        this.view = view;
    }

    @Provides
    MainPresenterImpl provideMainPresenter() {
        return new MainPresenterImpl(view);
    }
}
