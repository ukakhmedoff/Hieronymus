package ru.snatcher.hieronymus.presenter.vo;

import java.util.List;

/**
 * {@link Translate}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

public class Translate {

    private String fLang;
    private List<String> fTranslates;

    public Translate(final String pLang, final List<String> pTranslates) {
        fLang = pLang;
        fTranslates = pTranslates;
    }

    public Translate(final List<String> pTranslates) {
        fTranslates = pTranslates;
    }

    public String getLang() {
        return fLang;
    }

    public List<String> getTranslates() {
        return fTranslates;
    }
}
