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

    @Inject
    Model fModel;

    @Inject
    CompositeSubscription fCompositeSubscription;

    BasePresenter() {
        App.getAppComponent().inject(this);
    }

    void addSubscription(Subscription subscription) {
        fCompositeSubscription.add(subscription);
    }

    @Override
    public void onStop() {
        fCompositeSubscription.clear();
    }

    protected abstract BaseView getView();
}