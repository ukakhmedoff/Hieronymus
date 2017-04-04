package ru.snatcher.hieronymus.view;

import java.util.List;

import ru.snatcher.hieronymus.presenter.vo.Language;
import ru.snatcher.hieronymus.presenter.vo.Translate;

/**
 * {@link ActivityCallback}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public interface ActivityCallback {

	void saveTranslate(Translate pTranslate);

	//TODO: Сделать сохранение в бд через activitycallback
	void saveLanguage(Language pLanguage);

	//TODO: Сделать сохранение в бд через activitycallback
	void saveLanguages(List<Language> pLanguages);

	void setSpinnerLanguagesToPreferences(String pToPreferences, int pLanguagePosition);

	int getSpinnerLanguagesFromPreferences(String pSpinnerLanguages);
}
