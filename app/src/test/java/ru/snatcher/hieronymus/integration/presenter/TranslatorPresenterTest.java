package ru.snatcher.hieronymus.integration.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import ru.snatcher.hieronymus.integration.other.IntegrationBaseTest;
import ru.snatcher.hieronymus.model.Model;
import ru.snatcher.hieronymus.model.entity.LanguageDTO;
import ru.snatcher.hieronymus.model.entity.TranslateDTO;
import ru.snatcher.hieronymus.other.TestConstants;
import ru.snatcher.hieronymus.presenter.TranslatorPresenter;
import ru.snatcher.hieronymus.view.fragment.translator.TranslatorFragmentView;
import rx.Observable;

import static org.mockito.Mockito.mock;

/**
 * {@link TranslatorPresenterTest}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class TranslatorPresenterTest extends IntegrationBaseTest {

	@Inject
	Model fModel;

	@Inject
	LanguageDTO fLanguageDTO;

	@Inject
	TranslateDTO fTranslateDTO;

	private TranslatorPresenter fTranslatorPresenter;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		component.inject(this);

		fModel = Mockito.mock(Model.class);
		final TranslatorFragmentView lvTranslatorFragmentView = mock(TranslatorFragmentView.class);

		fTranslatorPresenter = new TranslatorPresenter(lvTranslatorFragmentView);
	}

	@Test
	public void testLoadData() {
		fTranslatorPresenter.onCreateView(null);
		fTranslatorPresenter.onStop();

		Mockito.when(fModel.getRemoteLangs(TestConstants.TEST_KEY, "ru")).thenReturn(Observable.just(fLanguageDTO));
		Mockito.when(fModel.getTranslatedText(TestConstants.TEST_KEY, TestConstants.TEST_TO_TRANSLATE, TestConstants.TEST_LANGS)).thenReturn(Observable.just(fTranslateDTO));
	}

	@Test
	public void testLoadingState() {
		fTranslatorPresenter.onCreateView(null);
		fTranslatorPresenter.onStop();
	}

	@Test
	public void testOnErrorLoadingState() {
		setErrorAnswerWebServer();
	}
}