package ru.snatcher.hieronymus.view.fragment.main;

import java.util.List;

import ru.snatcher.hieronymus.db.Language;
import ru.snatcher.hieronymus.db.Translate;
import ru.snatcher.hieronymus.view.fragment.BaseView;

/**
 * {@link TranslatorFragmentView}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public interface TranslatorFragmentView extends BaseView {

	void showTranslate(Translate fTranslate);

	void showLanguagesList(final List<Language> pLanguages);

	void saveTranslate(Translate pTranslate);

	void saveLanguages(List<Language> pLanguages);
}
