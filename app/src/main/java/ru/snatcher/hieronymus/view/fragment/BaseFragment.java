package ru.snatcher.hieronymus.view.fragment;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import ru.snatcher.hieronymus.presenter.BasePresenter;
import ru.snatcher.hieronymus.view.BaseView;

/**
 * {@link BaseFragment}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public abstract class BaseFragment extends Fragment implements BaseView {

	protected abstract BasePresenter getPresenter();

	public void showError(final String error) {
		Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onStop() {
		super.onStop();
		if (getPresenter() != null) getPresenter().onStop();
	}

}