package ru.snatcher.hieronymus.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.snatcher.hieronymus.R;
import ru.snatcher.hieronymus.view.fragment.dashboard.DashboardFragment;
import ru.snatcher.hieronymus.view.fragment.translator.TranslatorFragment;

import static ru.snatcher.hieronymus.other.Constants.FRAGMENT_DASHBOARD_TAG;
import static ru.snatcher.hieronymus.other.Constants.FRAGMENT_TRANSLATOR_TAG;
import static ru.snatcher.hieronymus.other.Constants.PAGER_DASHBOARD_FRAGMENT_ID;
import static ru.snatcher.hieronymus.other.Constants.PAGER_FRAGMENT_TAG;
import static ru.snatcher.hieronymus.other.Constants.PAGER_TRANSLATOR_FRAGMENT_ID;
import static ru.snatcher.hieronymus.other.Constants.PREFERENCES_APP_STARTED;
import static ru.snatcher.hieronymus.other.Constants.PREFERENCES_LANGUAGE_FROM_TRANSLATE;
import static ru.snatcher.hieronymus.other.Constants.PREFERENCES_LANGUAGE_TO_TRANSLATE;
import static ru.snatcher.hieronymus.other.Constants.PREFERENCES_NAME;

/**
 * {@link MainActivity} is {@link AppCompatActivity}
 * <p>
 * Here we initialize our fragments and switch between them, as well as perform operations with SharedPreferences
 *
 * @author Usman Akhmedoff
 * @version 1.3
 */
public class MainActivity extends AppCompatActivity implements ActivityCallback {

	@BindView(R.id.navigation)
	BottomNavigationView fNavigation;
	private SharedPreferences fSharedPreferences;
	private SharedPreferences.Editor fEditor;
	private FragmentTransaction fFragmentTransaction;
	private FragmentManager fFragmentManager;

	private OnNavigationItemSelectedListener fOnNavigationItemSelectedListener
			= item -> {

		int lvFrom = item.getItemId();
		switch (item.getItemId()) {
			case R.id.navigation_main:
				if (lvFrom != PAGER_TRANSLATOR_FRAGMENT_ID)
					setFragment(FRAGMENT_TRANSLATOR_TAG, PAGER_TRANSLATOR_FRAGMENT_ID, lvFrom);
				return true;
			case R.id.navigation_dashboard:
				if (lvFrom != PAGER_DASHBOARD_FRAGMENT_ID)
					setFragment(FRAGMENT_DASHBOARD_TAG, PAGER_DASHBOARD_FRAGMENT_ID, lvFrom);
				return true;
		}
		return false;
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		fFragmentManager = getSupportFragmentManager();

		if (savedInstanceState != null)
			initFragment(savedInstanceState.getString(PAGER_FRAGMENT_TAG));
		else initFragment(FRAGMENT_TRANSLATOR_TAG);

		initPreferences();
		fNavigation.setOnNavigationItemSelectedListener(fOnNavigationItemSelectedListener);
	}

	private void beginTransaction() {
		fFragmentTransaction = fFragmentManager.beginTransaction();
	}

	/**
	 * Processing of elements clicking
	 *
	 * @param pPAGER_FRAGMENT_TAG - Fragment's tag
	 * @param pPagerFragmentId    - The id of the fragment to which we turn
	 * @param pFrom               - The id of the fragment from which we turn
	 */
	private void setFragment(final String pPAGER_FRAGMENT_TAG, final int pPagerFragmentId, final int pFrom) {
		if (pPagerFragmentId != pFrom) {
			beginTransaction();
			hidePreviousFragment();
			setAnimation(pFrom, pPagerFragmentId, getFragment(pPAGER_FRAGMENT_TAG));
			replaceFragment(pPAGER_FRAGMENT_TAG);
			commitTransaction();
		}
	}

