package ru.snatcher.hieronymus.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.snatcher.hieronymus.R;

/**
 * {@link BaseRecyclerAdapter}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerAdapter.ViewHolder> {

	List<T> fTList;

	BaseRecyclerAdapter(List<T> fTList) {
		this.fTList = fTList;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);
		return new ViewHolder(v);
	}

	@Override
	public int getItemCount() {
		return fTList.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		TextView fTranslateMain;

		ImageView fAddToGroup, fAddToBookmarks;

		ViewHolder(final View itemView) {
			super(itemView);
			fTranslateMain = (TextView) itemView.findViewById(R.id.translatedText);

			fAddToBookmarks = (ImageView) itemView.findViewById(R.id.addFavorite);
		}
	}

}