package edu.ptu.demo.test.tabStrip2;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.ProgressBar;

import edu.ptu.demo.R;
import edu.ptu.demo.test.progress.CustomProgressBar;
import edu.ptu.demo.test.utils.list.PagerUtils;

/**
 * Created by anshu.wang on 2016/11/24.
 */

public class SlidingTabActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding2);
        ViewPager vp = (ViewPager) findViewById(R.id.vp);
        SlidingTabLayout stl = (SlidingTabLayout) findViewById(R.id.stl);
        stl.setDistributeEvenly(true);
        stl.setSelectedIndicatorColors(0xffaabbcc);
        stl.setBackgroundColor(0xffffffff);
        stl.setCustomTabView(R.layout.view_tab, R.id.tv);
        PagerUtils.createPagerAdapter(this,vp);
        stl.setViewPager(vp);
        stl.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return 0;
            }
        });
        ProgressDialog customProgressBar =
                CustomProgressBar.createCustomProgressBar(this);
        customProgressBar.show();
    }
}
