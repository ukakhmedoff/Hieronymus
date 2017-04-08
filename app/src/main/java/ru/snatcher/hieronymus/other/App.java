package ru.snatcher.hieronymus.other;

import android.app.Application;
import android.content.res.Configuration;

import org.greenrobot.greendao.database.Database;

import ru.snatcher.hieronymus.db.DaoMaster;
import ru.snatcher.hieronymus.db.DaoSession;
import ru.snatcher.hieronymus.other.di.AppComponent;
import ru.snatcher.hieronymus.other.di.DaggerAppComponent;

/**
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class App extends Application {
	private static AppComponent sfAppComponent;
	private DaoSession fDaoSession;

	public static AppComponent getAppComponent() {
		return sfAppComponent;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		sfAppComponent = buildComponent();

		DaoMaster.DevOpenHelper lvDevOpenHelper = new DaoMaster.DevOpenHelper(this, "app_database");
		Database lvWritableDb = lvDevOpenHelper.getWritableDb();
		fDaoSession = new DaoMaster(lvWritableDb).newSession();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	protected AppComponent buildComponent() {
		return DaggerAppComponent.builder().build();
	}

	public DaoSession getDaoSession() {
		return fDaoSession;
	}
}
