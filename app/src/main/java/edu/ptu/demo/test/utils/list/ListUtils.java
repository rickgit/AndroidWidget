package edu.ptu.demo.test.utils.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.ptu.demo.R;
import edu.ptu.demo.test.recyclerview.RecyclerActivity;
import edu.ptu.demo.test.utils.Utils;

/**
 * Created by anshu.wang on 2016/11/24.
 */

public class ListUtils {
    public static void setVerticleListData(RecyclerView rcv) {
        // 1,找到这个View
        RecyclerView mRecyclerView = rcv;
// 2,设置布局管理LayoutManager

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(rcv.getContext());
//        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
// 3，（可选）如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
// 4，设置Adapter

        mRecyclerView.setAdapter(new MyAdapter());
    }

    public static class MyAdapter extends RecyclerView.Adapter<RecyclerActivity.ViewHolder> {
        public List datas = new ArrayList() {{
            for (int i = 0; i < 100; i++) {
                add(" item " + i);
            }
        }};

        //创建新View，被LayoutManager所调用
        @Override
        public RecyclerActivity.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_recycler_item, viewGroup, false);
            RecyclerActivity.ViewHolder vh = new RecyclerActivity.ViewHolder(view);
            return vh;
        }

//        int color1 = (Utils.generateBeautifulColor());
//        int color2 = (Utils.generateBeautifulColor());
        int color1 = 0xffaaccbb;
        int color2 = (Utils.generateBeautifulColor());

        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(RecyclerActivity.ViewHolder viewHolder, int position) {
//            viewHolder.mTextView.setText(datas[position]);
            View convertView = viewHolder.itemView;
            if (position % 2 == 0)
                convertView.setBackgroundColor(color1);
            else
                convertView.setBackgroundColor(color2);

        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return datas.size();
        }

    }   //自定义的ViewHolder，持有每个Item的的所有界面元素

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.list_tv);
        }
    }
}
