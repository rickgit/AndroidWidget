package edu.ptu.viewpager.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ptu.viewpager.LazyLoadFragmUtils;
import edu.ptu.viewpager.LogUtils;
import edu.ptu.viewpager.R;

/**
 * Created by anshu.wang on 2017/7/26.
 */

public class SimpFragment extends Fragment implements LazyLoadFragmUtils.LazyLoadFragment {

    protected final LazyLoadFragmUtils lazyLoadFragmUtils;

    public SimpFragment() {
        lazyLoadFragmUtils = LazyLoadFragmUtils.getInstance(this);
    }

    @Override
    public void fetchData() {
        LogUtils.logMainInfo("fetchData");
    }

    @Override
    public View onLazyCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.item_go, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazyLoadFragmUtils.onActivityCreated(getActivity());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoadFragmUtils.setUserVisibleHint(getActivity(),isVisibleToUser);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return lazyLoadFragmUtils.onCreateView(inflater, container, savedInstanceState);
    }
}
