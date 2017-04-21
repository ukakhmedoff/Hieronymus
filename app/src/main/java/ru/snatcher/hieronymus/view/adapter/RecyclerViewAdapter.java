package ru.snatcher.hieronymus.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.snatcher.hieronymus.R;
import ru.snatcher.hieronymus.model.db.Translate;
import ru.snatcher.hieronymus.other.App;
import ru.snatcher.hieronymus.other.Constants;
import ru.snatcher.hieronymus.presenter.BasePresenter;

/**
 * @author Usman Akhmedoff
 * @version 1.0
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
	private List<Translate> fTranslateList;

	private BasePresenter fPresenter;

	private App fApp;
	private OnListItemClickListener fOnListItemClickListener;

	public RecyclerViewAdapter(final List<Translate> pTranslateList, BasePresenter pPresenter, App pApp, OnListItemClickListener pOnListItemClickListener) {
		this.fTranslateList = pTranslateList;
		fPresenter = pPresenter;
		fApp = pApp;
		fOnListItemClickListener = pOnListItemClickListener;
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, final int position) {
		Translate lvTranslate = fTranslateList.get(position);
		holder.fLangs.setText(lvTranslate.getLang());
		holder.fTextTranslated.setText(lvTranslate.getTranslatedText());
		holder.fAddToBookmarks.setLiked(lvTranslate.getIsBookmark());

		holder.fAddToBookmarks.setOnLikeListener(new OnLikeListener() {
			@Override
			public void liked(LikeButton likeButton) {
				setLiked(holder, lvTranslate, Constants.TRANSLATE_IS_FAVOURITE);
			}

			@Override
			public void unLiked(LikeButton likeButton) {
				setLiked(holder, lvTranslate, Constants.TRANSLATE_ISNT_FAVOURITE);
			}
		});
		holder.itemView.setOnClickListener(v -> fOnListItemClickListener.onListItemClicked(lvTranslate));
	}

	private void setLiked(final ViewHolder holder, final Translate pTranslate, final boolean pTranslateIsntFavourite) {
		holder.fAddToBookmarks.setLiked(pTranslateIsntFavourite);
		pTranslate.setIsBookmark(pTranslateIsntFavourite);
		saveTranslate(pTranslate);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);
		return new ViewHolder(v);
	}

	public void setTranslateList(final List<Translate> pTranslateList) {
		fTranslateList = pTranslateList;
	}

	@Override
	public int getItemCount() {
		return fTranslateList.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.textToTranslate)
		TextView fTextTranslated;

		@BindView(R.id.langs)
		TextView fLangs;

		@BindView(R.id.addFavourite)
		LikeButton fAddToBookmarks;

		ViewHolder(final View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

	private void saveTranslate(Translate pTranslate) {
		fPresenter.saveTranslate(pTranslate, fApp);
		notifyDataSetChanged();
	}

	public interface OnListItemClickListener {
		void onListItemClicked(Translate pTranslate);
	}
}
