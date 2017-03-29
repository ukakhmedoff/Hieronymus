package ru.snatcher.hieronymus.view.fragment;

import android.support.v4.app.Fragment;

import ru.snatcher.hieronymus.presenter.Presenter;

/**
 * {@link BaseFragment}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public abstract class BaseFragment extends Fragment implements BaseView {


    protected abstract Presenter getPresenter();

    @Override
    public void onStop() {
        super.onStop();
        if (getPresenter() != null) {
            getPresenter().onStop();
        }
    }
}