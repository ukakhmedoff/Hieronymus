package ru.snatcher.hieronymus.view.fragment.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.snatcher.hieronymus.R;
import ru.snatcher.hieronymus.presenter.BasePresenter;
import ru.snatcher.hieronymus.view.fragment.BaseFragment;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class HistoryFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_history, container, false);
	}

	@Override
	protected BasePresenter getPresenter() {
		return null;
	}
}
