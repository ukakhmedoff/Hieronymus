package ru.snatcher.hieronymus.presenter;

import ru.snatcher.hieronymus.presenter.vo.Language;

/**
 * {@link MainPresenter}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

interface MainPresenter extends Presenter {

    String onLanguageSelected(Language pLanguage);

    void getLangs(String pKey);

    void getTranslates(String pKey, String pToTranslateText, String pLangs);
}
