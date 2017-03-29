package ru.snatcher.hieronymus.presenter.vo;

import java.util.Map;

/**
 * {@link Language}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

public class Language {
    private Map<String, String> fLangs;

    public Language(final Map<String, String> pLangs) {
        fLangs = pLangs;
    }

    public Map<String, String> getLangs() {
        return fLangs;
    }
}
