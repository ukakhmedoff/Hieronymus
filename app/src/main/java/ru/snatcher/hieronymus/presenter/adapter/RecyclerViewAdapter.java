package ru.snatcher.hieronymus.presenter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.snatcher.hieronymus.R;

/**
 * @author Usman Akhmedoff
 * @version 1.0
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<String> fTranslateList;

    public void setDataChanged(List<String> pTranslateList) {
        fTranslateList = pTranslateList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View lvView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);

        return new ViewHolder(lvView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.fTranslateMain.setText(fTranslateList.get(0));
        holder.fTranslateId.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return fTranslateList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView fTranslateMain, fTranslateId;

        ViewHolder(final View itemView) {
            super(itemView);
            fTranslateMain = (TextView) itemView.findViewById(R.id.translateMain);
            fTranslateId = (TextView) itemView.findViewById(R.id.translateId);
        }
    }


}
