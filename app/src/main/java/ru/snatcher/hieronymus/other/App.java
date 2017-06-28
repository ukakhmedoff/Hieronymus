package ru.snatcher.hieronymus.other;

import android.app.Application;

import ru.snatcher.hieronymus.other.broadcast.NetworkChangeReceiver;
import ru.snatcher.hieronymus.other.broadcast.NetworkChangeReceiver.ConnectionReceiverListener;
import ru.snatcher.hieronymus.other.di.AppComponent;
import ru.snatcher.hieronymus.other.di.DaggerAppComponent;

/**
 * @author Usman Akhmedoff.
 * @version 1.5
 */
public class App extends Application {
	private static AppComponent appComponent;
	private static App instance;

	/**
	 * @return {@link AppComponent}
	 */
	public static AppComponent getComponent() {
		return appComponent;
	}

	/**
	 * @return {@link App} instance
	 */
	public static synchronized App getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		appComponent = buildComponent();
	}

	/**
	 * @return {@link AppComponent } builder
	 */
	protected AppComponent buildComponent() {
		return DaggerAppComponent.builder().build();
	}

	/**
	 * @param listener - setting connection listener
	 */
	public void setConnectionListener(ConnectionReceiverListener listener) {
		NetworkChangeReceiver.connectionReceiverListener = listener;
	}
}
