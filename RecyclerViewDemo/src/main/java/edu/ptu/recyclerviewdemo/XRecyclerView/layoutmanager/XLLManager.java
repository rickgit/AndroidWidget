package edu.ptu.recyclerviewdemo.XRecyclerView.layoutmanager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import edu.ptu.recyclerviewdemo.XRecyclerView.RecyclerViewScrollListener;

/**
 * Created by WangAnshu on 2017/11/26.
 */

public class XLLManager extends LinearLayoutManager {
    private RecyclerViewScrollListener listener;
    public XLLManager(Context context) {
        super(context);
    }

    public XLLManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public XLLManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    //实际要滑动的距离 dy，下拉刷新 dy小于0
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scrollRange = super.scrollVerticallyBy(dy, recycler, state);
        if (listener!=null)
            listener.scrollVerticallyBy(dy-scrollRange);//如果没有下拉刷新，dy-scrollRange为0，否则负数的移动距离
        return scrollRange;
    }

    public void setScrollListener(RecyclerViewScrollListener listener) {
        this.listener = listener;
    }
}
