package ru.snatcher.hieronymus.view.fragment;

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
import android.widget.Toast;

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
import ru.snatcher.hieronymus.other.Constants;
import ru.snatcher.hieronymus.other.di.view.DaggerViewComponent;
import ru.snatcher.hieronymus.other.di.view.ViewComponent;
import ru.snatcher.hieronymus.other.di.view.ViewDynamicModule;
import ru.snatcher.hieronymus.presenter.MainPresenterImpl;
import ru.snatcher.hieronymus.presenter.Presenter;
import ru.snatcher.hieronymus.presenter.vo.Language;
import ru.snatcher.hieronymus.presenter.vo.Translate;
import ru.snatcher.hieronymus.view.ActivityCallback;

/**
 * {@link MainFragment} is fragment, where user enters sentences and gets translates
 */
public class MainFragment extends BaseFragment implements MainFragmentView {

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

	private ViewComponent viewComponent;
	private View fView;

	private ActivityCallback activityCallback;
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

	private void setSpinnerLanguagesToPreferences(String pLanguagesToPreferences, int pLanguagePosition) {
		activityCallback.setSpinnerLanguagesToPreferences(pLanguagesToPreferences, pLanguagePosition);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			activityCallback = (ActivityCallback) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement activityCallback");
		}
	}

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		fView = inflater.inflate(R.layout.fragment_main, container, false);
		ButterKnife.bind(this, fView);

		fMainPresenter.getLangs(fView.getResources().getString(R.string.key));

		final CompositeDisposable lvDisposable = new CompositeDisposable();

		Disposable inputWatcher = RxTextView.textChanges(fTextToTranslate)
				.debounce(400, TimeUnit.MILLISECONDS)
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

		fMainPresenter.getTranslates(fView.getResources().getString(R.string.key), inputText, getSpinnerLangKey());
	}

	public void setViewComponent(ViewComponent viewComponent) {
		this.viewComponent = viewComponent;
	}

	private ArrayAdapter<String> getLanguageArrayAdapter() {
		List<String> lvLangs = new ArrayList<>();
		for (Language lvLanguage : fLangs) lvLangs.add(lvLanguage.getLangValue());

		// Настраиваем адаптер
		ArrayAdapter<String> adapter = new ArrayAdapter<>(
				fView.getContext(),
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

	public void showError(final String error) {
		Toast.makeText(fView.getContext(), error, Toast.LENGTH_LONG).show();
	}

	@Override
	protected Presenter getPresenter() {
		return fMainPresenter;
	}

	@Override
	public void showTranslate(final Translate pTranslate) {
		fView.findViewById(R.id.include_translated_text).setVisibility(View.VISIBLE);
		fTranslatedText.setText(pTranslate.getTranslatedText());
		if (pTranslate.isBookmark())
			fAddBookmarks.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border, fView.getContext().getApplicationContext().getTheme()));
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

	private int getSpinnerSelectedItemFromPreferences(final String pPreferencesLanguageTranslate) {
		return activityCallback.getSpinnerLanguagesFromPreferences(pPreferencesLanguageTranslate);
	}

	@Override
	public void saveTranslate(final Translate pTranslate) {
		activityCallback.saveTranslate(pTranslate);
	}

	@Override
	public void saveLanguage(final Language pLanguage) {
		activityCallback.saveLanguage(pLanguage);
	}

	@Override
	public void saveLanguages(final List<Language> pLanguages) {
		activityCallback.saveLanguages(pLanguages);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		fMainPresenter.onSaveInstanceState(outState);
	}
}