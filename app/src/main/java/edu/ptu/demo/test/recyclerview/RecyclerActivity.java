package edu.ptu.demo.test.recyclerview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import edu.ptu.demo.R;

public class RecyclerActivity extends FragmentActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_scrollerview);
// 1,找到这个View
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
// 2,设置布局管理LayoutManager

        mLayoutManager = new LinearLayoutManager(this);
//        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
// 3，（可选）如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
// 4，设置Adapter
        mRecyclerView.setAdapter(new MyAdapter(new String[]{"你好","你好","你好","你好","你好","你好","你好","你好","你好","你好","你好","你好","你好","你好","你好","你好"}));
    }
    public class MyAdapter extends RecyclerView.Adapter<ViewHolder> {
        public String[] datas = null;
        public MyAdapter(String[] datas) {
            this.datas = datas;
        }
        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_recycler_item,viewGroup,false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }
        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.mTextView.setText(datas[position]);
        }
        //获取数据的数量
        @Override
        public int getItemCount() {
            return datas.length;
        }

    }   //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View view){
            super(view);
            mTextView = (TextView) view.findViewById(R.id.list_tv);
        }
    }
}
