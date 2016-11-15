package edu.ptu.demo.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import edu.ptu.demo.R;

/**
 * Created by anshu.wang on 2016/11/4.
 */

public class ListviewActivity extends Activity {

    private ListView lvDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        ListDemo();
    }

    private void ListDemo() {
        lvDatas = (ListView) findViewById(R.id.lv);
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
                    convertView = (ViewGroup) LayoutInflater.from(ListviewActivity.this).inflate(R.layout.activity_recycler_item,
                            null, false);
                TextView tv = (TextView) convertView.findViewById(R.id.list_tv);
                tv.setText("===============>" + position);
                return convertView;
            }


            ArrayList datas = new ArrayList() {{
                for (int i = 0; i < 15; i++) {
                    add("demo" + i);
                }
            }};


        });
        lvDatas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println(" getlistview scroll "+getScrollY(lvDatas));
                return false;
            }
        });
    }

    public int getScrollY(ListView listView) {
        View c = lvDatas.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = lvDatas.getFirstVisiblePosition();
        int top = c.getTop();
        return -(top-lvDatas.getPaddingTop()) + firstVisiblePosition * c.getHeight() ;
    }

}
