package ru.snatcher.hieronymus;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.snatcher.hieronymus.entity.Language;
import ru.snatcher.hieronymus.entity.Languages;
import ru.snatcher.hieronymus.entity.Translate;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements TextWatcher {

    private Context context;

    private Spinner spinnerFromLang, spinnerToLang;
    private Languages languages;

    private Button btnReplace;

    private EditText editTextToTranslate;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        context = v.getContext();

        getLangs();
        initSpinners(v);
        initEditText(v);

        return v;
    }

    private void initEditText(View v) {
        editTextToTranslate = (EditText) v.findViewById(R.id.textToTranslate);
        editTextToTranslate.addTextChangedListener(this);
    }

    private void initSpinners(View v) {
        spinnerFromLang = (Spinner) v.findViewById(R.id.language_1);
        spinnerToLang = (Spinner) v.findViewById(R.id.language_2);
        //Ставим в наш спиннер адаптер
        spinnerToLang.setAdapter(getLanguageArrayAdapter());
        //Выбираем какой-нибудь язык по-умолчанию
        spinnerToLang.setSelection(1);
        //Ставим в наш спиннер адаптер
        spinnerFromLang.setAdapter(getLanguageArrayAdapter());
        //Выбираем какой-нибудь язык по-умолчанию
        spinnerFromLang.setSelection(1);
    }

    private void initTranslate(String text, String langFromTranslate, String langToTranslate) {
        App
                .getTranslateService()
                .translate(context.getResources().getString(R.string.key), text, langFromTranslate + "-" + langToTranslate)
                .enqueue(new Callback<Translate>() {
                    @Override
                    public void onResponse(final Call<Translate> call, final Response<Translate> response) {
                        Translate translatedText = response.body();

                    }

                    @Override
                    public void onFailure(final Call<Translate> call, final Throwable t) {

                    }

                });

    }

    private void getLangs() {

        final List<String> lvDirs = new ArrayList<>();
        final List<Language> lvLangs = new ArrayList<>();

        App
                .getTranslateService()
                .getLangs(context.getResources().getString(R.string.key), "ru")
                .enqueue(
                        new Callback<Languages>() {
                            @Override
                            public void onResponse(Call<Languages> call, Response<Languages> response) {
                                lvDirs.addAll(response.body().getDirs());
                                lvLangs.addAll(response.body().getLangs());
                                Log.d("LANG ", lvLangs.get(1).toString());
                            }

                            @Override
                            public void onFailure(Call<Languages> call, Throwable t) {

                            }
                        });
        languages = new Languages(lvDirs, lvLangs);
        languages.save();

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        initTranslate(editTextToTranslate.getText().toString(), spinnerLang(spinnerFromLang), spinnerLang(spinnerToLang));
    }

    @NonNull
    private ArrayAdapter<String> getLanguageArrayAdapter() {
        List<String> lvListLang = new ArrayList<>();

        for (Language lang : languages.listAll(Language.class)) {
            lvListLang.add(lang.getfLang());
        }

        // Настраиваем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item,
                lvListLang);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private String spinnerLang(Spinner spinnerLang) {
        return languages.findById(Language.class, spinnerLang.getSelectedItemId()).getfShortLang();
    }
}