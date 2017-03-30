package ru.snatcher.hieronymus.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.snatcher.hieronymus.R;

/**
 * {@link BaseAdapter}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {

    List<T> fTList;

    BaseAdapter(List<T> fTList) {
        this.fTList = fTList;
    }

    public List<T> getTList() {
        return fTList;
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

        ViewHolder(final View itemView) {
            super(itemView);
            fTranslateMain = (TextView) itemView.findViewById(R.id.translateMain);
        }
    }

}