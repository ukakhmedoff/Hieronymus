package ru.snatcher.hieronymus.presenter;

import android.content.Context;

import javax.inject.Inject;

import ru.snatcher.hieronymus.model.Model;
import ru.snatcher.hieronymus.model.db.Translate;
import ru.snatcher.hieronymus.other.App;
import ru.snatcher.hieronymus.view.BaseView;
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

	public void saveTranslate(final Translate pTranslate, Context pContext) {
		fModel.saveTranslate(pTranslate, pContext);
	}

	public boolean getTranslateFavourite(final Translate pTranslate, App pApp) {
		return fModel.getTranslateIsFavourite(pTranslate, pApp);
	}

	@Override
	public void onStop() {
		fCompositeSubscription.clear();
	}
}