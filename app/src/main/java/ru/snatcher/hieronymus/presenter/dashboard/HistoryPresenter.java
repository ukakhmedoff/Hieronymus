package ru.snatcher.hieronymus.presenter.dashboard;

import javax.inject.Inject;

import ru.snatcher.hieronymus.other.App;
import ru.snatcher.hieronymus.presenter.BasePresenter;
import ru.snatcher.hieronymus.view.BaseView;
import ru.snatcher.hieronymus.view.fragment.dashboard.HistoryFragmentView;

/**
 * {@link HistoryPresenter}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class HistoryPresenter extends BasePresenter {

	private HistoryFragmentView fHistoryFragment;

	@Inject
	public HistoryPresenter() {
	}

	@Override
	public BaseView getView() {
		return fHistoryFragment;
	}

	public void onCreate(HistoryFragmentView pHistoryFragment) {
		App.getComponent().inject(this);
		fHistoryFragment = pHistoryFragment;
	}

	public void getTranslates(boolean pFavourite, App pApp) {
		fHistoryFragment.showTranslates(fModel.getTranslates(pFavourite, pApp));
	}
}