package edu.ptu.demo.test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import edu.ptu.demo.R;

public class MainActivityTemp extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取控件
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new RecyclerView.Adapter<MainActivityTemp.ViewHolder>  () {
            ArrayList datas = new ArrayList() {{
                for (int i = 0; i < 100; i++) {
                    add("demo" + i);
                }
            }};

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                ViewGroup view = (ViewGroup) LayoutInflater.from(MainActivityTemp.this).inflate(R.layout.activity_recycler_item,
                        viewGroup, false);
                ViewHolder viewHolder = new ViewHolder(view);

                viewHolder.mTxt = (TextView) view
                        .getChildAt(0);
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int i) {
                viewHolder.mTxt.setText(datas.get(i).toString());
            }





            @Override
            public int getItemCount() {
                return datas.size();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        ImageView mImg;
        TextView mTxt;
    }

    private void getGMTTime() {
        Calendar current = Calendar.getInstance();

        long miliSeconds = current.getTimeInMillis();

        TimeZone tzCurrent = current.getTimeZone();
        int offset = tzCurrent.getRawOffset();
        if (tzCurrent.inDaylightTime(new Date())) {
            offset = offset + tzCurrent.getDSTSavings();
        }

        miliSeconds = miliSeconds - offset;

        Date resultdate = new Date(miliSeconds);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("==> " + sdf.format(resultdate));
    }
}
