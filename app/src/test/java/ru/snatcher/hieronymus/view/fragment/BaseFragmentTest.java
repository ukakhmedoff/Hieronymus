package ru.snatcher.hieronymus.view.fragment;

import org.junit.Before;

import ru.snatcher.hieronymus.other.BaseTest;
import ru.snatcher.hieronymus.presenter.BasePresenter;
import ru.snatcher.hieronymus.view.MainActivity;
import ru.snatcher.hieronymus.view.fragment.translator.TranslatorFragment;

import static org.mockito.Mockito.mock;

/**
 * {@link BaseFragmentTest}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class BaseFragmentTest extends BaseTest {

	private BaseFragment baseFragment;
	private BasePresenter basePresenter;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		component.inject(this);

		final MainActivity lvActivity = mock(MainActivity.class);

		TranslatorFragment lvTranslatorFragment = new TranslatorFragment();
		baseFragment = lvTranslatorFragment;
		baseFragment.onCreate(null); //for Di
		baseFragment.onAttach(lvActivity); //for link activity

		basePresenter = lvTranslatorFragment.getPresenter();
	}

/*	@Test
	public void testOnStop() {
		baseFragment.onStop();
		verify(basePresenter).onStop();
	}*/
}