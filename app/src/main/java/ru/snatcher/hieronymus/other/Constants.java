package ru.snatcher.hieronymus.other;

/**
 * {@link Constants}
 *
 * @author Usman Akhmedoff.
 * @version 1.2
 */
public class Constants {

	/**
	 * @value TRANSLATE_ISNT_FAVOURITE - if user don't set translate how favourite
	 * @value TRANSLATE_IS_FAVOURITE - if user set translate how favourite
	 */
	public static final boolean TRANSLATE_ISNT_FAVOURITE = false;
	public static final boolean TRANSLATE_IS_FAVOURITE = true;

	/**
	 * {@value BASE_URL} - is base url to get data from API
	 */
	public static final String BASE_URL = "https://translate.yandex.net/api/v1.5/";

	public static final String UI_THREAD = "UI_THREAD";
	public static final String IO_THREAD = "IO_THREAD";

	public static final String PREFERENCES_NAME = "app_settings";
	public static final String PREFERENCES_APP_STARTED = "app_started";

	public static final String PREFERENCES_LANGUAGE_FROM_TRANSLATE = "language_from_translate";
	public static final String PREFERENCES_LANGUAGE_TO_TRANSLATE = "language_to_translate";

	public static final int PAGER_TRANSLATOR_FRAGMENT_ID = 0;
	public static final int PAGER_DASHBOARD_FRAGMENT_ID = 1;
	public static final int PAGER_SETTINGS_FRAGMENT_ID = 2;

	public static final String FRAGMENT_TRANSLATOR_TAG = "translator";
	public static final String FRAGMENT_DASHBOARD_TAG = "dashboard";
	public static final String FRAGMENT_SETTINGS_TAG = "settings";
}
