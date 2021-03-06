package edu.ptu.demo.test.scrollview;

/**
 * Created by anshu.wang on 2016/11/16.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * 屏蔽 滑动事件
 * Created by fc on 2015/7/16.
 */
public   class MyScrollView extends ScrollView {
    private int downX;
    private int downY;
    private int mTouchSlop;

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    private boolean isTop = false;//是不是滑动到了最低端 ；使用这个方法，解决了上拉加载的问题
    private OnScrollToBottomListener onScrollToBottom;

    public MyScrollView(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
                                  boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if(scrollY != 0 && null != onScrollToBottom &&isTop()){
            onScrollToBottom.onScrollBottomListener(clampedY);
        }
    }

    public void setOnScrollToBottomLintener(OnScrollToBottomListener listener){
        onScrollToBottom = listener;
    }

    public interface OnScrollToBottomListener{
        public void onScrollBottomListener(boolean isBottom);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                setTop(false);
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                Log.i("-----::----downY-----::",downY+"");
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                Log.i("-----::----moveY-----::",moveY+"");
                /****判断是向下滑动，才设置为true****/
                if(downY-moveY>0){
                    setTop(true);
                }else{
                    setTop(false);
                }
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }
}