package edu.ptu.navpattern.tablayout;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.ptu.navpattern.LogUtils;
import edu.ptu.navpattern.R;

/**
 * Created by rick.wang on 2017/8/3.
 */

public class SlidingViewPagerHelper {
    private SlidingTablayout tablayout;
    private ViewPager mViewPager;

    public void bindViewPager(ViewPager vp, SlidingTablayout slidingTablayout) {
        this.mViewPager = vp;
        this.tablayout = slidingTablayout;
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tablayout.setmCurrentTab(position);
                tablayout.setmCurrentPositionOffset(positionOffset);
                tablayout.scrollToCurrentTab();
                tablayout.invalidate();
                LogUtils.logMainInfo(" position :" + position + "; offset " + positionOffset + " ;" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                tablayout.updateTabStyles(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        populateTabStrip();
    }

    private void populateTabStrip() {
        final PagerAdapter adapter = mViewPager.getAdapter();

        for (int i = 0; i < adapter.getCount(); i++) {
            ViewGroup tabView = null;
            // If there is a custom tab view layout id set, try and inflate it
            tabView = createDefaultTabView(mViewPager.getContext(), adapter.getPageTitle(i));
//            tabView.setId(i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            tablayout.getTabsContainer().addView(tabView, params);

            final ViewGroup finalTabView = tabView;
            if (i%2==1)
            tabView.setBackgroundColor(0xff000000);
            tabView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    tablayout.setIndicatorWidth(finalTabView.getWidth());
                }
            });
            tabView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = tablayout.getTabsContainer().indexOfChild(v);
                    if (position != -1) {
                        if (mViewPager.getCurrentItem() != position) {
//                            if (mSnapOnTabClick) {
//                                mViewPager.setCurrentItem(position, false);
//                            } else {
                            mViewPager.setCurrentItem(position);
//                            }

//                            if (mListener != null) {
//                                mListener.onTabSelect(position);
//                            }
                        } else {
//                            if (mListener != null) {
//                                mListener.onTabReselect(position);
//                            }
                        }
                    }
                }
            });
        }
    }

    private ViewGroup createDefaultTabView(Context context, CharSequence pageTitle) {
        ViewGroup vg = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.layout_tab, null);
        TextView textView = (TextView) vg.getChildAt(0);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setText(pageTitle);
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
        return vg;
    }


}
