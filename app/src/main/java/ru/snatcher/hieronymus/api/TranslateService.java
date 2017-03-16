package ru.snatcher.hieronymus.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.snatcher.hieronymus.entity.Languages;
import ru.snatcher.hieronymus.entity.Translate;

/**
 * @author Usman Akhmedoff.
 * @version 1.0
 */

public interface TranslateService {

    // Перевод текста
    @GET("api/v1.5/tr.json/translate")
    Call<Translate> translate(@Query("key") String key, @Query("text") String text, @Query("lang") String lang);

    // Получение списка языков
    @GET("api/v1.5/tr.json/getLang")
    Call<Languages> getLangs(@Query("key") String key, @Query("ui") String ui_lang);

}
