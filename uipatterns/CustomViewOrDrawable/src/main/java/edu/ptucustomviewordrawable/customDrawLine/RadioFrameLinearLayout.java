package edu.ptucustomviewordrawable.customDrawLine;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import edu.ptucustomviewordrawable.DeviceUtils;
import edu.ptucustomviewordrawable.LogUtils;


/**
 * Created by anshu.wang on 2017/7/3.
 */

public class RadioFrameLinearLayout extends LinearLayout {
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mWPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int childCount;

    public RadioFrameLinearLayout(Context context) {
        super(context);
        initView();
    }

    public RadioFrameLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RadioFrameLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RadioFrameLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        mPaint.setColor(0xffcacaca);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mWPaint.setColor(0xffffaacc);
        mWPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        mWPaint.setStrokeWidth(5);
        mWPaint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawSeparateLines(canvas);
    }

    //    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        drawSeparateLines(canvas);
//    }
    private int select = -1;

    /**
     * 绘制tab之间的分割线
     *
     * @param canvas
     */
    private void drawSeparateLines(Canvas canvas) {
        childCount = getChildCount();
        LogUtils.logMainInfo("drawSeparateLines " + childCount);
        if (childCount == 0) {
            throw new IllegalArgumentException("SegmentView's child is zero !");
        }

        if (getOrientation() == HORIZONTAL) {
            LogUtils.logMainInfo("drawSeparateLines  getWidth" + DeviceUtils.getScreenSize(this)[0] + ": " + getWidth() + " :" + getPaddingRight());
            float childWidth = (this.getWidth() - getPaddingLeft() - getPaddingRight()) / childCount;
            RectF rectF = new RectF();
            canvas.drawRect( getPaddingLeft(),getPaddingTop(),this.getWidth() - getPaddingLeft() - getPaddingRight(),getHeight()-getPaddingTop()-getPaddingBottom(),mPaint);
            for (int i = 0; i < childCount-1; i++) {// { |█|█|█| } 共两条分割线
                float startX = getPaddingLeft() + childWidth * i;
                float endX = startX + childWidth;


                LogUtils.logMainInfo("drawSeparateLines  startX" + startX + " " + getHeight());
                if (select != i) {
//                    if (select== -1 || select != i - 1) {
//                        canvas.drawLine(startX, getHeight(), startX, 0, mWPaint);//左分割线
//                        LogUtils.logMainInfo(String.format("drawSeparateLines 左分割线 %d  %d  %d   %d ", (int) startX, getHeight(), (int) startX, 0));
//                    }
//                    canvas.drawLine(startX, 0, endX, 0, mPaint);//上分割线
//                    LogUtils.logMainInfo(String.format("drawSeparateLines 上分割线 %d  %d  %d   %d ", (int) startX, 0, (int) endX, 0));
//                    canvas.drawLine(startX, getHeight(), endX, getHeight(), mPaint);//下分割线
//                    LogUtils.logMainInfo(String.format("drawSeparateLines 下分割线 %d  %d  %d   %d ", (int) startX, getHeight(), (int) endX, getHeight()));
//                    if (i == childCount - 1) {
//                        canvas.drawLine(endX, getHeight(), endX, 0, mWPaint);//右分割分割线
//                        LogUtils.logMainInfo(String.format("drawSeparateLines 右分割线  %d  %d  %d   %d ", (int) endX, getHeight(), (int) endX, 0));
//                    }
                    canvas.drawBitmap();
                }
            }
            drawSelect(canvas,mWPaint);

        } else {//TODO　增加垂直方向的　segmentView
            int childHeight = this.getHeight() / childCount;
            for (int i = 1; i < childCount; i++) {
                int startY = childHeight * i;
                canvas.drawLine(0, startY, this.getWidth() - 0, startY, mPaint);
            }
            canvas.drawLine(0, 0, getWidth(), 0, mPaint);
            canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), mPaint);
            canvas.drawLine(getWidth(), getHeight(), 0, getHeight(), mPaint);
            canvas.drawLine(0, getHeight(), 0, 0, mPaint);
        }

    }

    private void drawSelect(Canvas canvas, Paint mWPaint) {
        canvas.save();
//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        float childWidth = (this.getWidth() - getPaddingLeft() - getPaddingRight()) / childCount;
        float startX = getPaddingLeft() + childWidth * select;
        float endX = startX + childWidth;
        canvas.drawRect(startX,getPaddingTop(),endX,getHeight()-getPaddingTop()-getPaddingBottom(),mWPaint);
        canvas.restore();
    }

    public void setSelect(int select) {
        this.select = select;
    }

    public void unSelect() {
        this.select = -1;
    }


}
