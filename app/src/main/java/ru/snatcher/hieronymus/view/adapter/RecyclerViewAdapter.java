package ru.snatcher.hieronymus.view.adapter;

import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

import ru.snatcher.hieronymus.db.Translate;
import ru.snatcher.hieronymus.other.App;
import ru.snatcher.hieronymus.other.Constants;
import ru.snatcher.hieronymus.presenter.BasePresenter;

/**
 * @author Usman Akhmedoff
 * @version 1.0
 */
public class RecyclerViewAdapter extends BaseRecyclerAdapter<Translate> {

	public RecyclerViewAdapter(final List<Translate> pTranslateList, BasePresenter pPresenter, App pApp) {
		super(pTranslateList, pPresenter, pApp);
	}

	@Override
	public void onBindViewHolder(final BaseRecyclerAdapter.ViewHolder holder, final int position) {
		Translate lvTranslate = fTList.get(position);
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
	}

	private void setLiked(final ViewHolder holder, final Translate pTranslate, final boolean pTranslateIsntFavourite) {
		holder.fAddToBookmarks.setLiked(pTranslateIsntFavourite);
		pTranslate.setIsBookmark(pTranslateIsntFavourite);
		saveTranslate(pTranslate);
	}

	public void setTranslateList(List<Translate> pTranslateList) {
		this.fTList = pTranslateList;
		notifyDataSetChanged();
	}
}
