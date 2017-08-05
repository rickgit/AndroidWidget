package edu.ptu.navpattern;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.ptu.navpattern.tablayout.Tablayout;
import edu.ptu.navpattern.tablayout.ViewPagerHelper;
import edu.ptu.navpattern.tooltip.OnItemClickListener;
import edu.ptu.navpattern.tooltip.Tooltip;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tooltip.Builder builder = new Tooltip.Builder(v)
                        .setItemText(new String[]{" s33333333333333333333333df", "asdf ", "sadf "}, new int[]{R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher})
//                        .setItemText(new String[]{" s33333333333333333333333df", "asdf ", "sadf "})
                        .setmOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Toast.makeText(MainActivity.this, "  " + which, Toast.LENGTH_LONG).show();
                            }
                        });

                builder.show();
            }
        });
        LayoutInflater inflater=getLayoutInflater();
       View view1 = inflater.inflate(R.layout.item, null);
        View  view2 = inflater.inflate(R.layout.item,null);
        View  view3 = inflater.inflate(R.layout.item, null);

       final List<View> viewList = new ArrayList<View>();// 将要分页显示的View装入数组中

        for (int i = 0; i < 13; i++) {
            viewList.add(inflater.inflate(R.layout.item, null));
        }
        Tablayout tablayout = (Tablayout) findViewById(R.id.tabllayout);
        ViewPager vp= (ViewPager) findViewById(R.id.vp);
        vp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return charSequences.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return charSequences[position];
            }
            CharSequence [] charSequences={"11","222","33","11","222","33","11","222","33","11","222","33","444"};
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View child = viewList.get(position);
                if (position%2==1)
                    child.setBackgroundColor(0xffaa00cc);
                container.addView(child);
                return child;

            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
                container.removeView(viewList.get(position));
            }
        });
        new ViewPagerHelper().bindViewPager(vp,tablayout);
    }
}
