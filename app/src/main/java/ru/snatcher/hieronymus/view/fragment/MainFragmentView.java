package ru.snatcher.hieronymus.view.fragment;

import java.util.List;
import java.util.Map;

import ru.snatcher.hieronymus.presenter.vo.Language;

/**
 * {@link MainFragmentView}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

public interface MainFragmentView extends BaseView {

    void showTranslatesList(List<String> fTranslates);

    void showLanguagesList(final List<Language> pLanguages);
}
