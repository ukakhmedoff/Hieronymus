package ru.snatcher.hieronymus.presenter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import ru.snatcher.hieronymus.other.BaseTest;
import ru.snatcher.hieronymus.presenter.translator.TranslatorPresenter;
import ru.snatcher.hieronymus.view.fragment.BaseView;
import ru.snatcher.hieronymus.view.fragment.translator.TranslatorFragmentView;
import rx.Subscription;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * {@link BasePresenterTest}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

public class BasePresenterTest extends BaseTest {

	protected BaseView view;
	private BasePresenter basePresenter;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		TranslatorFragmentView lvTranslatorFragmentView = mock(TranslatorFragmentView.class);
		basePresenter = new TranslatorPresenter(lvTranslatorFragmentView);
		view = lvTranslatorFragmentView;
	}

	@Test
	public void testAddSubscription() throws Exception {
		Subscription test = new TestSubscriber<>();
		basePresenter.addSubscription(test);
	}

	@Test
	public void testOnStop() throws Exception {
		Subscription test = new TestSubscriber<>();
		basePresenter.addSubscription(test);
		basePresenter.onStop();
		assertTrue(test.isUnsubscribed());
	}

	@Test
	public void testOnStopManySubscription() throws Exception {
		final int num_subscription = 100;
		ArrayList<Subscription> subscriptionList = new ArrayList<>();

		for (int i = 0; i < num_subscription; i++) {
			Subscription test = new TestSubscriber<>();
			basePresenter.addSubscription(test);
			subscriptionList.add(test);
		}

		basePresenter.onStop();

		for (Subscription subscription : subscriptionList) {
			assertTrue(subscription.isUnsubscribed());
		}
	}
}