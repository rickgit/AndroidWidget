package edu.ptu.customview.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ptu.customview.R;

public class ViewPagerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_viewpager);
        ViewPagerSlidingLayout sl = (ViewPagerSlidingLayout) findViewById(R.id.sl);
        ViewPager vp = (ViewPager) findViewById(R.id.vp);
        vp.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            SparseArrayCompat<ItemFragm> list=new SparseArrayCompat<ItemFragm>();
            @Override
            public Fragment getItem(int position) {
                if (list.valueAt(position)==null) {
                    ItemFragm intance = ItemFragm.getIntance();
                    list.setValueAt(position,intance);
                }
                return list.valueAt(position);
            }

            @Override
            public int getCount() {
                return 10;
            }
        });
    }
    public static class ItemFragm extends Fragment{
        public static ItemFragm getIntance(){
            return new ItemFragm();
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragm_item,null);
        }
    }
}
