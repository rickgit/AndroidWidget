package edu.ptu.customview.viewpager;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * @author anshu.wang
 * @version 1.0
 * @describe
 * @time 2017/9/22.
 */
public class CustomHorizalScrollView extends HorizontalScrollView{
    public CustomHorizalScrollView(Context context) {
        super(context);
    }

    public CustomHorizalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomHorizalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomHorizalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
