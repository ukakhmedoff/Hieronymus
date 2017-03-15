package ru.snatcher.hieronymus.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

/**
 * @author Usman Akhmedoff.
 * @version 1.0
 */

public class Languages {

	@SerializedName("dirs")
	@Expose
	private List<String> fDirs;

	@SerializedName("langs")
	@Expose
	private HashMap<String, String> fLangs;

	/**
	 * No args constructor for use in serialization
	 */
	public Languages() {
	}

	/**
	 * @param fLangs
	 * @param fDirs
	 */
	public Languages(final List<String> pDirs, final HashMap<String, String> pLangs) {
		fDirs = pDirs;
		fLangs = pLangs;
	}

	public List<String> getDirs() {
		return fDirs;
	}

	public void setDirs(List<String> pDirs) {
		this.fDirs = pDirs;
	}

	public HashMap<String, String> getLangs() {
		return fLangs;
	}

	public void setLangs(final HashMap<String, String> pLangs) {
		fLangs = pLangs;
	}
}