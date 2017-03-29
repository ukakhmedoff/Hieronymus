package ru.snatcher.hieronymus.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * @author Usman Akhmedoff.
 * @version 1.0
 */

public class LanguageDTO {

    @SerializedName("dirs")
    private List<String> fDirs;

    @SerializedName("langs")
    private Map<String, String> fLangs;

    /**
     * @param pLangs - languages to translate
     * @param pDirs - direct languages
     */
    public LanguageDTO(final List<String> pDirs, final Map<String, String> pLangs) {
        fDirs = pDirs;
        fLangs = pLangs;
    }

    public Map<String, String> getLangs() {
        return fLangs;
    }
}