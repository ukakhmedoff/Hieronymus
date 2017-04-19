package ru.snatcher.hieronymus.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.snatcher.hieronymus.R;
import ru.snatcher.hieronymus.view.fragment.dashboard.HistoryFragment;

/**
 * {@link PagerAdapter}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class PagerAdapter extends FragmentPagerAdapter {

	private String[] fTabTitles;

	private HistoryFragment fHistoryFragment;

	public PagerAdapter(Context pContext, final FragmentManager pFragmentManager) {
		super(pFragmentManager);
		fTabTitles = pContext.getResources().getStringArray(R.array.dashboard_tabs_names);
	}

	@Override
	public Fragment getItem(final int pPosition) {
		fHistoryFragment = HistoryFragment.newInstance(pPosition);
		return fHistoryFragment;
	}

	@Override
	public int getCount() {
		return fTabTitles.length;
	}

	@Override
	public CharSequence getPageTitle(int pPosition) {
		return fTabTitles[pPosition];
	}

}
