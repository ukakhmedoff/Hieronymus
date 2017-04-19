package ru.snatcher.hieronymus.view.fragment.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.snatcher.hieronymus.R;
import ru.snatcher.hieronymus.db.Translate;
import ru.snatcher.hieronymus.other.App;
import ru.snatcher.hieronymus.presenter.BasePresenter;
import ru.snatcher.hieronymus.presenter.dashboard.HistoryPresenter;
import ru.snatcher.hieronymus.view.adapter.RecyclerViewAdapter;
import ru.snatcher.hieronymus.view.fragment.BaseFragment;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class HistoryFragment extends BaseFragment implements HistoryFragmentView {
	public static final String ARG_IS_FAVOURITE = "ARG_IS_FAVOURITE";

	@BindView(R.id.recycler_history)
	RecyclerView fRecyclerView;

	@Inject
	HistoryPresenter fHistoryPresenter;

	RecyclerViewAdapter fRecyclerViewAdapter;

	public static HistoryFragment newInstance(int pPage) {
		Bundle args = new Bundle();
		args.putBoolean(ARG_IS_FAVOURITE, pPage != 0);
		HistoryFragment fragment = new HistoryFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		App.getComponent().inject(this);
		super.onCreate(savedInstanceState);
		fHistoryPresenter.onCreate(this);
	}

	@Override
	public void setUserVisibleHint(final boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);

		if (fRecyclerViewAdapter != null && isVisibleToUser) getTranslates();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View lvView = inflater.inflate(R.layout.fragment_history, container, false);
		ButterKnife.bind(this, lvView);

		getTranslates();

		return lvView;
	}

	@Override
	public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		fRecyclerView.setAdapter(fRecyclerViewAdapter);
	}

	@Override
	protected BasePresenter getPresenter() {
		return fHistoryPresenter;
	}

	@Override
	public void showTranslates(final List<Translate> pTranslates) {
		fRecyclerViewAdapter = new RecyclerViewAdapter(pTranslates, fHistoryPresenter, (App) getActivity().getApplication());
	}

	@Override
	public void getTranslates() {
		fHistoryPresenter.getTranslates(getArguments().getBoolean(ARG_IS_FAVOURITE), getContext());
	}


}
