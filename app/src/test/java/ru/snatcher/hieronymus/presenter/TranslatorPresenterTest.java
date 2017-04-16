package ru.snatcher.hieronymus.presenter;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import javax.inject.Inject;

import ru.snatcher.hieronymus.model.Model;
import ru.snatcher.hieronymus.model.db.Language;
import ru.snatcher.hieronymus.model.db.Translate;
import ru.snatcher.hieronymus.model.entity.LanguageDTO;
import ru.snatcher.hieronymus.model.entity.TranslateDTO;
import ru.snatcher.hieronymus.other.BaseTest;
import ru.snatcher.hieronymus.other.TestConstants;
import ru.snatcher.hieronymus.presenter.mapper.LanguageMapper;
import ru.snatcher.hieronymus.presenter.mapper.TranslateMapper;
import ru.snatcher.hieronymus.presenter.translator.TranslatorPresenter;
import ru.snatcher.hieronymus.view.ActivityCallback;
import ru.snatcher.hieronymus.view.fragment.translator.TranslatorFragmentView;
import rx.Observable;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * {@link TranslatorPresenter}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class TranslatorPresenterTest extends BaseTest {

	@Inject
	protected Model model;
	@Inject
	TranslateMapper fTranslateMapper;
	@Inject
	LanguageMapper fLanguageMapper;
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

		final ActivityCallback lvActivityCallback = mock(ActivityCallback.class);

		fTranslatorFragmentView = mock(TranslatorFragmentView.class);
		fTranslatorPresenter = new TranslatorPresenter(fTranslatorFragmentView);
		doAnswer(invocation -> Observable.just(fLanguageDTO))
				.when(model)
				.getRemoteLangs(TestConstants.TEST_KEY, "ru");

		doAnswer(invocation -> TestConstants.TEST_KEY)
				.when(fTranslatorFragmentView)
				.getLangs();
	}


	@Test
	public void testLoadData() {
		fTranslatorPresenter.onCreateView(null);
		fTranslatorPresenter.onStop();

		verify(fTranslatorFragmentView).showLanguageList(fLanguages);
	}

	@Test
	public void testSaveState() {
		fTranslatorPresenter.onCreateView(null);

		Bundle bundle = Bundle.EMPTY;
		fTranslatorPresenter.onStop();

		fTranslatorPresenter.onCreateView(bundle);

		verify(fTranslatorFragmentView, times(2)).showLanguageList(fLanguages);
		verify(model).getRemoteLangs(TestConstants.TEST_KEY, "ru");
	}
}