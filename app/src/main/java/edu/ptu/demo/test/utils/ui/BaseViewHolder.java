package edu.ptu.demo.test.utils.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by anshu.wang on 2016/11/8.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ViewHolderClickListener mListener;
    private BaseViewHolder(View itemView) {
        super(itemView);
    }
    public BaseViewHolder(View rootView, ViewHolderClickListener listener) {
        super(rootView);
        this.mListener = listener;
        rootView.setOnClickListener(this);
    }
    public BaseViewHolder(int layoutId, View parent,ViewHolderClickListener listener) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutId,null));
        this.mListener = listener;
        itemView.setOnClickListener(this);
    }
    /**
     * item项下，多个控件可点击的构造方法。需要实现ViewHolder实现
     *
     * @param parent
     * @param listener
     * @param view
     */
    public BaseViewHolder(int layoutId,View parent, ViewHolderClickListener listener, View[] view) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutId,null));
        this.mListener = listener;
        itemView.setOnClickListener(this);
        for (int i = 0; i < view.length; i++) {
            view[i].setOnClickListener(this);
        }
    }

    /**
     * 点击监听
     */
    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemClick(v, getPosition());
        }
    }

    public static interface ViewHolderClickListener {
        /**
         * <pre>
         *     switch (v.getId()) {
         *       case R.id.btn://按钮的回调
         *       mMoreClickListener.onButtonClick();
         *       break;
         *       case R.id.iv_icon://图片回调
         *       mMoreClickListener.onImageClick();
         *       break;
         *  }
         * </pre>
         *
         * @param v
         * @param position
         */
        public void onItemClick(View v, int position);
    }
}
