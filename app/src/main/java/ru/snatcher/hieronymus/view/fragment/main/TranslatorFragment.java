package ru.snatcher.hieronymus.view.fragment.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.snatcher.hieronymus.R;
import ru.snatcher.hieronymus.db.Language;
import ru.snatcher.hieronymus.db.Translate;
import ru.snatcher.hieronymus.other.App;
import ru.snatcher.hieronymus.other.Constants;
import ru.snatcher.hieronymus.other.di.view.DaggerViewComponent;
import ru.snatcher.hieronymus.other.di.view.ViewComponent;
import ru.snatcher.hieronymus.other.di.view.ViewDynamicModule;
import ru.snatcher.hieronymus.presenter.MainPresenterImpl;
import ru.snatcher.hieronymus.presenter.Presenter;
import ru.snatcher.hieronymus.view.ActivityCallback;
import ru.snatcher.hieronymus.view.fragment.BaseFragment;

/**
 * {@link TranslatorFragment} is fragment, where user enters sentences and gets translates
 */
public class TranslatorFragment extends BaseFragment implements TranslatorFragmentView {

	private final List<Language> fLangs = new ArrayList<>();

	@BindView(R.id.spinner_language_from)
	Spinner fSpinnerFromLang;

	@BindView(R.id.spinner_language_to)
	Spinner fSpinnerToLang;

	@BindView(R.id.textToTranslate)
	EditText fTextToTranslate;

	@BindView(R.id.translatedText)
	TextView fTranslatedText;

	@BindView(R.id.addFavorite)
	ImageView fAddBookmarks;

	@Inject
	MainPresenterImpl fMainPresenter;

	private ViewComponent fViewComponent;
	private View fView;

	private ActivityCallback fActivityCallback;
	AdapterView.OnItemSelectedListener fOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			prepareAndProcessTranslationRequest();
			setSpinnerLanguagesToPreferences(Constants.PREFERENCES_LANGUAGE_FROM_TRANSLATE, fSpinnerFromLang.getSelectedItemPosition());
			setSpinnerLanguagesToPreferences(Constants.PREFERENCES_LANGUAGE_TO_TRANSLATE, fSpinnerToLang.getSelectedItemPosition());
		}

		@Override
		public void onNothingSelected(AdapterView<?> parentView) {
		}
	};

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			fActivityCallback = (ActivityCallback) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement fActivityCallback");
		}
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		if (fViewComponent == null) {
			fViewComponent = DaggerViewComponent.builder()
					.viewDynamicModule(new ViewDynamicModule(this))
					.build();
		}
		fViewComponent.inject(this);
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		fView = inflater.inflate(R.layout.fragment_translator, container, false);
		ButterKnife.bind(this, fView);

		fMainPresenter.getLangs(fView.getResources().getString(R.string.key));

		final CompositeDisposable lvDisposable = new CompositeDisposable();

		Disposable inputWatcher = RxTextView.textChanges(fTextToTranslate)
				.debounce(700, TimeUnit.MILLISECONDS)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(charSequence -> prepareAndProcessTranslationRequest());
		lvDisposable.add(inputWatcher);

		fMainPresenter.onCreateView(savedInstanceState);

		return fView;
	}

	private void prepareAndProcessTranslationRequest() {
		String inputText = fTextToTranslate.getText().toString().trim();
		if (inputText.length() == 0) return;

		fMainPresenter.getTranslatesRemote(getActivity().getResources().getString(R.string.key), inputText, getSpinnerLangKey());
	}

	private ArrayAdapter<String> getLanguageArrayAdapter() {
		List<String> lvLangs = new ArrayList<>();
		for (Language lvLanguage : fLangs) lvLangs.add(lvLanguage.getLangValue());

		// Настраиваем адаптер
		ArrayAdapter<String> adapter = new ArrayAdapter<>(
				getActivity(),
				android.R.layout.simple_spinner_item,
				lvLangs
		);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}

	private String getSpinnerLangKey() {
		return fMainPresenter.onLanguageSelected(fLangs.get(fSpinnerFromLang.getSelectedItemPosition()))
				+ "-"
				+ fMainPresenter.onLanguageSelected(fLangs.get(fSpinnerToLang.getSelectedItemPosition()));
	}

	@Override
	protected Presenter getPresenter() {
		return fMainPresenter;
	}

	private void setSpinnerLanguagesToPreferences(String pLanguagesToPreferences, int pLanguagePosition) {
		fActivityCallback.setSpinnerLanguagesToPreferences(pLanguagesToPreferences, pLanguagePosition);
	}

	private int getSpinnerSelectedItemFromPreferences(final String pPreferencesLanguageTranslate) {
		return fActivityCallback.getSpinnerLanguagesFromPreferences(pPreferencesLanguageTranslate);
	}

	@Override
	public void showTranslate(final Translate pTranslate) {
		fView.findViewById(R.id.include_translated_text).setVisibility(View.VISIBLE);
		fTranslatedText.setText(pTranslate.getTranslatedText());
		if (pTranslate.getIsBookmark())
			fAddBookmarks.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border, getActivity().getApplicationContext().getTheme()));
	}

	@Override
	public void showLanguagesList(final List<Language> pLanguages) {
		fLangs.addAll(pLanguages);

		//Ставим в наш спиннер адаптер
		fSpinnerToLang.setAdapter(getLanguageArrayAdapter());
		fSpinnerFromLang.setAdapter(getLanguageArrayAdapter());

		fSpinnerFromLang.setOnItemSelectedListener(fOnItemSelectedListener);
		fSpinnerToLang.setOnItemSelectedListener(fOnItemSelectedListener);

		//Выбираем какой-нибудь язык по-умолчанию
		fSpinnerToLang.setSelection(getSpinnerSelectedItemFromPreferences(Constants.PREFERENCES_LANGUAGE_TO_TRANSLATE));
		fSpinnerFromLang.setSelection(getSpinnerSelectedItemFromPreferences(Constants.PREFERENCES_LANGUAGE_FROM_TRANSLATE));
	}

	@Override
	public void saveTranslate(final Translate pTranslate) {
		fMainPresenter.saveTranslate(pTranslate, (App) getActivity().getApplication());
	}

	@Override
	public void saveLanguages(final List<Language> pLanguages) {
		fMainPresenter.saveLanguages(pLanguages, (App) getActivity().getApplication());
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
}