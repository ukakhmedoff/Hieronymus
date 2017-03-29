package ru.snatcher.hieronymus.model.repository;

import io.reactivex.Observable;
import ru.snatcher.hieronymus.model.entity.LanguageDTO;
import ru.snatcher.hieronymus.model.entity.TranslateDTO;

/**
 * {@link TranslationRepository}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

interface TranslationRepository {

    Observable<TranslateDTO> translate(String text, String lang);

    Observable<LanguageDTO> getLanguages();
}
