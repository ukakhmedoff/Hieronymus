package ru.snatcher.hieronymus.view;

/**
 * {@link ActivityCallback}
 *
 * @author Usman Akhmedoff.
 * @version 1.1
 */
public interface ActivityCallback extends BaseView {

	/**
	 * Save selected languages to settings
	 *
	 * @param pToPreferences    - What setting to change
	 * @param pLanguagePosition - what language tu put in setting
	 */
	void setSpinnerLanguagesToPreferences(String pToPreferences, int pLanguagePosition);

	/**
	 * Get language
	 *
	 * @param pSpinnerLanguages - What setting to get
	 * @return language id
	 */
	int getSpinnerLanguagesFromPreferences(String pSpinnerLanguages);
}
