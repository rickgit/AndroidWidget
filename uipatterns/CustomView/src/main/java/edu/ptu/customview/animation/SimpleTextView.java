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
import android.widget.TextView;

import static edu.ptu.customview.animation.LotteryScrollNumView.dpToPx;

/**
 * @author anshu.wang
 * @version 1.0
 * @time 2017/11/8.
 */
public class SimpleTextView extends View {
    private Paint textPaint;
    private float mOffset=0f;
    private Rect mTextBounds = new Rect();
    private int mTextHeight;
    private int mTextCenterX;

    public SimpleTextView(Context context) {
        super(context);
        initPaint();
    }

    public SimpleTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public SimpleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SimpleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint();
    }

    private void initPaint() {
        if (textPaint != null)
            return;
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(dpToPx(getContext(), 23));
        textPaint.setColor(0xffcc00ca);


    }
    public static int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources()
                .getDisplayMetrics());
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        textPaint.getTextBounds("00", 0, 2, mTextBounds);
        mTextHeight = mTextBounds.height();
        setMeasuredDimension(dpToPx(getContext(),43), dpToPx(getContext(),43));
        mTextCenterX = (getMeasuredWidth() - getPaddingLeft() - getPaddingRight()) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(0,mOffset);
        canvas.drawText("02", getMeasuredWidth()/2, -getMeasuredHeight()/2 - mTextHeight / 2, textPaint);
        canvas.drawText("03", getMeasuredWidth()/2, 0, textPaint);
        canvas.drawText("04", getMeasuredWidth()/2, getMeasuredHeight()/2 + mTextHeight / 2, textPaint);
    }

    public void setmOffset(float mOffset) {
        this.mOffset = mOffset;
        invalidate();
    }
    public void addOffset(float mOffset) {
        this.mOffset+= 1;
        invalidate();
    }
}
