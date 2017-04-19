package ru.snatcher.hieronymus;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import ru.snatcher.hieronymus.di.TestComponent;
import ru.snatcher.hieronymus.other.App;
import ru.snatcher.hieronymus.tools.ApiConfig;
import ru.snatcher.hieronymus.tools.EspressoTools;
import ru.snatcher.hieronymus.tools.TestConstants;
import ru.snatcher.hieronymus.view.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@android.support.test.filters.LargeTest
public class TranslatorFragmentTest {

	@Rule
	public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

	@Inject
	ApiConfig apiConfig;

	@Before
	public void setUp() {
		((TestComponent) App.getComponent()).inject(this);
	}

	@Test
	public void testElementsDisplayed() {
		onView(withId(R.id.textTranslated)).check(matches(isDisplayed()));
		onView(withId(R.id.spinner_language_from)).check(matches(isDisplayed()));
		onView(withId(R.id.spinner_language_to)).check(matches(isDisplayed()));
	}

	@Test
	public void testGetTranslate() {
		apiConfig.setCorrectAnswer();

		onView(withId(R.id.textTranslated)).check(EspressoTools.hasItemsCount(1));
	}

	@Test
	public void testGetLanguages() {
		apiConfig.setCorrectAnswer();

		onView(withId(R.id.spinner_language_from)).check(EspressoTools.hasItemsCount(93));
		onView(withId(R.id.spinner_language_to)).check(EspressoTools.hasItemsCount(93));

	}

	@Test
	public void testGetTranslateError() {
		apiConfig.setErrorAnswer();

		onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(TestConstants.TEST_ERROR)))
				.check(matches(isDisplayed()));

		onView(withId(R.id.textTranslated)).check(EspressoTools.hasItemsCount(0));
	}
}