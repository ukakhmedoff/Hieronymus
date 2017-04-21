package ru.snatcher.hieronymus.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;

import javax.inject.Inject;

import ru.snatcher.hieronymus.R;
import ru.snatcher.hieronymus.other.BaseTest;
import ru.snatcher.hieronymus.other.di.view.DaggerTestViewComponent;
import ru.snatcher.hieronymus.other.di.view.TestViewComponent;
import ru.snatcher.hieronymus.other.di.view.TestViewDynamicModule;
import ru.snatcher.hieronymus.presenter.TranslatorPresenter;
import ru.snatcher.hieronymus.view.MainActivity;
import ru.snatcher.hieronymus.view.fragment.translator.TranslatorFragment;

import static org.mockito.Mockito.verify;

/**
 * {@link TranslatorFragmentTest}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class TranslatorFragmentTest extends BaseTest {

	@Inject
	TranslatorPresenter fTranslatorPresenter;

	private TranslatorFragment fTranslatorFragment;
	private MainActivity fMainActivity;

	private Bundle bundle;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		TestViewComponent testViewComponent = DaggerTestViewComponent.builder()
				.testViewDynamicModule(new TestViewDynamicModule())
				.build();

		testViewComponent.inject(this);

		fTranslatorFragment = new TranslatorFragment();
		fMainActivity = Robolectric.setupActivity(MainActivity.class);
		bundle = Bundle.EMPTY;

		fTranslatorFragment.setViewComponent(testViewComponent);

		fTranslatorFragment.onCreate(null); // need for DI
	}

	@Test
	public void testOnCreateView() {
		fTranslatorFragment.onCreateView(LayoutInflater.from(fMainActivity), (ViewGroup) fMainActivity.findViewById(R.id.content), null);
		verify(fTranslatorPresenter).onCreateView(null);
	}

	@Test
	public void testOnCreateViewWithBundle() {
		fTranslatorFragment.onCreateView(LayoutInflater.from(fMainActivity), (ViewGroup) fMainActivity.findViewById(R.id.content), bundle);
		verify(fTranslatorPresenter).onCreateView(bundle);
	}

	@Test
	public void testOnStop() {
		fTranslatorFragment.onStop();
		verify(fTranslatorPresenter).onStop();
	}

	@Test
	public void testOnSaveInstanceState() {
		fTranslatorFragment.onSaveInstanceState(null);
	}

	@Test
	public void testOnSaveInstanceStateWithBundle() {
		fTranslatorFragment.onSaveInstanceState(bundle);
	}
}