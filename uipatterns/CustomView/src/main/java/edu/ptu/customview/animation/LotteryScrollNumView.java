package edu.ptu.customview.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import edu.ptu.customview.utils.DeviceUtils;

/**
 * @author anshu.wang
 * @version 1.0
 * @time 2017/11/8.
 */
public class LotteryScrollNumView extends View {
    private int mTextCenterX;
    private Paint textPaint;
    private Rect mTextBounds = new Rect();
    private int mTextHeight;
    private float mOffset=0f;
    private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    public LotteryScrollNumView(Context context) {
        super(context);
        initPaint();
    }

    public LotteryScrollNumView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public LotteryScrollNumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LotteryScrollNumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint();
    }

    private void initPaint() {
        if (textPaint != null)
            return;
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(dpToPx(getContext(),23));
        textPaint.setColor(0xffcc00ca);
        measureTextHeight();
    }
    public static int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources()
                .getDisplayMetrics());
    }
    private void measureTextHeight() {
        textPaint.getTextBounds("00", 0, 2, mTextBounds);
        mTextHeight = mTextBounds.height();
    }


    private int mPreNum = 1;
    private int mPostNum = 2;
    private int mCurNum = 3;

    private int measureHeight(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int val = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                textPaint.getTextBounds("0", 0, 1, mTextBounds);
                result = mTextBounds.height() *3;
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        return result + getPaddingTop() + getPaddingBottom();
    }

    private int measureWidth(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int val = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                textPaint.getTextBounds("0", 0, 1, mTextBounds);
                result = mTextBounds.width();
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        return result + getPaddingLeft() + getPaddingRight() + 15;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
        mTextCenterX = (getMeasuredWidth() - getPaddingLeft() - getPaddingRight()) / 2;
    }

    private void drawNext(Canvas canvas, float mOffset) {
        int y = getMeasuredHeight() / 2;
        textPaint.setTextSize(dpToPx(getContext(),23)-mOffset*12);
        if (mPostNum < 10)
            canvas.drawText("0" + mPostNum + "", mTextCenterX, y -3* mTextHeight/2 ,
                    textPaint);
        else if (mPostNum < 10)
            canvas.drawText(mPostNum + "", mTextCenterX, y -3* mTextHeight/2,
                    textPaint);
    }

    private void drawSelf(Canvas canvas, float mOffset) {
        int y = getMeasuredHeight() / 2;
        textPaint.setTextSize(dpToPx(getContext(),23)+mOffset*12);
        if (mCurNum < 10)
            canvas.drawText("0" + mCurNum + "", mTextCenterX, y + mTextHeight / 2, textPaint);
        else canvas.drawText(mCurNum + "", mTextCenterX, y + mTextHeight / 2, textPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (System.currentTimeMillis() != endTimestamp) {
            postDelayed(mScrollRunnable, 0);
            if (mOffset <= -1) {
                mOffset = 0;
                calNum(mCurNum + 1);
            }
        }

        canvas.translate(0, -mOffset * getMeasuredHeight());
        drawSelf(canvas,mOffset);
        drawNext(canvas,mOffset);
//        canvas.restore();
    }

    private void calNum(int number) {
        number = number == -1 ? 42 : number;
        number = number == 43 ? 0 : number;
        mPreNum = number - 1 == 0 ? 42 : number - 1;
        mCurNum = number;
        mPostNum = number + 1 == 43 ? 0 : number + 1;
    }

    //
    private long endTimestamp;
    private long during;
    private Runnable mScrollRunnable = new Runnable() {
        @Override
        public void run() {
            float x = (float) (1 - 1.0 * (endTimestamp - System.currentTimeMillis()) / during);
            mOffset -= 0.15f * (1 - mInterpolator.getInterpolation(x) + 0.1);
            invalidate();
        }
    };


    public void setNumber(final int num, final long endTs, long delay){
        postDelayed(new Runnable() {
            @Override
            public void run() {
                 mCurNum=num;
                endTimestamp=endTs;
                during=endTimestamp-System.currentTimeMillis();
                mOffset=0f;
                invalidate();
            }
        }, delay);

    }
}
