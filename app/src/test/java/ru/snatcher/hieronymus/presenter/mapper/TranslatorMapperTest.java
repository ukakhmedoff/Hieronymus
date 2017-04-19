package ru.snatcher.hieronymus.presenter.mapper;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import javax.inject.Inject;

import ru.snatcher.hieronymus.db.Language;
import ru.snatcher.hieronymus.db.Translate;
import ru.snatcher.hieronymus.model.entity.LanguageDTO;
import ru.snatcher.hieronymus.model.entity.TranslateDTO;
import ru.snatcher.hieronymus.other.BaseTest;

import static org.junit.Assert.assertEquals;

/**
 * {@link TranslatorMapperTest}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class TranslatorMapperTest extends BaseTest {

	@Inject
	TranslateMapper fTranslateMapper;

	@Inject
	LanguageMapper fLanguageMapper;

	private TranslateDTO fTranslateDTO;

	private LanguageDTO fLanguageDTO;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		component.inject(this);
		fTranslateDTO = testUtils.getGson().fromJson(testUtils.readString("json/langs.json"), TranslateDTO.class);
		fLanguageDTO = testUtils.getGson().fromJson(testUtils.readString("json/translate.json"), LanguageDTO.class);

	}

	@Test
	public void testCall() throws Exception {
		Translate lvTranslate = fTranslateMapper.call(fTranslateDTO);
		List<Language> lvLanguages = fLanguageMapper.call(fLanguageDTO);

		assertEquals("hello", lvTranslate.getTranslatedText());

		assertEquals(93, lvLanguages.size());
	}
}
