package ru.snatcher.hieronymus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.snatcher.hieronymus.entity.Translate;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

	public MainFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		View v = inflater.inflate(R.layout.fragment_main, container, false);

		App.getTranslateService().translate(v.getContext().getResources().getString(R.string.key), "Hello!", "ru").enqueue(new Callback<Translate>() {
			@Override
			public void onResponse(final Call<Translate> call, final Response<Translate> response) {
				Translate translatedText = response.body();
				Log.d("Languages", "onResponse: " + translatedText.getText().get(0));
			}

			@Override
			public void onFailure(final Call<Translate> call, final Throwable t) {

			}

		});
		return v;
	}

}
