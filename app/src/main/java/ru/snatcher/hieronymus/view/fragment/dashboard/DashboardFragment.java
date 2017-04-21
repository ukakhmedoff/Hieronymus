package ru.snatcher.hieronymus.view.fragment.dashboard;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.snatcher.hieronymus.R;
import ru.snatcher.hieronymus.view.adapter.DashboardPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

	@BindView(R.id.tabs)
	TabLayout fTabLayout;

	@BindView(R.id.dash_view_pager)
	ViewPager fViewPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View lvView = inflater.inflate(R.layout.fragment_dashboard, container, false);
		ButterKnife.bind(this, lvView);

		fViewPager.setAdapter(new DashboardPagerAdapter(getActivity(), getFragmentManager()));
		fTabLayout.setupWithViewPager(fViewPager);

		return lvView;
	}

	@Override
	public void setUserVisibleHint(final boolean isVisibleToUser) {
		if (isVisibleToUser) fViewPager.getAdapter().notifyDataSetChanged();
		super.setUserVisibleHint(isVisibleToUser);
	}
}
