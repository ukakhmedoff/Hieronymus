package ru.snatcher.hieronymus.presenter.vo;

import com.orm.SugarRecord;

/**
 * {@link TranslateGroup}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
class TranslateGroup extends SugarRecord {

	private String fGroupName;

	private int[] fTranslateIds;

	private TranslateGroup(final String pGroupName) {
		fGroupName = pGroupName;
	}

	private TranslateGroup(final String pGroupName, final int[] pTranslateIds) {
		fGroupName = pGroupName;
		fTranslateIds = pTranslateIds;
	}

	private String getGroupName() {
		return fGroupName;
	}

	private void setGroupName(final String pGroupName) {
		fGroupName = pGroupName;
	}

	private int[] getTranslateIds() {
		return fTranslateIds;
	}

	private void setTranslateIds(final int[] pTranslateIds) {
		fTranslateIds = pTranslateIds;
	}
}
