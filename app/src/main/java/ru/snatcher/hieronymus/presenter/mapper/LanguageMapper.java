package ru.snatcher.hieronymus.presenter.mapper;

import javax.inject.Inject;

import ru.snatcher.hieronymus.model.entity.LanguageDTO;
import ru.snatcher.hieronymus.presenter.vo.Language;
import rx.functions.Func1;

/**
 * {@link LanguageMapper}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class LanguageMapper implements Func1<LanguageDTO, Language> {

    @Inject
    LanguageMapper() {
    }

    @Override
    public Language call(LanguageDTO pLanguageDTO) {
        if (pLanguageDTO == null) {
            return null;
        }

        return new Language(pLanguageDTO.getLangs());
    }

}