package ru.snatcher.hieronymus.view.fragment.translator;

import java.util.List;

import ru.snatcher.hieronymus.model.db.Language;
import ru.snatcher.hieronymus.model.db.Translate;
import ru.snatcher.hieronymus.view.BaseView;

/**
 * {@link TranslatorFragmentView}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public interface TranslatorFragmentView extends BaseView {

	/**
	 * Translate to show
	 *
	 * @param pTranslate - to show
	 */
	void showTranslate(Translate pTranslate);

	/**
	 * Show languages in spinners
	 *
	 * @param pLanguages - list to show in spinners
	 */
	void showLanguageList(final List<Language> pLanguages);

	/**
	 * Save translate to DB
	 *
	 * @param pTranslate - for save in DB
	 */
	void saveTranslate(Translate pTranslate);

	/**
	 * Save languages to DB
	 *
	 * @param pLanguages - list for save in DB
	 */
	void saveLanguages(List<Language> pLanguages);

	/**
	 * Get langs from Presenter
	 */
	void getLangs();

	void showSelectedItem(Translate pTranslate);
}
