package ru.snatcher.hieronymus.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import ru.snatcher.hieronymus.R;
import ru.snatcher.hieronymus.other.Constants;
import ru.snatcher.hieronymus.presenter.vo.Language;
import ru.snatcher.hieronymus.presenter.vo.Translate;
import ru.snatcher.hieronymus.view.fragment.MainFragment;

public class MainActivity extends AppCompatActivity implements ActivityCallback {

	private static final String PAGER_POSITION = "PAGER_POSITION";

	private SharedPreferences fSharedPreferences;
	private BottomNavigationView fNavigation;

	private SharedPreferences.Editor fEditor;
	private BottomNavigationView.OnNavigationItemSelectedListener fOnNavigationItemSelectedListener
			= item -> {

		switch (item.getItemId()) {
			case R.id.navigation_main:
				showFragment(new MainFragment());
				return true;
			case R.id.navigation_dashboard:
				showFragment(new MainFragment());
				return true;
			case R.id.navigation_settings:
				showFragment(new MainFragment());
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

		showFragment(new MainFragment());

		fSharedPreferences = getSharedPreferences(Constants.PREFERENCES_NAME, MODE_PRIVATE);

		fEditor = fSharedPreferences.edit();

		if (!fSharedPreferences.getBoolean(Constants.PREFERENCES_APP_STARTED, false)) {
			fEditor.putBoolean(Constants.PREFERENCES_APP_STARTED, true);
			fEditor.putInt(Constants.PREFERENCES_LANGUAGE_FROM_TRANSLATE, 0);
			fEditor.putInt(Constants.PREFERENCES_LANGUAGE_TO_TRANSLATE, 1);
		}
		fNavigation = (BottomNavigationView) findViewById(R.id.navigation);
		fNavigation.setOnNavigationItemSelectedListener(fOnNavigationItemSelectedListener);
		fNavigation.setSelected(true);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(PAGER_POSITION, fNavigation.getSelectedItemId());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		fNavigation.setSelectedItemId(savedInstanceState.getInt(PAGER_POSITION, 0));
	}

	@Override
	public void saveTranslate(final Translate pTranslate) {
		Translate lvTranslate = Translate.findById(Translate.class, pTranslate.getId());
		if (lvTranslate != null & pTranslate.isBookmark() & pTranslate.getTranslateGroup() != null) {
			lvTranslate.setBookmark(pTranslate.isBookmark());
			lvTranslate.setTranslateGroup(pTranslate.getTranslateGroup());
		} else if (lvTranslate != null & pTranslate.isBookmark()) {
			lvTranslate.setBookmark(pTranslate.isBookmark());
			lvTranslate.setTranslateGroup(pTranslate.getTranslateGroup());
		} else if (lvTranslate != null & pTranslate.getTranslateGroup() != null) {
			lvTranslate.setTranslateGroup(pTranslate.getTranslateGroup());
		}
		assert lvTranslate != null;
		lvTranslate.save();

	}

	@Override
	public void saveLanguage(final Language pLanguage) {
		//pLanguage.save();
	}

	@Override
	public void saveLanguages(final List<Language> pLanguages) {
		/*for (Language lvLanguage : pLanguages) {
			lvLanguage.save();
		}*/
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
