package edu.ptu.viewpager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by anshu.wang on 2017/7/26.
 */

public class SimpFragment extends Fragment {

    private int key=-2;

    public static  SimpFragment getInstance(int i){
        SimpFragment simpFragment = new SimpFragment();
        Bundle args = new Bundle();
        args.putInt("key",-1);
        simpFragment.setArguments(args);
        return simpFragment;
    }
   @SuppressLint("ValidFragment")
   private SimpFragment(){
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
