package ru.snatcher.hieronymus;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.snatcher.hieronymus.presenter.api.TranslateService;

/**
 * @author Usman Akhmedoff.
 * @version 1.0
 */

public class App extends Application {

    private static TranslateService fTranslateService;

    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://translate.yandex.net/api/v1.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fTranslateService = retrofit.create(TranslateService.class);
    }

    public static TranslateService getTranslateService() {
        return fTranslateService;
    }
}
