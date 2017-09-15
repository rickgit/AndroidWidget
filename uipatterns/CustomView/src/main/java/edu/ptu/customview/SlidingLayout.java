package edu.ptu.customview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.Random;

import edu.ptu.customview.utils.LogUtils;

/**
 * Created by rick.wang on 2017/9/13.
 */

public class SlidingLayout extends FrameLayout {
    private ViewDragHelper mDragHelper;

    /**
     * Minimum velocity that will be detected as a fling
     */
    private static final int MIN_FLING_VELOCITY = 400; // dips per second
    private View dragView;

    public SlidingLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public SlidingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlidingLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SlidingLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
            return (Math.abs(dy) <= Math.abs(dx));
        }
    }

    View touchView;

    public void init(Context context) {
        if (mDragHelper == null) {
            //构建GestureDetector实例
            mGestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {


                @Override
                public boolean onDown(MotionEvent event) {
                    mDragHelper.processTouchEvent(event);
                    for (int i = 0; i < getChildCount(); i++) {
                        View childAt = getChildAt(i);
                        boolean viewUnder = mDragHelper.isViewUnder(childAt, (int) event.getX(), (int) event.getY());
                        if (viewUnder) {
                            touchView = childAt;
                            childAt.dispatchTouchEvent(event);
                        }
                    }
                    mDownY = (int) event.getY();
                    return true;
                }

                int mDownY;


                @Override
                public boolean onSingleTapUp(MotionEvent event) {

                    boolean viewUnder = mDragHelper.isViewUnder(touchView, (int) event.getX(), (int) event.getY());
                    if (viewUnder)
                        touchView.dispatchTouchEvent(event);

                    return true;
                }

                @Override
                public boolean onScroll(MotionEvent motionEvent, MotionEvent event, float v, float v1) {
                    if (getChildAt(2).getY() == 0 && event.getY() - mDownY <= 0 || ((ViewGroup) getChildAt(2)).getScrollY() > 0) {
                        boolean viewUnder = mDragHelper.isViewUnder(touchView, (int) event.getX(), (int) event.getY());
                        if (viewUnder)
                            touchView.dispatchTouchEvent(event);
                    } else {
                        mDragHelper.processTouchEvent(event);
                        if (touchView != null) {
                            MotionEvent obtain = MotionEvent.obtain(event);
                            obtain.setAction(MotionEvent.ACTION_CANCEL);//取消longclick,取消listview滑动
                            touchView.dispatchTouchEvent(obtain);
                            touchView = null;
                        }
                    }
                    return true;
                }

                @Override
                public void onShowPress(MotionEvent e) {
                    super.onShowPress(e);
                    LogUtils.logMainInfo("显示 释放");
                }

                @Override
                public boolean onFling(MotionEvent motionEvent, MotionEvent event, float v, float v1) {

                    if (getChildAt(2).getY() == 0 && event.getY() - mDownY <= 0 || ((ViewGroup) getChildAt(2)).getScrollY() > 0) {
                        boolean viewUnder = mDragHelper.isViewUnder(touchView, (int) event.getX(), (int) event.getY());
                        if (viewUnder)
                            touchView.dispatchTouchEvent(event);
                    } else {
                        mDragHelper.processTouchEvent(event);
                        if (touchView != null)
                            touchView.setLongClickable(false);
                    }
                    return true;
                }
            });
            //手势处理类
            mDragHelper = ViewDragHelper.create(this, 0.5f, new ViewDragHelper.Callback() {
                @Override
                public boolean tryCaptureView(View child, int pointerId) {
                    LogUtils.logMainInfo("view " + child);
                    boolean b = child == getChildAt(2);

                    return b;
                }

                @Override
                public int clampViewPositionVertical(View child, int top, int dy) {
                    final int topBound = getPaddingTop();
                    final int bottomBound = getHeight() - dragView.getHeight() - topBound;

                    final int newtop = Math.min(Math.max(top, topBound), bottomBound);
                    return newtop;
                }

                //手指释放的时候回调
                @Override
                public void onViewReleased(View releasedChild, float xvel, float yvel) {
                    //mAutoBackView手指释放时可以自动回去
//                    if (releasedChild == mAutoBackView)
//                    {
                    int finalTop = (new Random().nextInt(400));
//                    mDragHelper.settleCapturedViewAt(0, 0);
//                    mDragHelper.smoothSlideViewTo(getChildAt(0),0,-getChildAt(0).getHeight());
                    if (releasedChild.getY() < getChildAt(0).getHeight() / 2)
                        mDragHelper.smoothSlideViewTo(releasedChild, 0, 0);
                    else if (releasedChild.getY() >= getChildAt(0).getHeight() / 2 && releasedChild.getY() < getChildAt(0).getHeight() * 3 / 2)
                        mDragHelper.smoothSlideViewTo(releasedChild, 0, getChildAt(0).getHeight());
                    else if (releasedChild.getY() >= getChildAt(0).getHeight() * 3 / 2)
                        mDragHelper.smoothSlideViewTo(releasedChild, 0, getChildAt(0).getHeight() * 2);
                    ViewCompat.postInvalidateOnAnimation(SlidingLayout.this);
//                    invalidate();

//                    }
                }

//                @Override
//                public int getViewHorizontalDragRange(View child) {
//                   return 120;
//                }
////
//                @Override
//                public int getViewVerticalDragRange(View child) {
//                    return 120;
//                }

                public int clampViewPositionHorizontal(View child, int left, int dx) {
//                    final int leftBound = getPaddingLeft();
//                    final int rightBound = getWidth() - dragView.getWidth() - leftBound;

//                    final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                    final int newLeft = 0;

                    return newLeft;
                }

                @Override

                public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                    super.onViewPositionChanged(changedView, left, top, dx, dy);

                    //拖动蓝色方块时，红色也跟随移动
                    int t = changedView.getTop() - getChildAt(1).getHeight();
//                    getChildAt(1).layout(getChildAt(1).getLeft(), t,
//                            getChildAt(1).getRight() , changedView.getTop() );
                    ViewCompat.offsetTopAndBottom(getChildAt(1), dy);
//                    if (t>=0){
//                        getChildAt(0).layout(getChildAt(0).getLeft(), t-getChildAt(0).getHeight(),
//                                getChildAt(0).getRight() , t);
                    ViewCompat.offsetTopAndBottom(getChildAt(0), dy);
//                    }

                }

                @Override
                public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                    super.onEdgeDragStarted(edgeFlags, pointerId);
//                    mDragHelper.captureChildView(getChildAt(2), pointerId);
                }
            });
            final float density = context.getResources().getDisplayMetrics().density;
            mDragHelper.setMinVelocity(MIN_FLING_VELOCITY * density);
