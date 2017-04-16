package ru.snatcher.hieronymus.presenter.mapper;

import javax.inject.Inject;

import ru.snatcher.hieronymus.db.Translate;
import ru.snatcher.hieronymus.model.entity.TranslateDTO;
import ru.snatcher.hieronymus.other.Constants;
import rx.functions.Func1;

/**
 * {@link TranslateMapper}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class TranslateMapper implements Func1<TranslateDTO, Translate> {

	@Inject
	TranslateMapper() {
	}

	@Override
	public Translate call(TranslateDTO pTranslateDTO) {
		if (pTranslateDTO == null) return null;

		return new Translate(pTranslateDTO.getLang(), pTranslateDTO.getText().get(0), Constants.TRANSLATE_ISNT_FAVOURITE);
	}

}