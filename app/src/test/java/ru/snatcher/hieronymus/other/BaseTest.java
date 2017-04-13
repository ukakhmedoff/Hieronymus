package ru.snatcher.hieronymus.other;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.snatcher.hieronymus.BuildConfig;
import ru.snatcher.hieronymus.other.di.TestComponent;

/**
 * {@link BaseTest}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class,
		constants = BuildConfig.class,
		sdk = 21)
@Ignore
public class BaseTest {

	public TestComponent component;
	public TestUtils testUtils;

	@Before
	public void setUp() throws Exception {
		component = (TestComponent) App.getComponent();
		testUtils = new TestUtils();
	}

}
