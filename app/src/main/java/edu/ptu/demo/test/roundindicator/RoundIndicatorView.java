package edu.ptu.demo.test.roundindicator;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by anshu.wang on 2017/4/17.
 */

public class RoundIndicatorView extends View {

    private RoundIndicatorBean roundIndicatorBean;

    public RoundIndicatorView(Context context) {
        super(context);
        roundIndicatorBean = new RoundIndicatorBean(240);
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
        canvas.drawColor(Color.BLACK);
        drawPanelFrame(canvas);
        drawRule(canvas);
        drawPointer(canvas);
        drawProcess(canvas);
        drawScale(canvas);
        drawFont(canvas);
    }


    private void drawPanelFrame(Canvas canvas) {
        RectF oval = new RectF();                     //RectF对象
        oval.left = roundIndicatorBean.getPanelFrame().width;                              //左边
        oval.top = roundIndicatorBean.getPanelFrame().width;                                   //上边
        oval.right = roundIndicatorBean.getPanel()[0] - roundIndicatorBean.getPanelFrame().width;                             //右边
        oval.bottom = roundIndicatorBean.getPanel()[0] - roundIndicatorBean.getPanelFrame().width;

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(roundIndicatorBean.getPanelFrame().bgColor);
        paint.setStrokeWidth(roundIndicatorBean.getPanelFrame().width);

        canvas.drawArc(oval, roundIndicatorBean.getPanelFrame().startAngle, roundIndicatorBean.getPanelFrame().sweepAngle, false, paint);
    }

    private void drawRule(Canvas canvas) {
        RectF oval = new RectF();                     //RectF对象
        oval.left = roundIndicatorBean.getPanel()[0] / 2 - roundIndicatorBean.getDashRule().width - roundIndicatorBean.getDashRule().radius;                              //左边
        oval.top = roundIndicatorBean.getPanel()[0] / 2 - roundIndicatorBean.getDashRule().width - roundIndicatorBean.getDashRule().radius;                                   //上边
        oval.right = roundIndicatorBean.getPanel()[0] / 2 + roundIndicatorBean.getDashRule().width + roundIndicatorBean.getDashRule().radius;                             //右边
        oval.bottom = roundIndicatorBean.getPanel()[0] / 2 + roundIndicatorBean.getDashRule().width + roundIndicatorBean.getDashRule().radius;

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(roundIndicatorBean.getDashRule().bgColor);
        paint.setStrokeWidth(roundIndicatorBean.getDashRule().width);

        canvas.drawArc(oval, roundIndicatorBean.getDashRule().startAngle, roundIndicatorBean.getDashRule().sweepAngle, false, paint);
    }

    private void drawProcess(Canvas canvas) {
        RectF oval = new RectF();                     //RectF对象
        oval.left = roundIndicatorBean.getPanel()[0] / 2 - roundIndicatorBean.getProcess().width - roundIndicatorBean.getProcess().radius;                              //左边
        oval.top = roundIndicatorBean.getPanel()[0] / 2 - roundIndicatorBean.getProcess().width - roundIndicatorBean.getProcess().radius;                                   //上边
        oval.right = roundIndicatorBean.getPanel()[0] / 2 + roundIndicatorBean.getProcess().width + roundIndicatorBean.getProcess().radius;                             //右边
        oval.bottom = roundIndicatorBean.getPanel()[0] / 2 + roundIndicatorBean.getProcess().width + roundIndicatorBean.getProcess().radius;

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(roundIndicatorBean.getProcess().bgColor);
        paint.setStrokeWidth(roundIndicatorBean.getProcess().width);

        canvas.drawArc(oval, roundIndicatorBean.getProcess().startAngle, roundIndicatorBean.getProcess().sweepAngle, false, paint);
    }

