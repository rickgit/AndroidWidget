package edu.ptu.demo.test.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.ptu.demo.R;
import edu.ptu.demo.test.utils.ui.BaseViewHolder;

/**
 * Created by anshu.wang on 2016/11/15.
 */

public class HomeActivity extends FragmentActivity {

    private double lastVisibleItem;
    private RecyclerView.Adapter<BaseViewHolder> adapter;
    private SwipeRefreshLayout srl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        srl = (SwipeRefreshLayout) findViewById(R.id.srl);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()) {
                    srl.setRefreshing(true);
                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
//                    handler.sendEmptyMessageDelayed(0, 3000);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            srl.setRefreshing(false);
                        }
                    }, 100);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        adapter = new RecyclerView.Adapter<BaseViewHolder>() {
            @Override
            public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
                if (viewType == 1)
                    return new BaseViewHolder(R.layout.layout_vp, viewGroup, null);
                else
                    return new BaseViewHolder(R.layout.activity_recycler_item, viewGroup, null);
            }

            @Override
            public void onBindViewHolder(BaseViewHolder viewHolder, int i) {
                if (i % 2 == 0) {
                    viewHolder.itemView.setBackgroundColor(0xffaabbcc);
                } else {
                    viewHolder.itemView.setBackgroundColor(0xffccbbaa);
                }

//                if (i == 2) {
                ViewPager vp = (ViewPager) viewHolder.itemView.findViewById(R.id.layout_vp_container);
                if (vp != null)
                    vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                        @Override
                        public Fragment getItem(int i) {
                            return new RecyclerFragment();
                        }

                        @Override
                        public int getCount() {
                            return 3;
                        }
                    });
//                }
            }


            @Override
            public int getItemViewType(int position) {
                if (position == 2)
                    return 1;
                else return 0;
            }

            @Override
            public int getItemCount() {
                return datas.size();
            }

            List datas = new ArrayList<String>() {{
                for (int i = 0; i < 3; i++) {
                    add("data" + i);
                }
            }};
        };
        rv.setAdapter(adapter);
    }

}
