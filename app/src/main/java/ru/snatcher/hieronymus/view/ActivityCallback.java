package ru.snatcher.hieronymus.view;

/**
 * {@link ActivityCallback}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public interface ActivityCallback {

	void setSpinnerLanguagesToPreferences(String pToPreferences, int pLanguagePosition);

	int getSpinnerLanguagesFromPreferences(String pSpinnerLanguages);

}
