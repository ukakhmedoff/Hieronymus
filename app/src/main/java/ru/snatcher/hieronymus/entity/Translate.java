package ru.snatcher.hieronymus.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Table;

import java.util.List;

/**
 * @author Usman Akhmedoff.
 * @version 1.0
 */

@Table
public class Translate {

    private Long id;

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("text")
    @Expose
    private List<String> text = null;

    /**
     * No args constructor for use in serialization
     */
    public Translate() {
    }

    /**
     * @param text
     * @param code
     * @param lang
     */
    public Translate(Integer code, String lang, List<String> text) {
        super();
        this.code = code;
        this.lang = lang;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

}