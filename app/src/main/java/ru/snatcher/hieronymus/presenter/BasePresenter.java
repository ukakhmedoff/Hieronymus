package ru.snatcher.hieronymus.presenter;

import javax.inject.Inject;

import ru.snatcher.hieronymus.model.Model;
import ru.snatcher.hieronymus.other.App;
import ru.snatcher.hieronymus.view.fragment.BaseView;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * {@link BasePresenter}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public abstract class BasePresenter implements Presenter {

	protected static final String BUNDLE_TRANSLATE_LIST_KEY = "BUNDLE_TRANSLATE_LIST_KEY";

	@Inject
	protected Model fModel;

	@Inject
	CompositeSubscription fCompositeSubscription;

	protected BasePresenter() {
		App.getComponent().inject(this);
	}

	protected void addSubscription(Subscription subscription) {
		fCompositeSubscription.add(subscription);
	}

	public abstract BaseView getView();

	@Override
	public void onStop() {
		fCompositeSubscription.clear();

	}
}