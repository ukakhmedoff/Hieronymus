package ru.snatcher.hieronymus.view.fragment.dashboard;

import java.util.List;

import ru.snatcher.hieronymus.model.db.Translate;
import ru.snatcher.hieronymus.view.BaseView;

/**
 * {@link HistoryFragmentView}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

public interface HistoryFragmentView extends BaseView {

	void showTranslates(List<Translate> pTranslates);

	void getTranslates();

	void notifyDataSetChanged();
}
