package ru.snatcher.hieronymus.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.List;

/**
 * @author Usman Akhmedoff.
 * @version 1.0
 */

public class Languages extends SugarRecord {

    private Long id;

    @SerializedName("dirs")
    @Expose
    private List<String> fDirs;

    @SerializedName("langs")
    @Expose
    private List<Language> fLangs;

    /**
     * No args constructor for use in serialization
     */
    public Languages() {
    }

    /**
     * @param fLangs
     * @param fDirs
     */
    public Languages(final List<String> pDirs, final List<Language> pLangs) {
        fDirs = pDirs;
        fLangs = pLangs;
    }

       public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getDirs() {
        return fDirs;
    }

    public void setDirs(List<String> pDirs) {
        this.fDirs = pDirs;
    }

    public List<Language> getLangs() {
        return fLangs;
    }

    public void setLangs(final List<Language> pLangs) {
        fLangs = pLangs;
    }
}