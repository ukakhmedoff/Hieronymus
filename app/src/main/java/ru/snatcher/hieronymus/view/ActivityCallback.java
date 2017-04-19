package ru.snatcher.hieronymus.view;

import ru.snatcher.hieronymus.db.Translate;

/**
 * {@link ActivityCallback}
 *
 * @author Usman Akhmedoff.
 * @version 1.1
 */
public interface ActivityCallback {

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


	/**
	 * Will called if user clicked item from Dashboard lists
	 *
	 * @param pTranslate - item, clicked
	 */
	void onTranslatesItemClicked(Translate pTranslate);
}
