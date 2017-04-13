package ru.snatcher.hieronymus.view;

import org.junit.Before;

import ru.snatcher.hieronymus.other.BaseTest;

/**
 * {@link MainActivityTest}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class MainActivityTest extends BaseTest {

	private MainActivity mainActivity;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		mainActivity = new MainActivity();
	}
}