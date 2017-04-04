package ru.snatcher.hieronymus.presenter.vo;

import com.orm.SugarRecord;

/**
 * {@link Language}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class Language extends SugarRecord {

	private String fLangKey;
	private String fLangValue;

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
}
