package ru.snatcher.hieronymus.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class LanguageDTO {

    @SerializedName("langs")
    private Map<String, String> fLangs;

    /**
     * @param pLangs - languages to translate
     */
    public LanguageDTO(final Map<String, String> pLangs) {
        fLangs = pLangs;
    }

    public Map<String, String> getLangs() {
        return fLangs;
    }
}