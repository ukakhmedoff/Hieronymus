package ru.snatcher.hieronymus.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.snatcher.hieronymus.R;
import ru.snatcher.hieronymus.db.Translate;
import ru.snatcher.hieronymus.view.adapter.MainPagerAdapter;
import ru.snatcher.hieronymus.view.fragment.translator.TranslatorFragmentView;

import static ru.snatcher.hieronymus.other.Constants.PAGER_FRAGMENT_ID;
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


	@BindView(R.id.content)
	UnswipeableViewPager fContent;

	@BindView(R.id.navigation)
	TabLayout fNavigation;

	private SharedPreferences fSharedPreferences;
	private SharedPreferences.Editor fEditor;
	MainPagerAdapter fMainPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		initPreferences();
		initTabs();

	}

	private void initTabs() {
		fMainPagerAdapter = new MainPagerAdapter(this, getSupportFragmentManager());
		fContent.setAdapter(fMainPagerAdapter);
		fNavigation.setupWithViewPager(fContent);
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

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt(PAGER_FRAGMENT_ID, fContent.getCurrentItem());
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(final Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		fContent.setCurrentItem(savedInstanceState.getInt(PAGER_FRAGMENT_ID));
	}

	@Override
	public void setSpinnerLanguagesToPreferences(final String pToPreferences, final int pLanguagePosition) {
		fEditor.putInt(pToPreferences, pLanguagePosition).commit();
	}

	@Override
	public int getSpinnerLanguagesFromPreferences(final String pSpinnerLanguages) {
		return fSharedPreferences.getInt(pSpinnerLanguages, 0);
	}

	@Override
	public void onTranslatesItemClicked(final Translate pTranslate) {
		fContent.setCurrentItem(PAGER_TRANSLATOR_FRAGMENT_ID, true);
		TranslatorFragmentView lvRegisteredFragment = (TranslatorFragmentView) fMainPagerAdapter
				.getRegisteredFragment(PAGER_TRANSLATOR_FRAGMENT_ID);
		lvRegisteredFragment.showSelectedItem(pTranslate);
	}

}
