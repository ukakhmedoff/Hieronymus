package ru.snatcher.hieronymus.other;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import ru.snatcher.hieronymus.broadcast.NetworkChangeReceiver;
import ru.snatcher.hieronymus.broadcast.NetworkChangeReceiver.ConnectionReceiverListener;
import ru.snatcher.hieronymus.db.DaoMaster;
import ru.snatcher.hieronymus.db.DaoSession;
import ru.snatcher.hieronymus.other.di.AppComponent;
import ru.snatcher.hieronymus.other.di.DaggerAppComponent;

/**
 * @author Usman Akhmedoff.
 * @version 1.5
 */
public class App extends Application {
	private static AppComponent sfAppComponent;
	private static App sfInstance;
	private DaoSession fDaoSession;

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

		DaoMaster.DevOpenHelper lvDevOpenHelper = new DaoMaster.DevOpenHelper(this, "app_database");
		Database lvWritableDb = lvDevOpenHelper.getWritableDb();
		fDaoSession = new DaoMaster(lvWritableDb).newSession();
	}

	/**
	 * @return {@link AppComponent } builder
	 */
	protected AppComponent buildComponent() {
		return DaggerAppComponent.builder().build();
	}

	/**
	 * @return {@link DaoSession} to save DB data
	 */
	public DaoSession getDaoSession() {
		return fDaoSession;
	}

	/**
	 * @param pListener - setting connection listener
	 */
	public void setConnectionListener(ConnectionReceiverListener pListener) {
		NetworkChangeReceiver.connectionReceiverListener = pListener;
	}
}
