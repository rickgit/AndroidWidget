package edu.ptu.recyclerviewdemo.XRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by WangAnshu on 2017/11/26.
 */

public class XAdapter extends RecyclerView.Adapter{

    private ArrayList<View> mHeaderViews=new ArrayList<>();
    private ArrayList<View> mFootViews=new ArrayList<>();
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * jude is head view
     * @param position pos
     * @return boolean
     */
    public boolean isHeader(int position) {
        return position >= 0 && position < mHeaderViews.size();
    }

    /**
     * jude is foot view
     * @param position pos
     * @return boolean
     */
    public boolean isFooter(int position) {
        return position < getItemCount() && position >= getItemCount() - mFootViews.size();
    }


}
