package ru.snatcher.hieronymus.integration.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;

import java.util.List;

import javax.inject.Inject;

import ru.snatcher.hieronymus.R;
import ru.snatcher.hieronymus.integration.other.IntegrationBaseTest;
import ru.snatcher.hieronymus.model.db.Language;
import ru.snatcher.hieronymus.other.TestConstants;
import ru.snatcher.hieronymus.view.MainActivity;
import ru.snatcher.hieronymus.view.fragment.translator.TranslatorFragment;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * {@link TranslatorFragmentTest}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class TranslatorFragmentTest extends IntegrationBaseTest {

	@Inject
	List<Language> fLanguageList;

	private TranslatorFragment fTranslatorFragment;

	private MainActivity activity;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		component.inject(this);

		fTranslatorFragment = spy(new TranslatorFragment());
		activity = Robolectric.buildActivity(MainActivity.class).create().get();

		fTranslatorFragment.onAttach(activity);
		doAnswer(invocation -> TestConstants.TEST_KEY)
				.when(fTranslatorFragment)
				.getLangs();

	}

	@Test
	public void testOnCreate() {
		fTranslatorFragment.onCreate(null);
		fTranslatorFragment.onCreateView(LayoutInflater.from(activity), (ViewGroup) activity.findViewById(R.id.content), null);
		fTranslatorFragment.onStop();
		verify(fTranslatorFragment).showLanguageList(fLanguageList);
	}

	@Test
	public void testOnCreateWithError() {
		setErrorAnswerWebServer();
		fTranslatorFragment.onCreate(null);
		fTranslatorFragment.onCreateView(LayoutInflater.from(activity), (ViewGroup) activity.findViewById(R.id.content), null);
		fTranslatorFragment.onStop();
		verify(fTranslatorFragment).showError(TestConstants.ERROR_RESPONSE_500);
	}

}