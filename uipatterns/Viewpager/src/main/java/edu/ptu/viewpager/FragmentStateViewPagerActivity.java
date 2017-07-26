package edu.ptu.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by anshu.wang on 2017/7/24.
 */

public class FragmentStateViewPagerActivity   extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statefragm);
        ViewPager vp = (ViewPager) findViewById(R.id.vp);
        vp.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return   SimpFragment.getInstance(position);
            }

            @Override
            public int getCount() {
                return 5;
            }
        });
        vp.setOffscreenPageLimit(5);
    }
}