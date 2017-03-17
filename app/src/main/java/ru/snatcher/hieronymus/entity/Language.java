package ru.snatcher.hieronymus.entity;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.List;
import java.util.Map;

/**
 * @author Usman Akhmedoff.
 * @version 1.0
 */

public class Language extends SugarRecord {

    @SerializedName("dirs")
    private List<String> fDirs;

    @SerializedName("langs")
    private Map<String, String> fLangs;

    /**
     * @param fLangs
     * @param fDirs
     */
    public Language(final List<String> pDirs, final Map<String, String> pLangs) {
        fDirs = pDirs;
        fLangs = pLangs;
    }

    public Language(Map<String, String> fLangs) {
        this.fLangs = fLangs;
    }

    public List<String> getfDirs() {
        return fDirs;
    }

    public void setfDirs(List<String> fDirs) {
        this.fDirs = fDirs;
    }

    public Map<String, String> getLangs() {
        return fLangs;
    }

    public void setLangs(final Map<String, String> pLangs) {
        fLangs = pLangs;
    }
}