    private void drawPointer(Canvas canvas) {

        // 绘制这个三角形,你可以绘制任意多边形
        Path path = new Path();
        float left = roundIndicatorBean.getPanel()[0] / 2 - roundIndicatorBean.getProcess().width - roundIndicatorBean.getProcess().radius - roundIndicatorBean.getPointWidth();
        float top = roundIndicatorBean.getPanel()[0] / 2 - roundIndicatorBean.getPointWidth() / 2;
        float bottom = top + roundIndicatorBean.getPointWidth();
        float right = left + roundIndicatorBean.getPointWidth();
        path.moveTo(left - 10, top);// 此点为多边形的起点
        path.lineTo(left - 10, bottom);
        path.lineTo(right - 10, bottom - roundIndicatorBean.getPointWidth() / 2);
        path.close(); // 使这些点构成封闭的多边形

        Paint paint = new Paint();
//        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(roundIndicatorBean.getProcess().bgColor);
        paint.setStrokeWidth(roundIndicatorBean.getPointWidth());

        canvas.save(Canvas.MATRIX_SAVE_FLAG); //Saving the canvas and later restoring it so only this image will be rotated.
        canvas.rotate(roundIndicatorBean.getProcess().sweepAngle, roundIndicatorBean.getPanel()[0] / 2, roundIndicatorBean.getPanel()[0] / 2);
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    private void drawScale(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(roundIndicatorBean.getProcess().bgColor);
        paint.setStrokeWidth(roundIndicatorBean.getScale().length);

        RectF oval = new RectF();                     //RectF对象
        oval.left = roundIndicatorBean.getPanel()[0] / 2 - roundIndicatorBean.getScale().length - roundIndicatorBean.getScale().radius;                              //左边
        oval.top = roundIndicatorBean.getPanel()[0] / 2 - roundIndicatorBean.getScale().length - roundIndicatorBean.getScale().radius;                                   //上边
        oval.right = roundIndicatorBean.getPanel()[0] / 2 + roundIndicatorBean.getScale().length + roundIndicatorBean.getScale().radius;                             //右边
        oval.bottom = roundIndicatorBean.getPanel()[0] / 2 + roundIndicatorBean.getScale().length + roundIndicatorBean.getScale().radius;

        canvas.save(Canvas.MATRIX_SAVE_FLAG); //Saving the canvas and later restoring it so only this image will be rotated.
        canvas.rotate(roundIndicatorBean.getScale().off, roundIndicatorBean.getPanel()[0] / 2, roundIndicatorBean.getPanel()[0] / 2);//正数 ，顺时针旋转
        for (int i = 0; i < roundIndicatorBean.getScale().scaleSize; i++) {
            int color = roundIndicatorBean.getScale().getColor(i * roundIndicatorBean.getScale().scaleSize + roundIndicatorBean.getScale().off, roundIndicatorBean.getProcess().sweepAngle);
            paint.setColor(color);
            canvas.drawArc(oval, 180 + roundIndicatorBean.getScale().lineWidth / 2, roundIndicatorBean.getScale().lineWidth, false, paint);
            canvas.rotate(roundIndicatorBean.getScale().scaleSize, roundIndicatorBean.getPanel()[0] / 2, roundIndicatorBean.getPanel()[0] / 2);
        }
        canvas.restore();
    }

    private void drawFont(Canvas canvas) {
        Paint mPaint = new Paint();
//        mPaint.setStrokeWidth(3);
        mPaint.setTextSize(roundIndicatorBean.getType().fontSize);
        mPaint.setColor(roundIndicatorBean.getType().fontColor);
        mPaint.setTextAlign(Paint.Align.LEFT);
        Rect bounds = new Rect();
        mPaint.getTextBounds(roundIndicatorBean.getType().text, 0, roundIndicatorBean.getType().text.length(), bounds);
        canvas.drawText(roundIndicatorBean.getType().text, roundIndicatorBean.getPanel()[0] / 2 - bounds.width() / 2, roundIndicatorBean.getPanel()[0] / 2- 4, mPaint);

        Paint valuePain = new Paint();
        valuePain.setTextSize(roundIndicatorBean.getValue().fontSize);
        valuePain.setColor(roundIndicatorBean.getValue().fontColor);
        valuePain.setTextAlign(Paint.Align.LEFT);
        Rect valueBound = new Rect();
        valuePain.getTextBounds(roundIndicatorBean.getValue().text, 0, roundIndicatorBean.getValue().text.length() , valueBound);
        //数值
        canvas.drawText(roundIndicatorBean.getValue().text, roundIndicatorBean.getPanel()[0] / 2 - valueBound.width() / 2, roundIndicatorBean.getPanel()[0] / 2 - 4 - bounds.height(), valuePain);
    }

    public void setValue(String ratio) {
        float value=0;

        if (ratio!=null||!ratio.equals("")||ratio.contains("%")){
            value = Float.parseFloat(ratio.replace("%", ""))/100f;
        }
        if (value == 0) {
            roundIndicatorBean.getProcess().sweepAngle = roundIndicatorBean.getScale().off;
        } else if (value < 1.6) {//0~160%，没刻度占用20%
            float allAngle = roundIndicatorBean.getScale().off + roundIndicatorBean.getScale().scaleSize * 8;
            float allValue = 1.6f;
            roundIndicatorBean.getProcess().sweepAngle = allAngle / allValue * value;
        } else if (value < 2.8) {//每40%用一个刻度
            float allAngle = roundIndicatorBean.getScale().off + roundIndicatorBean.getScale().scaleSize * 11;
            float allValue = 2.8f;
            roundIndicatorBean.getProcess().sweepAngle = allAngle / allValue * value;
        } else {
            roundIndicatorBean.getProcess().sweepAngle = roundIndicatorBean.getScale().off + roundIndicatorBean.getScale().scaleSize * 11;
        }
        roundIndicatorBean.getValue().text=ratio;
        invalidate();
    }
}
