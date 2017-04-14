package ru.snatcher.hieronymus.presenter.dashboard;

import ru.snatcher.hieronymus.other.App;
import ru.snatcher.hieronymus.presenter.BasePresenter;
import ru.snatcher.hieronymus.view.BaseView;
import ru.snatcher.hieronymus.view.fragment.dashboard.DashboardFragment;

/**
 * {@link DashboardPresenter}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

public class DashboardPresenter extends BasePresenter {

	private DashboardFragment fDashboardFragment;

	@Override
	public BaseView getView() {
		return null;
	}

	private DashboardPresenter(final DashboardFragment pDashboardFragment) {
		App.getComponent().inject(this);

		fDashboardFragment = pDashboardFragment;
	}

}
