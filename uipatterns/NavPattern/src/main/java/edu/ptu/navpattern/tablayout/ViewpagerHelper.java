package edu.ptu.navpattern.tablayout;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by anshu.wang on 2017/6/28.
 */

public class ViewpagerHelper {
    private ViewPager mViewPager;
    private Tablayout tablayout;

    public  void bindViewPager(ViewPager viewPager, final Tablayout tablayout) {
        this.mViewPager = viewPager;
        this.tablayout=tablayout;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                View v=null;
                for (int i = 0; i < tablayout.getChildCount(); i++) {
                    v=tablayout.getChildAt(i);
                    if (position == i) {
                        tablayout.getChildAt(i).setBackgroundColor(0xffff0000);
                    }else{
                        tablayout.getChildAt(i).setBackgroundColor(0xff00ff00);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        populateTabStrip();
    }

    private void populateTabStrip() {
        final PagerAdapter adapter = mViewPager.getAdapter();
        final View.OnClickListener tabClickListener = new TabClickListener();
        for (int i = 0; i < adapter.getCount(); i++) {
            TextView tabView = null;
            // If there is a custom tab view layout id set, try and inflate it
            tabView = createDefaultTabView(mViewPager.getContext());
            tabView.setText(adapter.getPageTitle(i));
//            tabView.setId(i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight=1;
            tablayout.addView(tabView, params);
            tabView.setOnClickListener(tabClickListener);
        }
    }

    /**
     * Create a default view to be used for tabs. This is called if a custom tab view is not set via
     */
    protected TextView createDefaultTabView(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        textView.setTypeface(Typeface.DEFAULT_BOLD);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            // If we're running on Honeycomb or newer, then we can use the Theme's
//            // selectableItemBackground to ensure that the View has a pressed state
//            TypedValue outValue = new TypedValue();
//            context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground,
//                    outValue, true);
//            textView.setBackgroundResource(outValue.resourceId);
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//            // If we're running on ICS or newer, enable all-caps to match the Action Bar tab style
//            textView.setAllCaps(true);
//        }

        int padding = 24;
        textView.setPadding(padding, padding, padding, padding);

        return textView;
    }
    private class TabClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < tablayout.getChildCount(); i++) {
                if (v == tablayout.getChildAt(i)) {
                    tablayout.getChildAt(i).setBackgroundColor(0xffff0000);
                    mViewPager.setCurrentItem(i);
                }else{
                    tablayout.getChildAt(i).setBackgroundColor(0xff00ff00);
                }
            }
        }
    }

}
