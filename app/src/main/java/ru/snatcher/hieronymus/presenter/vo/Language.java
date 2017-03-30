package ru.snatcher.hieronymus.presenter.vo;

/**
 * {@link Language}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class Language {

    private String fLangKey;
    private String fLangValue;

    private int fChecked;

    public Language(final String pLangKey, final String pLangValue) {
        fLangKey = pLangKey;
        fLangValue = pLangValue;
    }

    public String getLangKey() {
        return fLangKey;
    }

    public void setLangKey(final String pLangKey) {
        fLangKey = pLangKey;
    }

    public String getLangValue() {
        return fLangValue;
    }

    public void setLangValue(final String pLangValue) {
        fLangValue = pLangValue;
    }

    public int getChecked() {
        return fChecked;
    }

    public void setChecked(final int pChecked) {
        fChecked = pChecked;
    }
}
