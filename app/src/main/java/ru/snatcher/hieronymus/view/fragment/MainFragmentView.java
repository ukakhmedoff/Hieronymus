package ru.snatcher.hieronymus.view.fragment;

import java.util.List;

import ru.snatcher.hieronymus.presenter.vo.Language;
import ru.snatcher.hieronymus.presenter.vo.Translate;

/**
 * {@link MainFragmentView}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public interface MainFragmentView extends BaseView {

	void showTranslate(Translate fTranslate);

	void showLanguagesList(final List<Language> pLanguages);

	void saveTranslate(Translate pTranslate);

	void saveLanguage(Language pLanguage);

	void saveLanguages(List<Language> pLanguages);
}
