package ru.snatcher.hieronymus.presenter;

import android.content.Context;

import javax.inject.Inject;

import ru.snatcher.hieronymus.other.App;
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
	public final BaseView getView() {
		return fHistoryFragment;
	}

	public final void onCreate(HistoryFragmentView pHistoryFragment) {
		App.getComponent().inject(this);
		fHistoryFragment = pHistoryFragment;
	}

	public final void getTranslates(boolean pFavourite, Context pContext) {
		fHistoryFragment.showTranslates(fModel.getTranslates(pFavourite, pContext));
	}
}