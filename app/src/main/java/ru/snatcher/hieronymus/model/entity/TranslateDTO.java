package ru.snatcher.hieronymus.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class TranslateDTO {

	@SerializedName("code")
	@Expose
	private Integer code;
	@SerializedName("lang")
	@Expose
	private String lang;
	@SerializedName("text")
	@Expose
	private List<String> text;

	/**
	 * @param texts - translated texts
	 * @param code  - response code
	 * @param lang  - lang to translated
	 */
	public TranslateDTO(Integer code, String lang, List<String> texts) {
		super();
		this.code = code;
		this.lang = lang;
		this.text = texts;
	}

	public final String getLang() {
		return lang;
	}

	public final List<String> getText() {
		return text;
	}
}