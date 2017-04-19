package ru.snatcher.hieronymus.other;

import android.app.Application;

import ru.snatcher.hieronymus.broadcast.NetworkChangeReceiver;
import ru.snatcher.hieronymus.broadcast.NetworkChangeReceiver.ConnectionReceiverListener;
import ru.snatcher.hieronymus.other.di.AppComponent;
import ru.snatcher.hieronymus.other.di.DaggerAppComponent;

/**
 * @author Usman Akhmedoff.
 * @version 1.5
 */
public class App extends Application {
	private static AppComponent sfAppComponent;
	private static App sfInstance;

	/**
	 * @return {@link AppComponent}
	 */
	public static AppComponent getComponent() {
		return sfAppComponent;
	}

	/**
	 * @return {@link App} instance
	 */
	public static synchronized App getInstance() {
		return sfInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		sfInstance = this;
		sfAppComponent = buildComponent();

	}

	/**
	 * @return {@link AppComponent } builder
	 */
	protected AppComponent buildComponent() {
		return DaggerAppComponent.builder().build();
	}

	/**
	 * @param pListener - setting connection listener
	 */
	public void setConnectionListener(ConnectionReceiverListener pListener) {
		NetworkChangeReceiver.connectionReceiverListener = pListener;
	}
}
