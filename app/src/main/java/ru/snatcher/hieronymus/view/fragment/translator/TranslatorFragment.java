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
import ru.snatcher.hieronymus.presenter.TranslatorPresenter;
import ru.snatcher.hieronymus.view.ActivityCallback;
import ru.snatcher.hieronymus.view.fragment.BaseFragment;

/**
 * {@link TranslatorFragment} is fragment, where user enters sentences and gets translates
 */
public class TranslatorFragment extends BaseFragment implements TranslatorFragmentView {

	private final List<Language> fLangs = new ArrayList<>();

	@BindString(R.string.key)
	String fApiKey;

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

	private ViewComponent fViewComponent;
	private View fView;

	private ActivityCallback fActivityCallback;

	@Override
	public final void onAttach(Context pContext) {
		super.onAttach(pContext);
		try {
			fActivityCallback = (ActivityCallback) getActivity();
		} catch (ClassCastException e) {
			throw new ClassCastException(getActivity().toString()
					+ " must implement fActivityCallback");
		}
	}

	@Override
	public final void onCreate(@Nullable Bundle savedInstanceState) {
		if (fViewComponent == null) {
			fViewComponent = DaggerViewComponent.builder()
					.viewDynamicModule(new ViewDynamicModule(this))
					.build();
		}
		fViewComponent.inject(this);
		super.onCreate(savedInstanceState);
	}

	public final void setViewComponent(final ViewComponent pViewComponent) {
		fViewComponent = pViewComponent;
	}

	@Override
	public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

		fAddBookmarks.setOnLikeListener(new OnLikeListener() {
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
		});

		fTranslatorPresenter.onCreateView(savedInstanceState);

		return fView;
	}

	@Override
	public final void onStart() {
		super.onStart();
		getLangs();
	}

	public final void getLangs() {
		fTranslatorPresenter.getLangs(fApiKey, getContext().getResources().getString(R.string.ui_lang), (App) getActivity().getApplication());
	}

	@Override
	public final void showLanguageList(final List<Language> pLanguages) {
		fLangs.addAll(pLanguages);

		fSpinnerToLang.setAdapter(getLanguageArrayAdapter(R.layout.spinner_text_item));
		fSpinnerFromLang.setAdapter(getLanguageArrayAdapter(android.R.layout.simple_spinner_item));

		fSpinnerFromLang.setOnItemSelectedListener(getItemSelectedListener(Constants.PREFERENCES_LANGUAGE_FROM_TRANSLATE));
		fSpinnerToLang.setOnItemSelectedListener(getItemSelectedListener(Constants.PREFERENCES_LANGUAGE_TO_TRANSLATE));

		//Выбираем какой-нибудь язык по-умолчанию
		fSpinnerToLang.setSelection(getSpinnerSelectedItemFromPreferences(Constants.PREFERENCES_LANGUAGE_TO_TRANSLATE));
		fSpinnerFromLang.setSelection(getSpinnerSelectedItemFromPreferences(Constants.PREFERENCES_LANGUAGE_FROM_TRANSLATE));

		saveLanguages(pLanguages);
	}

	/**
	 * @return {@link ArrayAdapter} set spinners
	 */
	private ArrayAdapter<String> getLanguageArrayAdapter(int pLayoutId) {
		List<String> lvLangs = new ArrayList<>();
		for (Language lvLanguage : fLangs) lvLangs.add(lvLanguage.getLangValue());

		// Настраиваем адаптер
		ArrayAdapter<String> adapter = new ArrayAdapter<>(
				fView.getContext(),
				pLayoutId,
				lvLangs
		);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}

	@Override
	public final void showSelectedItem(final Translate pTranslate) {
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

	/**
	 * Сначала делаем видимым поле для перевода
	 * Узнаем был ли перевод в истории и в избранном
	 * Проставляем текст в поле и делаем избранным или же нет
	 * Сохраняем перевод
	 *
	 * @param pTranslate - to show
	 */
	@Override
	public final void showTranslate(final Translate pTranslate) {
		fView.findViewById(R.id.include_translated_text).setVisibility(View.VISIBLE);
		fTranslate = pTranslate;

		pTranslate.setIsBookmark(fTranslatorPresenter.getTranslateFavourite(pTranslate, (App) getActivity().getApplication()));
		fTranslatedText.setText(pTranslate.getTranslatedText());

		fAddBookmarks.setLiked(pTranslate.getIsBookmark());

		saveTranslate(pTranslate);
	}

	@Override
	public final void saveTranslate(final Translate pTranslate) {
		fTranslatorPresenter.saveTranslate(pTranslate, getContext());
	}

	@Override
	public final void saveLanguages(final List<Language> pLanguages) {
		fTranslatorPresenter.saveLanguages(pLanguages, getContext());
	}

	@Override
	public final void onResume() {
		super.onResume();

		App.getInstance().setConnectionListener(fTranslatorPresenter);
	}

	/**
	 * On click to swap button
	 */
	@OnClick(R.id.btnSwap)
	public final void onClickSwap() {
		int lvFrom = fSpinnerFromLang.getSelectedItemPosition();
		int lvTo = fSpinnerToLang.getSelectedItemPosition();

		fTextToTranslate.setText(fTranslatedText.getText());
		fSpinnerFromLang.setSelection(lvTo);
		fSpinnerToLang.setSelection(lvFrom);
	}

	public final AdapterView.OnItemSelectedListener getItemSelectedListener(String pS) {
		return new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				getTranslate();
				setSpinnerLanguagesToPreferences(pS, position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
			}
		};
	}
}