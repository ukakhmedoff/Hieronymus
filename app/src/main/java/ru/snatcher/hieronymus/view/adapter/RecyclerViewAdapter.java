package ru.snatcher.hieronymus.view.adapter;

import java.util.List;

import ru.snatcher.hieronymus.presenter.MainPresenterImpl;

/**
 * @author Usman Akhmedoff
 * @version 1.0
 */
public class RecyclerViewAdapter extends BaseAdapter<String> {

    private MainPresenterImpl fMainPresenter;

    public RecyclerViewAdapter(final List<String> pTranslateList, final MainPresenterImpl pMainPresenter) {
        super(pTranslateList);
        fMainPresenter = pMainPresenter;
    }

    @Override
    public void onBindViewHolder(final BaseAdapter.ViewHolder holder, final int position) {
        holder.fTranslateMain.setText(fTList.get(position));
    }

    public void setRepoList(List<String> pTranslateList) {
        this.fTList = pTranslateList;
        notifyDataSetChanged();
    }
}
