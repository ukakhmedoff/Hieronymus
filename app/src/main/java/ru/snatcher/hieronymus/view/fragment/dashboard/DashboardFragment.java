package ru.snatcher.hieronymus.view.fragment.dashboard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.snatcher.hieronymus.presenter.BasePresenter;
import ru.snatcher.hieronymus.view.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends BaseFragment {


	public DashboardFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		return new TextView(getActivity());
	}

	@Override
	protected BasePresenter getPresenter() {
		return null;
	}
}
