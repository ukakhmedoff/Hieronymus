package ru.snatcher.hieronymus.other;

import android.content.Context;
import android.content.SharedPreferences;

import org.greenrobot.greendao.database.Database;

import ru.snatcher.hieronymus.model.db.DaoMaster;
import ru.snatcher.hieronymus.model.db.DaoSession;

import static android.content.Context.MODE_PRIVATE;
import static ru.snatcher.hieronymus.other.Constants.PREFERENCES_APP_STARTED;
import static ru.snatcher.hieronymus.other.Constants.PREFERENCES_LANGUAGE_FROM_TRANSLATE;
import static ru.snatcher.hieronymus.other.Constants.PREFERENCES_LANGUAGE_TO_TRANSLATE;
import static ru.snatcher.hieronymus.other.Constants.PREFERENCES_NAME;

/**
 * {@link Utils}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class Utils {

	/**
	 * @param pContext - context for DaoMaster
	 * @return {@link DaoSession} to save DB data
	 */
	public static DaoSession getDaoSession(Context pContext) {

		DaoMaster.DevOpenHelper lvDevOpenHelper = new DaoMaster.DevOpenHelper(pContext, "app_database");
		Database lvWritableDb = lvDevOpenHelper.getWritableDb();
		return new DaoMaster(lvWritableDb).newSession();
	}

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
