package ru.snatcher.hieronymus.model.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.snatcher.hieronymus.other.Constants;

/**
 * {@link ApiTranslateModule} is implementation for {@link ApiTranslateInterface}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class ApiTranslateModule {
    private static ApiTranslateInterface sfApiTranslateInterface;
    private static Retrofit sfRetrofit;

    public static ApiTranslateInterface getApiInterfaceInstance() {
        if (sfApiTranslateInterface == null)
            sfApiTranslateInterface = getRetrofitInstance().create(ApiTranslateInterface.class);
        return sfApiTranslateInterface;
    }

    private static Retrofit getRetrofitInstance() {
        if (sfRetrofit == null) sfRetrofit = createRetrofit();
        return sfRetrofit;
    }

    private static Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
}
