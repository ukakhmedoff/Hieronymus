package ru.snatcher.hieronymus.other;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;
import static ru.snatcher.hieronymus.other.Constants.PREFERENCES_APP_STARTED;
import static ru.snatcher.hieronymus.other.Constants.PREFERENCES_LANGUAGE_FROM_TRANSLATE;
import static ru.snatcher.hieronymus.other.Constants.PREFERENCES_LANGUAGE_TO_TRANSLATE;
import static ru.snatcher.hieronymus.other.Constants.PREFERENCES_NAME;

/**
 * {@link Prefs}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class Prefs {

	/**
	 * Initialize {@link SharedPreferences}
	 * If app isn't started we create a new preferences
	 */
	public static void initPreferences(Context pContext) {
		SharedPreferences lvSharedPreferences = pContext.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

		if (!lvSharedPreferences.getBoolean(PREFERENCES_APP_STARTED, false)) {
			lvSharedPreferences.edit().putBoolean(PREFERENCES_APP_STARTED, true)
					.putInt(PREFERENCES_LANGUAGE_FROM_TRANSLATE, 63)
					.putInt(PREFERENCES_LANGUAGE_TO_TRANSLATE, 3).apply();
		}
	}

	public static void setSpinnerLanguagesToPreferences(Context pContext, final String pToPreferences, final int pLanguagePosition) {
		SharedPreferences lvSharedPreferences = pContext.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

		lvSharedPreferences.edit().putInt(pToPreferences, pLanguagePosition).apply();
	}

	public static int getSpinnerLanguagesFromPreferences(Context pContext, final String pSpinnerLanguages) {
		return pContext.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE).getInt(pSpinnerLanguages, 0);
	}

}
