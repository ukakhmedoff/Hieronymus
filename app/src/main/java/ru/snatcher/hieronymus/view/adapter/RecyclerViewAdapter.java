package ru.snatcher.hieronymus.view.adapter;

import java.util.List;

import ru.snatcher.hieronymus.presenter.vo.Translate;

/**
 * @author Usman Akhmedoff
 * @version 1.0
 */
public class RecyclerViewAdapter extends BaseAdapter<Translate> {

	public RecyclerViewAdapter(final List<Translate> pTranslateList) {
		super(pTranslateList);
	}

	@Override
	public void onBindViewHolder(final BaseAdapter.ViewHolder holder, final int position) {
		holder.fTranslateMain.setText(fTList.get(position).getTranslatedText());
		holder.fAddToBookmarks.setOnClickListener(v -> {

		});
		holder.fAddToGroup.setOnClickListener(v -> {

		});
	}

	public void setTranslateList(List<Translate> pTranslateList) {
		this.fTList = pTranslateList;
		notifyDataSetChanged();
	}
}
