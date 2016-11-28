package edu.ptu.demo.test.utils.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ptu.demo.R;

/**
 * Created by anshu.wang on 2016/11/16.
 */

public class RecyclerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) LayoutInflater.from(getContext()).inflate(R.layout.fragment_recycler, null);
        ListUtils.setVerticleListData(view);

        return view;
    }
}
