package ru.snatcher.hieronymus.presenter;

import java.util.List;

import javax.inject.Inject;

import ru.snatcher.hieronymus.db.Translate;
import ru.snatcher.hieronymus.other.App;
import ru.snatcher.hieronymus.view.fragment.BaseView;
import ru.snatcher.hieronymus.view.fragment.dashboard.HistoryFragmentView;

/**
 * {@link HistoryPresenterImpl}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class HistoryPresenterImpl extends BasePresenter implements HistoryPresenter {

	private HistoryFragmentView fHistoryFragmentView;

	@Inject
	HistoryPresenterImpl() {
	}

	public HistoryPresenterImpl(final HistoryFragmentView pHistoryFragmentView) {
		App.getAppComponent().inject(this);
		fHistoryFragmentView = pHistoryFragmentView;
	}

	@Override
	public void onStop() {

	}

	@Override
	protected BaseView getView() {
		return fHistoryFragmentView;
	}

	@Override
	public void getTranslatesLocal(final boolean isBookmarks, final App pApplication) {
		List<Translate> lvTranslates = pApplication.getDaoSession().getTranslateDao().loadAll();
		if (lvTranslates.size() > 0)
			fHistoryFragmentView.showTranslates(lvTranslates);
		else fHistoryFragmentView.showError("");
	}
}
