package ru.snatcher.hieronymus.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * {@link BasePagerAdapter} for {@link android.support.v4.view.ViewPager}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
class BasePagerAdapter extends FragmentPagerAdapter {

	String[] fTabTitles;

	BasePagerAdapter(final FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(final int position) {
		return null;
	}

	@Override
	public final int getCount() {
		return fTabTitles.length;
	}

	@Override
	public final CharSequence getPageTitle(int pPosition) {
		return fTabTitles[pPosition];
	}
}
