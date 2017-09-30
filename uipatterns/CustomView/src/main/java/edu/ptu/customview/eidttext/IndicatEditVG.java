package edu.ptu.customview.eidttext;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * @author anshu.wang
 * @version 1.0
 * @time 2017/9/30.
 */
public class IndicatEditVG extends RelativeLayout {
    public IndicatEditVG(Context context) {
        super(context);
    }

    public IndicatEditVG(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IndicatEditVG(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
