package edu.ptu.demo.test.roundindicator;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by anshu.wang on 2017/4/17.
 */

public class RoundIndicatorView extends View {
    public RoundIndicatorView(Context context) {
        super(context);
    }

    public RoundIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RoundIndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPanelFrame(canvas);
        drawRule(canvas);
        drawProcess(canvas);
        drawScale(canvas);
        drawFont(canvas);
    }

    private void drawPanelFrame(Canvas canvas) {

    }

    private void drawRule(Canvas canvas) {
    }

    private void drawProcess(Canvas canvas) {

    }

    private void drawScale(Canvas canvas) {

    }

    private void drawFont(Canvas canvas) {

    }


}
