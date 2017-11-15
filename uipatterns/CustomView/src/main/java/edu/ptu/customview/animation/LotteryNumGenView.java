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
    private String text = "01";
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

    public void startGen() {
        dex = 1000;//三秒加速到最大速度
        handler.removeMessages(0);
        handler.sendEmptyMessageDelayed(0, 1);
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    float dex = 2000;//一秒后加速到全部，更换数字，减速
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (dex > 1000) {
                text = "16";
            }
            dex=dex+20;
            float v = 80 * mInterpolator.getInterpolation(dex / 3000);//最大速度每毫秒
            mOffset = (int) (mOffset + v);
            if (mOffset >= 360) {
                mOffset = 0;
            }
            if (dex <= 3000) {
                invalidate();
                sendEmptyMessage(0);
            }else { mOffset = 0;
            }
        }
    };
}
