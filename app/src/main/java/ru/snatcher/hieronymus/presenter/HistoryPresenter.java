package ru.snatcher.hieronymus.presenter;

import ru.snatcher.hieronymus.other.App;

/**
 * {@link HistoryPresenter}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

public interface HistoryPresenter extends Presenter {

	void getTranslatesLocal(boolean isBookmarks, App pApplication);
}
