package edu.ptu.demo.test.utils.list;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * Created by anshu.wang on 2016/11/24.
 */

public class PagerUtils {
    public static  void createPagerAdapter(FragmentActivity activity, ViewPager viewPager){
        viewPager.setAdapter(new FragmentPagerAdapter(activity.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return new RecyclerFragment();
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "页面"+position;
//                return super.getPageTitle(position);
            }
        });
    }
}
