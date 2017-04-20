package ru.snatcher.hieronymus.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "TRANSLATE".
 */
@Entity
public class Translate {

	@NotNull
	private String Lang;

	@Id
	private String TranslatedText;
	private Boolean isBookmark;

	@Generated
	public Translate() {
	}

	public Translate(String TranslatedText) {
		this.TranslatedText = TranslatedText;
	}

	@Generated
	public Translate(String Lang, String TranslatedText, Boolean isBookmark) {
		this.Lang = Lang;
		this.TranslatedText = TranslatedText;
		this.isBookmark = isBookmark;
	}

	@NotNull
	public String getLang() {
		return Lang;
	}

	/**
	 * Not-null value; ensure this value is available before it is saved to the database.
	 */
	public void setLang(@NotNull String Lang) {
		this.Lang = Lang;
	}

	public String getTranslatedText() {
		return TranslatedText;
	}

	public void setTranslatedText(String TranslatedText) {
		this.TranslatedText = TranslatedText;
	}

	public Boolean getIsBookmark() {
		return isBookmark;
	}

	public void setIsBookmark(Boolean isBookmark) {
		this.isBookmark = isBookmark;
	}

}
