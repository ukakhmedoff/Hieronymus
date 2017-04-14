package ru.snatcher.hieronymus.model;

import java.util.List;

import ru.snatcher.hieronymus.model.db.Language;
import ru.snatcher.hieronymus.model.db.Translate;
import ru.snatcher.hieronymus.model.entity.LanguageDTO;
import ru.snatcher.hieronymus.model.entity.TranslateDTO;
import ru.snatcher.hieronymus.other.App;
import rx.Observable;

/**
 * {@link Model}
 * Here we'll get translates and langs
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public interface Model {

	Observable<LanguageDTO> getLangs(String pKey, String pUiLang);

	Observable<TranslateDTO> getTranslatedText(String pKey, String pTextToTranslate, String pLangs);

	String getLangKey(Language pLanguage);

	void saveTranslate(Translate pTranslate, App pApp);

	void saveLanguages(List<Language> pLanguages, App pApp);

	List<Translate> getTranslates(boolean pFavourite, App pApp);

	List<Language> getLocalLanguages(App pApp);
}
