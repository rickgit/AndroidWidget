package edu.ptu.viewpager.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ptu.viewpager.LogUtils;
import edu.ptu.viewpager.R;

/**
 * Created by anshu.wang on 2017/7/26.
 */

public class Simp1Fragment extends SimpFragment {

    private int key=-2;

    public static Simp1Fragment getInstance(int i){
        Simp1Fragment simpFragment = new Simp1Fragment();
        Bundle args = new Bundle();
        args.putInt("key",-1);
        simpFragment.setArguments(args);
        return simpFragment;
    }
   @SuppressLint("ValidFragment")
   private Simp1Fragment(){
       LogUtils.logMainInfo("SimpFragment"+key);
   }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        key = getArguments().getInt("key");
        LogUtils.logMainInfo("onCreate"+key);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.logMainInfo("setUserVisibleHint "+isVisibleToUser+"  "+key);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.logMainInfo("onActivityCreated"+key);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.logMainInfo("onViewCreated"+key);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.logMainInfo("onCreateView"+key);
        return super.onCreateView(inflater,container,savedInstanceState);
    }
}
