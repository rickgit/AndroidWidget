package edu.ptu.demo.test.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.ptu.demo.R;
import edu.ptu.demo.test.utils.ui.BaseViewHolder;

/**
 * Created by anshu.wang on 2016/11/16.
 */

public class RecyclerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) LayoutInflater.from(getContext()).inflate(R.layout.fragment_recycler, null);
        view.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        view.setAdapter(new RecyclerView.Adapter<BaseViewHolder>() {

            @Override
            public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                return new BaseViewHolder(R.layout.listview,viewGroup,null);
            }

            @Override
            public void onBindViewHolder(BaseViewHolder baseViewHolder, int i) {
                ListView lv = (ListView) baseViewHolder.itemView;
                lv.setAdapter(new BaseAdapter() {
                    List datas=new ArrayList<String>();
                    @Override
                    public int getCount() {
                        return datas.size();
                    }

                    @Override
                    public Object getItem(int position) {
                        return null;
                    }

                    @Override
                    public long getItemId(int position) {
                        return 0;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        if (convertView==null)
                            convertView=LayoutInflater.from(getContext()).inflate(R.layout.item,null);
                        if (position%2==0)
                            convertView.setBackgroundColor(0xffaaccbb);
                        else
                            convertView.setBackgroundColor(0xffccbbaa);
                        return convertView;
                    }
                });
            }

            @Override
            public int getItemCount() {
                return 0;
            }
        });

        return  new TextView(getContext());
    }
}
