package ru.snatcher.hieronymus.other;

import android.app.Application;

import ru.snatcher.hieronymus.other.di.AppComponent;
import ru.snatcher.hieronymus.other.di.DaggerAppComponent;

/**
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class App extends Application {
    private static AppComponent sfAppComponent;

    public static AppComponent getAppComponent() {
        return sfAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sfAppComponent = buildComponent();
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder().build();
    }
}
