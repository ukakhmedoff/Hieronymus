package ru.snatcher.hieronymus.presenter.vo;

import com.orm.SugarRecord;

/**
 * {@link Translate}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class Translate extends SugarRecord {
	private String fLang;

	private String fTranslatedText;

	private TranslateGroup fTranslateGroup;

	private boolean isBookmark;

	public Translate(final String pLang, final String pTranslatedText, final TranslateGroup pTranslateGroup, final boolean pIsBookmark) {
		fLang = pLang;
		fTranslatedText = pTranslatedText;
		fTranslateGroup = pTranslateGroup;
		isBookmark = pIsBookmark;
	}

	public String getLang() {
		return fLang;
	}

	public void setLang(final String pLang) {
		fLang = pLang;
	}

	public String getTranslatedText() {
		return fTranslatedText;
	}

	public void setTranslatedText(final String pTranslatedText) {
		fTranslatedText = pTranslatedText;
	}

	public TranslateGroup getTranslateGroup() {
		return fTranslateGroup;
	}

	public void setTranslateGroup(final TranslateGroup pTranslateGroup) {
		fTranslateGroup = pTranslateGroup;
	}

	public boolean isBookmark() {
		return isBookmark;
	}

	public void setBookmark(final boolean pBookmark) {
		isBookmark = pBookmark;
	}
}
