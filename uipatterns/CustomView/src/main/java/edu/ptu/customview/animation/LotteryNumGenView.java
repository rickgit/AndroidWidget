package edu.ptu.customview.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * @author anshu.wang
 * @version 1.0
 * @time 2017/10/27.
 */
public class LotteryNumGenView extends View {
    private int mOffset = 90;
    private Paint textPaint;
    private String text="01";
    private Rect mTextBounds = new Rect();
    private int mTextCenterX;
    private int mTextHeight;

    private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    public LotteryNumGenView(Context context) {
        this(context, null);
    }


    public LotteryNumGenView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LotteryNumGenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(13);
        textPaint.setColor(0xffcc00ca);

        measureTextHeight();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = getCustomWidth(widthMeasureSpec);
        int measureHeight = getCustomHeight(heightMeasureSpec);
        setMeasuredDimension(measureWidth, measureHeight);

        mTextCenterX = (getMeasuredWidth() - getPaddingLeft() - getPaddingRight()) / 2;
    }

    private void measureTextHeight() {
        textPaint.getTextBounds("00", 0, 2, mTextBounds);
        mTextHeight = mTextBounds.height();
    }

    private int getCustomHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                textPaint.getTextBounds("00", 0, 2, mTextBounds);
                result = mTextBounds.height() + 20;
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, size) : result;
        return result + getPaddingTop() + getPaddingBottom();
    }

    private int getCustomWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                textPaint.getTextBounds("00", 0, 2, mTextBounds);
                result = mTextBounds.width() + 15;
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, size) : result;
        return result + getPaddingLeft() + getPaddingRight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.rotate(mOffset, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        int y = getMeasuredHeight() / 2;
        canvas.drawText(text, mTextCenterX, y + mTextHeight / 2, textPaint);//textPaint 是居中对齐
        canvas.restore();
    }

    public void startGen() {handler.removeMessages(0);
        handler.sendEmptyMessage(0);
    }
    public void stopGen() {
        handler.removeMessages(0);
    }

    public void setText(String text) {
        this.text = text;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mOffset = (int) (mOffset+ 80*mInterpolator.getInterpolation(0.45f));
            if (mOffset>=360) {
                mOffset = 0;
            }
            invalidate();
            sendEmptyMessage(0);
        }
    };
}
