package ru.snatcher.hieronymus.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.snatcher.hieronymus.R;
import ru.snatcher.hieronymus.entity.Language;
import ru.snatcher.hieronymus.entity.Translate;
import ru.snatcher.hieronymus.presenter.adapter.RecyclerViewAdapter;

import static ru.snatcher.hieronymus.App.getTranslateService;

/**
 * A simple {@link Fragment} subclass.
 * <p>
 * {@link MainFragment} is fragment, where user enters sentences and gets translates
 */
public class MainFragment extends Fragment implements TextWatcher {

    private Context context;

    private RecyclerViewAdapter lvRecyclerViewAdapter;

    private Spinner spinnerFromLang, spinnerToLang;
    private Button btnReplace;
    private EditText editTextToTranslate;

    private final Map<String, String> fLangs = new ArrayMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        context = v.getContext();

        getLangs(v);

        initEditText(v);

        initRecyclerView(v);
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
        spinnerFromLang.setAdapter(getLanguageArrayAdapter());

        //Выбираем какой-нибудь язык по-умолчанию
        spinnerToLang.setSelection(1);
        spinnerFromLang.setSelection(1);
    }

    private void initTranslate(String text, String langsTranslate) {
        final List<String> lvTranslates = new ArrayList<>();
        getTranslateService()
                .translate(context.getResources().getString(R.string.key), text, langsTranslate)
                .enqueue(new Callback<Translate>() {
                    @Override
                    public void onResponse(final Call<Translate> call, final Response<Translate> response) {
                        lvTranslates.addAll(response.body().getTexts());
                        Log.d("TRANSLATE", "onResponse: " + lvTranslates.get(0));
                        setAdapterRecyclerView(lvTranslates);

                    }

                    @Override
                    public void onFailure(final Call<Translate> call, final Throwable t) {


                    }

                });

    }

    private void initRecyclerView(View pView) {

        final RecyclerView lvRecyclerViewTranslates = (RecyclerView) pView.findViewById(R.id.recycler_main_translates);
        lvRecyclerViewAdapter = new RecyclerViewAdapter();
        lvRecyclerViewTranslates.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        lvRecyclerViewTranslates.setAdapter(lvRecyclerViewAdapter);
    }

    private boolean getLangs(final View pV) {
        getTranslateService()
                .getLangs(context.getResources().getString(R.string.key), "ru")
                .enqueue(
                        new Callback<Language>() {
                            @Override
                            public void onResponse(Call<Language> call, Response<Language> response) {
                                Log.d("LANG ", response.toString());
                                if (response.isSuccessful()) {
                                    fLangs.putAll(response.body().getLangs());
                                    initSpinners(pV);
                                    Log.d("TAG", "onResponse: " + Arrays.toString(fLangs.values().toArray()));
                                } else {
                                    Log.d("TAG", "onResponse: " + "error");
                                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Language> call, Throwable t) {
                                Log.d("TAG", "onResponse: " + t.getMessage());
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
        return true;

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        initTranslate(editTextToTranslate.getText().toString(), getSpinnerLang(spinnerFromLang) + "-" + getSpinnerLang(spinnerToLang));
    }

    @NonNull
    private ArrayAdapter<String> getLanguageArrayAdapter() {

        List<String> lvLangs = new ArrayList<>(fLangs.values());

        Log.d("TAG", "getLanguageArrayAdapter: " + lvLangs.size());

        // Настраиваем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, lvLangs
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private String getSpinnerLang(Spinner spinnerLang) {
        List<String> lvKeys = new ArrayList<>(fLangs.keySet());
        String lvSelectedLang = spinnerLang.getSelectedItem().toString();
        String lvLangKey = null;
        for (String key : lvKeys) {
            String lvLangFromKey = fLangs.get(key);
            if (key != null) {
                if (lvSelectedLang.equals(lvLangFromKey)) {
                    lvLangKey = key;
                }
            }
        }
        return lvLangKey;
    }

    public void setAdapterRecyclerView(List<String> adapterRecyclerView) {

        lvRecyclerViewAdapter.setDataChanged(adapterRecyclerView);
    }
}