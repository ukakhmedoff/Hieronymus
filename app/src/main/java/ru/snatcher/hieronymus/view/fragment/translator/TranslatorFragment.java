package ru.snatcher.hieronymus.view.fragment.translator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.snatcher.hieronymus.R;
import ru.snatcher.hieronymus.model.db.Language;
import ru.snatcher.hieronymus.model.db.Translate;
import ru.snatcher.hieronymus.other.App;
import ru.snatcher.hieronymus.other.Constants;
import ru.snatcher.hieronymus.other.di.view.DaggerViewComponent;
import ru.snatcher.hieronymus.other.di.view.ViewComponent;
import ru.snatcher.hieronymus.other.di.view.ViewDynamicModule;
import ru.snatcher.hieronymus.presenter.BasePresenter;
import ru.snatcher.hieronymus.presenter.translator.TranslatorPresenter;
import ru.snatcher.hieronymus.view.ActivityCallback;
import ru.snatcher.hieronymus.view.fragment.BaseFragment;

import static android.widget.AdapterView.OnItemSelectedListener;

/**
 * {@link TranslatorFragment} is fragment, where user enters sentences and gets translates
 */
public class TranslatorFragment extends BaseFragment implements TranslatorFragmentView {

	private final List<Language> fLangs = new ArrayList<>();

	@BindString(R.string.key)
	String fApiKey;

	@BindString(R.string.ui_lang)
	String fUiLang;

	@BindView(R.id.spinner_language_from)
	Spinner fSpinnerFromLang;

	@BindView(R.id.spinner_language_to)
	Spinner fSpinnerToLang;

	@BindView(R.id.textToTranslate)
	EditText fTextToTranslate;

	@BindView(R.id.textTranslated)
	TextView fTranslatedText;

	@BindView(R.id.addFavouriteTranslator)
	LikeButton fAddBookmarks;

	Translate fTranslate;
	@Inject
	TranslatorPresenter fTranslatorPresenter;
	OnLikeListener fOnLikeListener = new OnLikeListener() {
		@Override
		public void liked(LikeButton likeButton) {
			fAddBookmarks.setLiked(true);
			fTranslate.setIsBookmark(Constants.TRANSLATE_IS_FAVOURITE);
			saveTranslate(fTranslate);
		}

		@Override
		public void unLiked(LikeButton likeButton) {
			fAddBookmarks.setLiked(false);
			fTranslate.setIsBookmark(Constants.TRANSLATE_ISNT_FAVOURITE);
			saveTranslate(fTranslate);
		}
	};
	private ViewComponent fViewComponent;
	private View fView;

	private ActivityCallback fActivityCallback;

