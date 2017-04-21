package ru.snatcher.hieronymus.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.snatcher.hieronymus.R;
import ru.snatcher.hieronymus.model.db.Translate;
import ru.snatcher.hieronymus.other.Utils;
import ru.snatcher.hieronymus.view.adapter.MainPagerAdapter;
import ru.snatcher.hieronymus.view.fragment.translator.TranslatorFragmentView;

import static ru.snatcher.hieronymus.other.Constants.PAGER_FRAGMENT_ID;
import static ru.snatcher.hieronymus.other.Constants.PAGER_TRANSLATOR_FRAGMENT_ID;

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

	MainPagerAdapter fMainPagerAdapter;

	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		Utils.initPreferences(this);
		initTabs();
	}

	private void initTabs() {
		fMainPagerAdapter = new MainPagerAdapter(this, getSupportFragmentManager());
		fContent.setAdapter(fMainPagerAdapter);
		fNavigation.setupWithViewPager(fContent);
	}

	@Override
	protected final void onSaveInstanceState(Bundle outState) {
		outState.putInt(PAGER_FRAGMENT_ID, fContent.getCurrentItem());
		super.onSaveInstanceState(outState);
	}

	@Override
	protected final void onRestoreInstanceState(final Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		fContent.setCurrentItem(savedInstanceState.getInt(PAGER_FRAGMENT_ID));
	}

	@Override
	public final void setSpinnerLanguagesToPreferences(final String pToPreferences, final int pLanguagePosition) {
		Utils.setSpinnerLanguagesToPreferences(this, pToPreferences, pLanguagePosition);
	}

	@Override
	public final int getSpinnerLanguagesFromPreferences(final String pSpinnerLanguages) {
		return Utils.getSpinnerLanguagesFromPreferences(this, pSpinnerLanguages);
	}

	@Override
	public final void onTranslatesItemClicked(final Translate pTranslate) {
		fContent.setCurrentItem(PAGER_TRANSLATOR_FRAGMENT_ID, true);
		TranslatorFragmentView lvRegisteredFragment = (TranslatorFragmentView) fMainPagerAdapter
				.getRegisteredFragment(PAGER_TRANSLATOR_FRAGMENT_ID);
		lvRegisteredFragment.showSelectedItem(pTranslate);
	}
}