	/**
	 * Display the fragment
	 *
	 * @param pPAGER_FRAGMENT_TAG - Fragment's tag
	 */
	private void replaceFragment(String pPAGER_FRAGMENT_TAG) {
		Fragment fragment = fFragmentManager.findFragmentByTag(pPAGER_FRAGMENT_TAG);
		if (fragment != null) fFragmentTransaction.show(fragment);
		else {
			fragment = getFragment(pPAGER_FRAGMENT_TAG);
			fFragmentTransaction.add(R.id.content, fragment, pPAGER_FRAGMENT_TAG);
		}
	}

	/**
	 * Initialize {@link Fragment}
	 *
	 * @param pPAGER_FRAGMENT_TAG - Fragment's tag
	 */
	private void initFragment(String pPAGER_FRAGMENT_TAG) {
		beginTransaction();
		replaceFragment(pPAGER_FRAGMENT_TAG);
		commitTransaction();
	}

	/**
	 * Initialize {@link SharedPreferences}
	 * If app isn't started we create a new preferences
	 */
	private void initPreferences() {
		fSharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

		fEditor = fSharedPreferences.edit();

		if (!fSharedPreferences.getBoolean(PREFERENCES_APP_STARTED, false)) {
			fEditor.putBoolean(PREFERENCES_APP_STARTED, true);
			fEditor.putInt(PREFERENCES_LANGUAGE_FROM_TRANSLATE, 63);
			fEditor.putInt(PREFERENCES_LANGUAGE_TO_TRANSLATE, 3);
		}
	}

	/**
	 * Setting animation to fragments
	 *
	 * @param pFragment        - Fragment
	 * @param pPagerFragmentId - The id of the fragment to which we turn
	 * @param pFrom            - The id of the fragment from which we turn
	 */
	private void setAnimation(final int pFrom, final int pPagerFragmentId, Fragment pFragment) {
		Slide lvSlide = new Slide();
		lvSlide.setDuration(500);

		if (pFrom > pPagerFragmentId) lvSlide = new Slide(Gravity.START);
		else if (pFrom < pPagerFragmentId) lvSlide = new Slide(Gravity.END);

		pFragment.setReenterTransition(lvSlide);
		pFragment.setEnterTransition(lvSlide);
	}

	/**
	 * calls hideFragment method with tags
	 */
	private void hidePreviousFragment() {
		hideFragment(FRAGMENT_TRANSLATOR_TAG);
		hideFragment(FRAGMENT_DASHBOARD_TAG);
	}

	/**
	 * Hide fragment
	 *
	 * @param pTag - Fragment's tag that we must hide
	 */
	private void hideFragment(String pTag) {
		Fragment lvFragmentByTag = fFragmentManager.findFragmentByTag(pTag);
		if (lvFragmentByTag != null && lvFragmentByTag.isVisible())
			fFragmentTransaction.hide(lvFragmentByTag);
	}

	/**
	 * Return {@link Fragment} by {@param tag}
	 *
	 * @param tag - Fragment's tag that we must return
	 * @return {@link Fragment} by {@param tag}
	 */
	@NonNull
	private Fragment getFragment(String tag) {
		switch (tag) {
			case FRAGMENT_TRANSLATOR_TAG:
				return new TranslatorFragment();
			case FRAGMENT_DASHBOARD_TAG:
				return new DashboardFragment();
			default:
				throw new IllegalArgumentException();
		}
	}

	/**
	 * Commit transaction
	 */
	private void commitTransaction() {
		fFragmentTransaction.commit();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		int i = fNavigation.getSelectedItemId();
		if (i == 0) outState.putString(PAGER_FRAGMENT_TAG, FRAGMENT_TRANSLATOR_TAG);
		else if (i == 1) outState.putString(PAGER_FRAGMENT_TAG, FRAGMENT_DASHBOARD_TAG);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void setSpinnerLanguagesToPreferences(final String pToPreferences, final int pLanguagePosition) {
		fEditor.putInt(pToPreferences, pLanguagePosition).commit();
	}

	@Override
	public int getSpinnerLanguagesFromPreferences(final String pSpinnerLanguages) {
		return fSharedPreferences.getInt(pSpinnerLanguages, 0);
	}
}
