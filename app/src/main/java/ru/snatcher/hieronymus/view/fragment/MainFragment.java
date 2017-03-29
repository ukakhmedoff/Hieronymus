package ru.snatcher.hieronymus.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.snatcher.hieronymus.R;
import ru.snatcher.hieronymus.other.di.view.DaggerViewComponent;
import ru.snatcher.hieronymus.other.di.view.ViewComponent;
import ru.snatcher.hieronymus.other.di.view.ViewDynamicModule;
import ru.snatcher.hieronymus.presenter.MainPresenterImpl;
import ru.snatcher.hieronymus.presenter.Presenter;
import ru.snatcher.hieronymus.view.adapter.RecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 * <p>
 * {@link MainFragment} is fragment, where user enters sentences and gets translates
 */
public class MainFragment extends BaseFragment implements MainFragmentView {

    @BindView(R.id.spinner_language_from)
    Spinner fSpinnerFromLang;
    @BindView(R.id.spinner_language_to)
    Spinner fSpinnerToLang;
    @BindView(R.id.textToTranslate)
    EditText fTextToTranslate;

    @Inject
    MainPresenterImpl fMainPresenter;

    private ViewComponent viewComponent;

    private View fView;
    private Map<String, String> fLangs = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (viewComponent == null) {
            viewComponent = DaggerViewComponent.builder()
                    .viewDynamicModule(new ViewDynamicModule(this))
                    .build();
        }
        viewComponent.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, fView);
        fMainPresenter.onCreateView(savedInstanceState);

        fMainPresenter.getLangs(fView.getResources().getString(R.string.key));

        final CompositeDisposable lvDisposable = new CompositeDisposable();

        Disposable inputWatcher = RxTextView.textChanges(fTextToTranslate)
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(charSequence -> prepareAndProcessTranslationRequest());
        lvDisposable.add(inputWatcher);
        return fView;
    }

    private void prepareAndProcessTranslationRequest() {
        String inputText = fTextToTranslate.getText().toString().trim();
        if (inputText.length() == 0) {
            return;
        }

        fMainPresenter.getTranslates(fView.getResources().getString(R.string.key), inputText, getSpinnersLang());
    }

    public void setViewComponent(ViewComponent viewComponent) {
        this.viewComponent = viewComponent;
    }

    private ArrayAdapter<String> getLanguageArrayAdapter() {
        List<String> lvLangs = new ArrayList<>(fLangs.values());
        Collections.sort(lvLangs);

        // Настраиваем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                fView.getContext(),
                android.R.layout.simple_spinner_item,
                lvLangs
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private String getSpinnersLang() {
        return getSpinnerLang(fSpinnerFromLang) + "-" + getSpinnerLang(fSpinnerToLang);
    }

    private String getSpinnerLang(Spinner pSpinnerLang) {
        return fMainPresenter.onLanguageSelected(pSpinnerLang.getSelectedItem().toString(), fLangs);
    }

    public void showError(final String error) {
        Toast.makeText(fView.getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Presenter getPresenter() {
        return fMainPresenter;
    }

    @Override
    public void showTranslatesList(final List<String> pTranslates) {
        final RecyclerView lvRecyclerViewTranslates = (RecyclerView) fView.findViewById(R.id.recycler_main_translates);
        final RecyclerViewAdapter lvRecyclerViewAdapter = new RecyclerViewAdapter(pTranslates, fMainPresenter);

        lvRecyclerViewTranslates.
                setLayoutManager(new LinearLayoutManager(fView.getContext(), LinearLayoutManager.VERTICAL, false));
        lvRecyclerViewTranslates.setAdapter(lvRecyclerViewAdapter);

    }

    @Override
    public void showLanguagesList(final Map<String, String> pLanguages) {
        fLangs = pLanguages;
        //Ставим в наш спиннер адаптер
        fSpinnerToLang.setAdapter(getLanguageArrayAdapter());
        fSpinnerFromLang.setAdapter(getLanguageArrayAdapter());

        //Выбираем какой-нибудь язык по-умолчанию
        fSpinnerToLang.setSelection(1);
        fSpinnerFromLang.setSelection(1);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        fMainPresenter.onSaveInstanceState(outState);
    }
}