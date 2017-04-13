package ru.snatcher.hieronymus.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.snatcher.hieronymus.model.entity.LanguageDTO;
import ru.snatcher.hieronymus.model.entity.TranslateDTO;
import ru.snatcher.hieronymus.tools.TestUtils;

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
	LanguageDTO provideLanguageDTO() {
		return testUtils.getGson().fromJson(testUtils.readString("json/contributors"), LanguageDTO.class);
	}


	@Provides
	@Singleton
	TranslateDTO provideTranslateDTO() {
		return testUtils.getGson().fromJson(testUtils.readString("json/branches"), TranslateDTO.class);
	}
}
