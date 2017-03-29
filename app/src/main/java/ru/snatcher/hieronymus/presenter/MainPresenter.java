package ru.snatcher.hieronymus.presenter;

import java.util.Map;

/**
 * {@link MainPresenter}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

interface MainPresenter extends Presenter {

    String onLanguageSelected(String pKey, final Map<String, String> pLangs);

    void getLangs(String pKey);

    void getTranslates(String pKey, String pToTranslateText, String pLangs);
}