	OnItemSelectedListener fOnItemSelectedListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			getTranslate();
			setSpinnerLanguagesToPreferences(Constants.PREFERENCES_LANGUAGE_FROM_TRANSLATE, fSpinnerFromLang.getSelectedItemPosition());
			setSpinnerLanguagesToPreferences(Constants.PREFERENCES_LANGUAGE_TO_TRANSLATE, fSpinnerToLang.getSelectedItemPosition());
		}

		@Override
		public void onNothingSelected(AdapterView<?> parentView) {
		}
	};

	@Override
	public void onAttach(Context pContext) {
		super.onAttach(pContext);
		try {
			fActivityCallback = (ActivityCallback) getActivity();
		} catch (ClassCastException e) {
			throw new ClassCastException(getActivity().toString()
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

	public void setViewComponent(final ViewComponent pViewComponent) {
		fViewComponent = pViewComponent;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		fView = inflater.inflate(R.layout.fragment_translator, container, false);
		ButterKnife.bind(this, fView);

		final CompositeDisposable lvDisposable = new CompositeDisposable();
		fTranslatedText.setMovementMethod(new ScrollingMovementMethod());
		Disposable inputWatcher = RxTextView.textChanges(fTextToTranslate)
				.debounce(700, TimeUnit.MILLISECONDS)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(charSequence -> getTranslate());
		lvDisposable.add(inputWatcher);

		fAddBookmarks.setOnLikeListener(fOnLikeListener);

		fTranslatorPresenter.onCreateView(savedInstanceState);

		return fView;
	}

	@Override
	public void onStart() {
		super.onStart();
		getLangs();
	}

	public void getLangs() {
		fTranslatorPresenter.getLangs(fApiKey, fUiLang, (App) getActivity().getApplication());
	}

	@Override
	public void showSelectedItem(final Translate pTranslate) {
		fTextToTranslate.setText(pTranslate.getTranslatedText());

		fTranslatorPresenter.getTranslatesRemote(fApiKey, pTranslate.getTranslatedText(), pTranslate.getLang());
	}

	/**
	 * Get translate from {@link TranslatorPresenter}
	 */
	private void getTranslate() {
		String inputText = fTextToTranslate.getText().toString().trim();
		if (inputText.length() == 0) return;

		fTranslatorPresenter.getTranslatesRemote(fApiKey, inputText, getSpinnerLangKey());
	}

	/**
	 * @return {@link ArrayAdapter} set spinners
	 */
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

	/**
	 * @return lang's key from translate - lang's key to translate
	 */
	private String getSpinnerLangKey() {
		return fLangs.get(fSpinnerFromLang.getSelectedItemPosition()).getLangKey()
				+ "-"
				+ fLangs.get(fSpinnerToLang.getSelectedItemPosition()).getLangKey();

	}

	@Override
	public BasePresenter getPresenter() {
		return fTranslatorPresenter;
	}

	/**
	 * If user selected new language we save it
	 *
	 * @param pLanguagesToPreferences - from constants
	 * @param pLanguagePosition       - selected position
	 */
	private void setSpinnerLanguagesToPreferences(String pLanguagesToPreferences, int pLanguagePosition) {
		fActivityCallback.setSpinnerLanguagesToPreferences(pLanguagesToPreferences, pLanguagePosition);
	}

	/**
	 * Get language id from preferences
	 *
	 * @param pPreferencesLanguageTranslate - from constants
	 * @return selected language id from preferences
	 */
	private int getSpinnerSelectedItemFromPreferences(final String pPreferencesLanguageTranslate) {
		return fActivityCallback.getSpinnerLanguagesFromPreferences(pPreferencesLanguageTranslate);
	}

	@Override
	public void showTranslate(final Translate pTranslate) {
		fView.findViewById(R.id.include_translated_text).setVisibility(View.VISIBLE);

		fTranslate = pTranslate;

		pTranslate.setIsBookmark(fTranslatorPresenter.getTranslateFavourite(pTranslate, (App) getActivity().getApplication()));
		fTranslatedText.setText(pTranslate.getTranslatedText());

		fAddBookmarks.setLiked(pTranslate.getIsBookmark());

		saveTranslate(pTranslate);

	}

	@Override
	public void showLanguageList(final List<Language> pLanguages) {
		fLangs.addAll(pLanguages);

		//Ставим в наш спиннер адаптер
		fSpinnerToLang.setAdapter(getLanguageArrayAdapter());
		fSpinnerFromLang.setAdapter(getLanguageArrayAdapter());

		fSpinnerFromLang.setOnItemSelectedListener(fOnItemSelectedListener);
		fSpinnerToLang.setOnItemSelectedListener(fOnItemSelectedListener);

		//Выбираем какой-нибудь язык по-умолчанию
		fSpinnerToLang.setSelection(getSpinnerSelectedItemFromPreferences(Constants.PREFERENCES_LANGUAGE_TO_TRANSLATE));
		fSpinnerFromLang.setSelection(getSpinnerSelectedItemFromPreferences(Constants.PREFERENCES_LANGUAGE_FROM_TRANSLATE));

		saveLanguages(pLanguages);
	}

	@Override
	public void saveTranslate(final Translate pTranslate) {
		fTranslatorPresenter.saveTranslate(pTranslate, getContext());
	}

	@Override
	public void saveLanguages(final List<Language> pLanguages) {
		fTranslatorPresenter.saveLanguages(pLanguages, getContext());
	}

	@Override
	public void onResume() {
		super.onResume();

		App.getInstance().setConnectionListener(fTranslatorPresenter);
	}

	/**
	 * On click to swap button
	 */
	@OnClick(R.id.btnSwap)
	public void onClickSwap() {
		int lvFrom = fSpinnerFromLang.getSelectedItemPosition();
		int lvTo = fSpinnerToLang.getSelectedItemPosition();

		fSpinnerFromLang.setSelection(lvTo);
		fSpinnerToLang.setSelection(lvFrom);
	}

	@Override
	public void onSaveInstanceState(final Bundle outState) {
		super.onSaveInstanceState(outState);
	}
}