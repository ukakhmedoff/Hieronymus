package ru.snatcher.hieronymus.presenter.mapper;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ru.snatcher.hieronymus.db.Language;
import ru.snatcher.hieronymus.model.entity.LanguageDTO;
import rx.Observable;
import rx.functions.Func1;

/**
 * {@link LanguageMapper}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class LanguageMapper implements Func1<LanguageDTO, List<Language>> {

	@Inject
	LanguageMapper() {
	}

	@Override
	public List<Language> call(LanguageDTO pLanguageDTO) {
		if (pLanguageDTO == null) return null;

		List<Language> lvLanguages = Observable.from(pLanguageDTO.getLangs().entrySet())
				.map(languages -> new Language(languages.getKey(), languages.getValue()))
				.toList()
				.toBlocking()
				.first();

		Collections.sort(lvLanguages, (pLanguage1, pLanguage2) -> pLanguage1.getLangValue().compareTo(pLanguage2.getLangValue()));
		return lvLanguages;
	}

}