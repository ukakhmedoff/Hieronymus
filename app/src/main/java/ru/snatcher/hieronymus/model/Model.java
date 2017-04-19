package ru.snatcher.hieronymus.model;

import android.content.Context;

import java.util.List;

import ru.snatcher.hieronymus.db.Language;
import ru.snatcher.hieronymus.db.Translate;
import ru.snatcher.hieronymus.model.entity.LanguageDTO;
import ru.snatcher.hieronymus.model.entity.TranslateDTO;
import rx.Observable;

/**
 * {@link Model}
 * Here we'll get translates and langs
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public interface Model {

	Observable<LanguageDTO> getRemoteLangs(String pKey, String pUiLang);

	Observable<TranslateDTO> getTranslatedText(String pKey, String pTextToTranslate, String pLangs);

	void saveTranslate(Translate pTranslate, Context pContext);

	void saveLanguages(List<Language> pLanguages, Context pContext);

	List<Translate> getTranslates(boolean pFavourite, Context pContext);

	List<Language> getLocalLanguages(Context pContext);

	boolean getTranslateIsFavourite(Translate pTranslate, Context pContext);
}
