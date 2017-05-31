package edu.ptu.demo.test.viewpager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import edu.ptu.demo.R;
import edu.ptu.demo.test.home.RecyclerFragment;

public class ViewPagerActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public Fragment getItem(int i) {
                return new RecyclerFragment();
            }
        });
        TabLayout tabs = (TabLayout) findViewById(R.id.titles);
        tabs.setupWithViewPager(pager);
    }
}
