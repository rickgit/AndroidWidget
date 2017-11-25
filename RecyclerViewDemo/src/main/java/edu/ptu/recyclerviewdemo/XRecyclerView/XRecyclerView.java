package edu.ptu.recyclerviewdemo.XRecyclerView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * @author anshu.wang
 * @version 1.0
 * @time 2017/11/25.
 */
public class XRecyclerView extends RecyclerView{
    public XRecyclerView(Context context) {
        super(context);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
