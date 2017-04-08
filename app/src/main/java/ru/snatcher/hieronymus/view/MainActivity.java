package ru.snatcher.hieronymus.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.snatcher.hieronymus.R;
import ru.snatcher.hieronymus.other.Constants;
import ru.snatcher.hieronymus.view.fragment.dashboard.DashboardFragment;
import ru.snatcher.hieronymus.view.fragment.main.TranslatorFragment;

public class MainActivity extends AppCompatActivity implements ActivityCallback {

	private static final String PAGER_POSITION = "PAGER_POSITION";

	private SharedPreferences fSharedPreferences;

	@BindView(R.id.navigation)
	BottomNavigationView fNavigation;

	private SharedPreferences.Editor fEditor;
	private BottomNavigationView.OnNavigationItemSelectedListener fOnNavigationItemSelectedListener
			= item -> {

		switch (item.getItemId()) {
			case R.id.navigation_main:
				showFragment(new TranslatorFragment());
				return true;
			case R.id.navigation_dashboard:
				showFragment(new DashboardFragment());
				return true;
			case R.id.navigation_settings:
				showFragment(new TranslatorFragment());
				return true;
		}
		return false;
	};

	private void showFragment(Fragment pFragment) {
		final FragmentTransaction lvFragmentTransaction = getSupportFragmentManager().beginTransaction();
		lvFragmentTransaction.replace(R.id.content, pFragment).commit();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		showFragment(new TranslatorFragment());

		initPreferences();
		fNavigation.setOnNavigationItemSelectedListener(fOnNavigationItemSelectedListener);
		fNavigation.setSelected(true);
	}

	private void initPreferences() {
		fSharedPreferences = getSharedPreferences(Constants.PREFERENCES_NAME, MODE_PRIVATE);

		fEditor = fSharedPreferences.edit();

		if (!fSharedPreferences.getBoolean(Constants.PREFERENCES_APP_STARTED, false)) {
			fEditor.putBoolean(Constants.PREFERENCES_APP_STARTED, true);
			fEditor.putInt(Constants.PREFERENCES_LANGUAGE_FROM_TRANSLATE, 0);
			fEditor.putInt(Constants.PREFERENCES_LANGUAGE_TO_TRANSLATE, 1);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(PAGER_POSITION, fNavigation.getSelectedItemId());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		fNavigation.setSelectedItemId(savedInstanceState.getInt(PAGER_POSITION));
	}

	@Override
	public void setSpinnerLanguagesToPreferences(final String pToPreferences, final int pLanguagePosition) {
		fEditor.putInt(pToPreferences, pLanguagePosition);
		fEditor.commit();
	}

	@Override
	public int getSpinnerLanguagesFromPreferences(final String pSpinnerLanguages) {
		return fSharedPreferences.getInt(pSpinnerLanguages, 0);
	}
}
