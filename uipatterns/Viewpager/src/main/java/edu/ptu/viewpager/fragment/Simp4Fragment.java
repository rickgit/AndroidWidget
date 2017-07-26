package edu.ptu.viewpager.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import edu.ptu.viewpager.LogUtils;

/**
 * Created by anshu.wang on 2017/7/26.
 */

public class Simp4Fragment extends SimpFragment {

    private int key=-2;

    public static Simp4Fragment getInstance(int i){
        Simp4Fragment simpFragment = new Simp4Fragment();
        Bundle args = new Bundle();
        args.putInt("key",-1);
        simpFragment.setArguments(args);
        return simpFragment;
    }
   @SuppressLint("ValidFragment")
   private Simp4Fragment(){
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
        LogUtils.logMainInfo("setUserVisibleHint"+key);
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
}
