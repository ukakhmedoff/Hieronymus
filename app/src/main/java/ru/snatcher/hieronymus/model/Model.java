package ru.snatcher.hieronymus.model;

import ru.snatcher.hieronymus.model.entity.LanguageDTO;
import ru.snatcher.hieronymus.model.entity.TranslateDTO;
import ru.snatcher.hieronymus.presenter.vo.Language;
import rx.Observable;

/**
 * {@link Model}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public interface Model {

	Observable<LanguageDTO> getLangs(String pKey);

	Observable<TranslateDTO> getTranslatedText(String pKey, String pTextToTranslate, String pLangs);

	String getLangKey(Language pLanguage);
}
