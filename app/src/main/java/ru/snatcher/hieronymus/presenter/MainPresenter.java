package ru.snatcher.hieronymus.presenter;

import java.util.List;

import ru.snatcher.hieronymus.db.Language;
import ru.snatcher.hieronymus.db.Translate;
import ru.snatcher.hieronymus.other.App;

/**
 * {@link MainPresenter}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
interface MainPresenter extends Presenter {

	String onLanguageSelected(Language pLanguage);

	void getLangs(String pKey);

	void getTranslatesRemote(String pKey, String pToTranslateText, String pLangs);

	void saveTranslate(Translate pTranslate, App pApp);

	void saveLanguages(List<Language> pLanguages, App pApp);
}
