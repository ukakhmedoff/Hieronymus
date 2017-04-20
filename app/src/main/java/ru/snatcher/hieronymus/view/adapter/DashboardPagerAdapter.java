package ru.snatcher.hieronymus.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import ru.snatcher.hieronymus.R;
import ru.snatcher.hieronymus.view.fragment.dashboard.HistoryFragment;

/**
 * {@link DashboardPagerAdapter}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class DashboardPagerAdapter extends BasePagerAdapter {

	public DashboardPagerAdapter(Context pContext, final FragmentManager pFragmentManager) {
		super(pFragmentManager);
		fTabTitles = pContext.getResources().getStringArray(R.array.dashboard_tabs_names);
	}

	@Override
	public Fragment getItem(final int pPosition) {
		return HistoryFragment.newInstance(pPosition);
	}
}