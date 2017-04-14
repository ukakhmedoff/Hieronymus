package ru.snatcher.hieronymus.model.api;

import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.snatcher.hieronymus.model.entity.LanguageDTO;
import ru.snatcher.hieronymus.model.entity.TranslateDTO;
import rx.Observable;

/**
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public interface ApiTranslateInterface {

	// Перевод текста
	@GET("translate")
	Observable<TranslateDTO> getTranslatedText(@Query("key") String key, @Query("text") String text, @Query("lang") String lang);

	// Получение списка языков
	@GET("getLangs")
	Observable<LanguageDTO> getLangs(@Query("key") String key, @Query("ui") String ui_lang);
}
