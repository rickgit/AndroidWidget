package edu.ptu.customview.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author anshu.wang
 * @version 1.0
 * @time 2017/10/27.
 */
public class LotteryNumTextView extends android.support.v7.widget.AppCompatTextView {

    public LotteryNumTextView(Context context) {
        super(context);
    }

    public LotteryNumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LotteryNumTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
