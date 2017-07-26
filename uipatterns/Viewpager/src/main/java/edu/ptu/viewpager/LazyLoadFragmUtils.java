package edu.ptu.viewpager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by anshu.wang on 2017/7/26.
 */

public class LazyLoadFragmUtils {
    //缓加载数据的字段
    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;
    //缓加载布局的字段
    private boolean isViewLazyLoaded = false;
    public LazyLoadFragment lazyLoadFragment;
    private FrameLayout mContentView;
    private Bundle mSavedInstanceState;


    //<init>->setUserVisibleHint->onCreate->onCreateView->onActivityCreated->setUserVisibleHint
    //  onCreateView->onViewCreated [label="当有手动初始化视图的时候触发"]
    //onViewCreated->onActivityCreated
    //setUserVisibleHint->setUserVisibleHint[label="onActivityCreated 后不断重复"]
    private LazyLoadFragmUtils(LazyLoadFragment lazyLoadFragment) {
        this.lazyLoadFragment = lazyLoadFragment;
    }

    public static LazyLoadFragmUtils getInstance(LazyLoadFragment lazyLoadFragment) {
        return new LazyLoadFragmUtils(lazyLoadFragment);
    }

    public void onActivityCreated(Activity activity) {
        isViewInitiated = true;
        replaceLazyView(activity);
        prepareFetchData();
    }

    public void setUserVisibleHint(Activity activity,boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        replaceLazyView(activity);
        prepareFetchData();
    }

    private boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            lazyLoadFragment.fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = (FrameLayout) inflater.inflate(R.layout.item, null);
        mSavedInstanceState = savedInstanceState;
        return mContentView;
    }

    public final void replaceLazyView(Activity context) {
        LogUtils.logMainInfo("isVisibleToUser "+isVisibleToUser);
        LogUtils.logMainInfo("isViewInitiated "+isViewInitiated);
        LogUtils.logMainInfo("mContentView "+mContentView);
        LogUtils.logMainInfo("isViewLazyLoaded "+isViewLazyLoaded);
        if (isVisibleToUser && isViewInitiated && mContentView!=null&&!isViewLazyLoaded) {
            isViewLazyLoaded = true;
            FrameLayout contentView = mContentView;
            View lazyCreateView = lazyLoadFragment.onLazyCreateView(LayoutInflater.from(context), contentView, mSavedInstanceState);
            if (contentView.indexOfChild(lazyCreateView) < 0) {
                contentView.removeAllViews();
                contentView.addView(lazyCreateView);
            }
        }
    }

    public static interface LazyLoadFragment {
        public void fetchData();

        public View onLazyCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    }
}
