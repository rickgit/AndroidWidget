package edu.ptu.demo.test;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by anshu.wang on 2016/11/3.
 */

public class HeaderScrollView extends ScrollView {

    public HeaderScrollView(Context context) {
        super(context);
    }

    public HeaderScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
            return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        System.out.println(ev.getX()+" ");
        return super.onTouchEvent(ev);

    }

    @Override
    public void postInvalidateOnAnimation() {
        super.postInvalidateOnAnimation();
    }
}
