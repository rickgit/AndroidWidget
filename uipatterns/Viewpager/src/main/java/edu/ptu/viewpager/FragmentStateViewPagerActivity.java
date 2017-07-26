package edu.ptu.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import edu.ptu.viewpager.fragment.Simp1Fragment;
import edu.ptu.viewpager.fragment.Simp2Fragment;
import edu.ptu.viewpager.fragment.Simp3Fragment;
import edu.ptu.viewpager.fragment.Simp4Fragment;
import edu.ptu.viewpager.fragment.Simp5Fragment;
import edu.ptu.viewpager.fragment.SimpFragment;

/**
 * Created by anshu.wang on 2017/7/24.
 */

public class FragmentStateViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statefragm);
        ViewPager vp = (ViewPager) findViewById(R.id.vp);

        vp.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0)
                    return Simp1Fragment.getInstance(position);
                if (position == 1)
                    return Simp2Fragment.getInstance(position);
                if (position == 2)
                    return Simp3Fragment.getInstance(position);
                if (position == 3)
                    return Simp4Fragment.getInstance(position);
                if (position == 4)
                    return Simp5Fragment.getInstance(position);

                return Simp1Fragment.getInstance(position);
            }

            @Override
            public int getCount() {
                return 5;
            }
        });
        vp.setOffscreenPageLimit(5);
    }
}