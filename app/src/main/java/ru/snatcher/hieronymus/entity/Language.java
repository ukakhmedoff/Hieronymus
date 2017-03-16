package ru.snatcher.hieronymus.entity;

import com.orm.dsl.Table;

/**
 * Created by akhme on 15.03.2017.
 */

@Table
public class Language {

    private Long id;

    private String fShortLang;
    private String fLang;

    public Language() {
    }

    public Language(String fShortLang) {
        this.fShortLang = fShortLang;
    }

    public Language(String fShortLang, String fLang) {
        this.fShortLang = fShortLang;
        this.fLang = fLang;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getfShortLang() {
        return fShortLang;
    }

    public void setfShortLang(String fShortLang) {
        this.fShortLang = fShortLang;
    }

    public String getfLang() {
        return fLang;
    }

    public void setfLang(String fLang) {
        this.fLang = fLang;
    }
}
