package ru.snatcher.hieronymus.view.adapter;

/**
 * {@link MainPagerAdapter}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.SparseArray;
import android.view.ViewGroup;

import ru.snatcher.hieronymus.R;
import ru.snatcher.hieronymus.view.fragment.dashboard.DashboardFragment;
import ru.snatcher.hieronymus.view.fragment.translator.TranslatorFragment;

/**
 * {@link DashboardPagerAdapter}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class MainPagerAdapter extends BasePagerAdapter {
	private SparseArray<Fragment> fRegisteredFragments = new SparseArray<>();

	public MainPagerAdapter(Context pContext, final FragmentManager pFragmentManager) {
		super(pFragmentManager);
		fTabTitles = pContext.getResources().getStringArray(R.array.main_tabs_names);
	}


	@Override
	public Fragment getItem(final int pPosition) {
		if (pPosition == 0) return new TranslatorFragment();
		else return new DashboardFragment();
	}

	@Override
	public Object instantiateItem(ViewGroup pViewGroup, int pPosition) {
		Fragment lvFragment = (Fragment) super.instantiateItem(pViewGroup, pPosition);
		fRegisteredFragments.put(pPosition, lvFragment);
		return lvFragment;
	}

	@Override
	public void destroyItem(ViewGroup pViewGroup, int pPosition, Object pObject) {
		fRegisteredFragments.remove(pPosition);
		super.destroyItem(pViewGroup, pPosition, pObject);
	}

	public Fragment getRegisteredFragment(int pPosition) {
		return fRegisteredFragments.get(pPosition);
	}

}
