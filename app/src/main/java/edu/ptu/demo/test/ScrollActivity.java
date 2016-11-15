package edu.ptu.demo.test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

public class ScrollActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_main);
        final ListView lvDatas = (ListView) findViewById(R.id.lv);
        final View vgHeader = findViewById(R.id.vgHeader);
        lvDatas.addHeaderView(LayoutInflater.from(this).inflate(R.layout.home_header_view, null));
        lvDatas.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int l = getHeight(lvDatas);
                if (l < 36)
                    vgHeader.setY(-l);
            }
        });
        lvDatas.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        lvDatas.setAdapter(new BaseAdapter() {
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
                if (convertView == null)
                    convertView = (ViewGroup) LayoutInflater.from(ScrollActivity.this).inflate(R.layout.activity_recycler_item,
                            null, false);
                return convertView;
            }


            ArrayList datas = new ArrayList() {{
                for (int i = 0; i < 100; i++) {
                    add("demo" + i);
                }
            }};


        });
    }

    private int getHeight(ListView lvDatas) {
        View c = lvDatas.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = lvDatas.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight();
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