//            mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_TOP);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean b = mDragHelper.shouldInterceptTouchEvent(event);
        LogUtils.logMainInfo("执行onInterceptTouchEvent " + b);

        return true;//&& gestureDetector.onTouchEvent(event)
    }

    int downX;
    int downY;
    int touchEvent = 0;
    GestureDetectorCompat mGestureDetector;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isUsed = mGestureDetector.onTouchEvent(event);
        LogUtils.logMainInfo("显示是否消耗" + isUsed);
        if (!isUsed && (MotionEvent.ACTION_CANCEL == event.getAction() || MotionEvent.ACTION_UP == event.getAction()))//一直在原点按着
        {
            mDragHelper.processTouchEvent(event);
        }
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        dragView = getChildAt(0);
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            LogUtils.logMainInfo("重新布局 computeScroll");
            postInvalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        LogUtils.logMainInfo("重新布局 onLayout");
        View childView = getChildAt(0);
        int measureHeight = childView.getMeasuredHeight();
        int measuredWidth = childView.getMeasuredWidth();

        childView.layout(left, -measureHeight, right, 0);
        View contentViewc = getChildAt(1);
        // 获取在onMeasure中计算的视图尺寸
        int measureHeightc = contentViewc.getMeasuredHeight();
        int measuredWidthc = contentViewc.getMeasuredWidth();

        contentViewc.layout(left, -measureHeightc - measureHeight, right, -measureHeight);
        View contentView = getChildAt(2);
        // 获取在onMeasure中计算的视图尺寸
        int measureHeight2 = contentView.getMeasuredHeight();
        int measuredWidth2 = contentView.getMeasuredWidth();

        contentView.layout(left, 0, right, bottom);

    }
}
