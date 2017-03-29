package ru.snatcher.hieronymus.presenter;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ru.snatcher.hieronymus.other.App;
import ru.snatcher.hieronymus.presenter.mapper.LanguageMapper;
import ru.snatcher.hieronymus.presenter.mapper.TranslateMapper;
import ru.snatcher.hieronymus.presenter.vo.Language;
import ru.snatcher.hieronymus.presenter.vo.Translate;
import ru.snatcher.hieronymus.view.fragment.BaseView;
import ru.snatcher.hieronymus.view.fragment.MainFragmentView;
import rx.Observer;
import rx.Subscription;

/**
 * {@link MainPresenterImpl}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

public class MainPresenterImpl extends BasePresenter implements MainPresenter {

    private static final String BUNDLE_LANGUAGES_LIST_KEY = "BUNDLE_LANGUAGES_LIST_KEY";
    private static final String BUNDLE_TRANSLATE_LIST_KEY = "BUNDLE_TRANSLATE_LIST_KEY";

    @Inject
    LanguageMapper fLanguageMapper;

    @Inject
    TranslateMapper fTranslateMapper;

    private MainFragmentView fMainFragmentView;
    private Map<String, String> fLanguages;
    private List<String> fTranslates;

    @Inject
    public MainPresenterImpl() {
    }

    public MainPresenterImpl(final MainFragmentView pMainFragmentView) {
        App.getAppComponent().inject(this);
        fMainFragmentView = pMainFragmentView;
    }

    @Override
    protected BaseView getView() {
        return fMainFragmentView;
    }

    public void onCreateView(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            fLanguages = (Map<String, String>) savedInstanceState.getSerializable(BUNDLE_LANGUAGES_LIST_KEY);
            fTranslates = (List<String>) savedInstanceState.getSerializable(BUNDLE_TRANSLATE_LIST_KEY);
        }
        if (isRepoListNotEmpty()) {
            fMainFragmentView.showLanguagesList(fLanguages);
            fMainFragmentView.showTranslatesList(fTranslates);
        }
    }

    private boolean isRepoListNotEmpty() {
        return (fTranslates != null && !fTranslates.isEmpty()) && (fLanguages != null && !fLanguages.isEmpty());
    }

    @Override
    public void onStop() {

    }


    public void onSaveInstanceState(Bundle outState) {
        if (isRepoListNotEmpty()) {
            outState.putSerializable(BUNDLE_LANGUAGES_LIST_KEY, new HashMap<>(fLanguages));
            outState.putSerializable(BUNDLE_TRANSLATE_LIST_KEY, new ArrayList<>(fTranslates));
        }
    }

    @Override
    public String onLanguageSelected(final String pValue, final Map<String, String> pLangs) {
        return fModel.getLangKey(pValue, pLangs);
    }

    @Override
    public void getLangs(String pKey) {
        Subscription lvSubscription = fModel.getLangs(pKey).map(fLanguageMapper).subscribe(new Observer<Language>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                fMainFragmentView.showError(e.getLocalizedMessage());
            }

            @Override
            public void onNext(Language pLanguage) {
                if (pLanguage != null) {
                    fLanguages = pLanguage.getLangs();
                    fMainFragmentView.showLanguagesList(fLanguages);
                } else {
                    fMainFragmentView.showError("Check your Internet connection!");
                }
            }
        });
        addSubscription(lvSubscription);
    }

    @Override
    public void getTranslates(final String pKey, final String pToTranslateText, String pLangs) {
        Subscription lvSubscription = fModel.getTranslatedText(pKey, pToTranslateText, pLangs).map(fTranslateMapper).subscribe(new Observer<Translate>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                fMainFragmentView.showError(e.getLocalizedMessage());
            }

            @Override
            public void onNext(Translate pTranslate) {
                if (pTranslate != null) {
                    fTranslates = pTranslate.getTranslates();
                    fMainFragmentView.showTranslatesList(fTranslates);
                } else {
                    fMainFragmentView.showError("Что-то пошло не так :(, скорее всего что-то не так с ключом!");
                }
            }
        });
        addSubscription(lvSubscription);
    }
}
