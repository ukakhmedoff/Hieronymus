package ru.snatcher.hieronymus.other.di;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.snatcher.hieronymus.db.Language;
import ru.snatcher.hieronymus.db.Translate;
import ru.snatcher.hieronymus.model.entity.LanguageDTO;
import ru.snatcher.hieronymus.model.entity.TranslateDTO;
import ru.snatcher.hieronymus.other.TestUtils;
import ru.snatcher.hieronymus.presenter.mapper.LanguageMapper;
import ru.snatcher.hieronymus.presenter.mapper.TranslateMapper;

/**
 * {@link DataTestModule}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
@Module
public class DataTestModule {

	private TestUtils testUtils;

	public DataTestModule() {
		testUtils = new TestUtils();
	}

	@Provides
	@Singleton
	TestUtils provideTestUtils() {
		return testUtils;
	}


	@Provides
	@Singleton
	LanguageDTO LanguageDTOList() {
		return testUtils.getGson().fromJson(testUtils.readString("json/langs.json"), LanguageDTO.class);
	}


	@Provides
	@Singleton
	TranslateDTO provideTranslateDTO() {
		return testUtils.getGson().fromJson(testUtils.readString("json/translate.json"), TranslateDTO.class);
	}


	@Provides
	@Singleton
	Translate provideTranslate(TranslateMapper pTranslateMapper, TranslateDTO pTranslateDTO) {
		return pTranslateMapper.call(pTranslateDTO);
	}

	@Provides
	@Singleton
	List<Language> provideLanguageList(LanguageMapper pLanguageMapper, LanguageDTO pLanguageDTO) {
		return pLanguageMapper.call(pLanguageDTO);
	}
}