package ru.snatcher.hieronymus.view.adapter;

import java.util.List;

import ru.snatcher.hieronymus.db.Translate;


/**
 * @author Usman Akhmedoff
 * @version 1.0
 */
public class RecyclerViewAdapter extends BaseRecyclerAdapter<Translate> {

	public RecyclerViewAdapter(final List<Translate> pTranslateList) {
		super(pTranslateList);
	}

	@Override
	public void onBindViewHolder(final BaseRecyclerAdapter.ViewHolder holder, final int position) {
		holder.fTranslateMain.setText(fTList.get(position).getTranslatedText());
		holder.fAddToBookmarks.setOnClickListener(v -> {

		});
	}

	public void setTranslateList(List<Translate> pTranslateList) {
		this.fTList = pTranslateList;
		notifyDataSetChanged();
	}
}
