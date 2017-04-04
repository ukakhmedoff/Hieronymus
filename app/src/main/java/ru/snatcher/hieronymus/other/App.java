package ru.snatcher.hieronymus.other;

import android.content.res.Configuration;

import com.orm.SchemaGenerator;
import com.orm.SugarApp;
import com.orm.SugarContext;
import com.orm.SugarDb;

import ru.snatcher.hieronymus.other.di.AppComponent;
import ru.snatcher.hieronymus.other.di.DaggerAppComponent;

/**
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class App extends SugarApp {
	private static AppComponent sfAppComponent;

	public static AppComponent getAppComponent() {
		return sfAppComponent;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		sfAppComponent = buildComponent();
		SugarContext.init(this);
		// create table if not exists
		SchemaGenerator schemaGenerator = new SchemaGenerator(this);
		schemaGenerator.createDatabase(new SugarDb(this).getDB());
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		SugarContext.terminate();
	}

	protected AppComponent buildComponent() {
		return DaggerAppComponent.builder().build();
	}
}
