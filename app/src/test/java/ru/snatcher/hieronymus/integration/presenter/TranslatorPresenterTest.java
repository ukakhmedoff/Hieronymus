package ru.snatcher.hieronymus.integration.presenter;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import javax.inject.Inject;

import ru.snatcher.hieronymus.db.Language;
import ru.snatcher.hieronymus.db.Translate;
import ru.snatcher.hieronymus.integration.other.IntegrationBaseTest;
import ru.snatcher.hieronymus.model.Model;
import ru.snatcher.hieronymus.model.entity.LanguageDTO;
import ru.snatcher.hieronymus.model.entity.TranslateDTO;
import ru.snatcher.hieronymus.other.TestConstants;
import ru.snatcher.hieronymus.presenter.mapper.LanguageMapper;
import ru.snatcher.hieronymus.presenter.mapper.TranslateMapper;
import ru.snatcher.hieronymus.presenter.translator.TranslatorPresenter;
import ru.snatcher.hieronymus.view.fragment.translator.TranslatorFragmentView;
import rx.Observable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * {@link TranslatorPresenterTest}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class TranslatorPresenterTest extends IntegrationBaseTest {

	@Inject
	TranslateMapper fTranslateMapper;
	@Inject
	LanguageMapper fLanguageMapper;

	@Inject
	Model fModel;

	@Inject
	List<Language> fLanguages;

	@Inject
	LanguageDTO fLanguageDTO;

	@Inject
	Translate fTranslate;

	@Inject
	TranslateDTO fTranslateDTO;

	private TranslatorFragmentView fTranslatorFragmentView;
	private TranslatorPresenter fTranslatorPresenter;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		component.inject(this);

		fModel = Mockito.mock(Model.class);
		fTranslatorFragmentView = mock(TranslatorFragmentView.class);

		Mockito.when(fModel.getRemoteLangs(TestConstants.TEST_KEY, "ru")).thenReturn(Observable.just(fLanguageDTO));
		Mockito.when(fModel.getTranslatedText(TestConstants.TEST_KEY, TestConstants.TEST_TO_TRANSLATE, TestConstants.TEST_LANGS)).thenReturn(Observable.just(fTranslateDTO));

		fTranslatorPresenter = new TranslatorPresenter(fTranslatorFragmentView);
	}


	@Test
	public void testLoadData() {
		fTranslatorPresenter.onCreateView(null);
		fTranslatorPresenter.onStop();

		verify(fTranslatorFragmentView).showLanguageList(fLanguages);
		verify(fTranslatorFragmentView).showTranslate(fTranslate);
	}

	@Test
	public void testSaveState() {
		fTranslatorPresenter.onCreateView(null);

		Bundle bundle = Bundle.EMPTY;
		fTranslatorPresenter.onStop();

		fTranslatorPresenter.onCreateView(bundle);

		verify(fTranslatorFragmentView, times(2)).showLanguageList(fLanguages);
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