package edu.ptu.demo.test.roundindicator;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static edu.ptu.demo.test.roundindicator.RoundIndicatorBean.dp2px;


/**
 * Created by anshu.wang on 2017/4/17.
 */

public class RoundIndicatorView extends View {

    private RoundIndicatorBean roundIndicatorBean;
    private RectF ovalRect;
    private Paint ovalPaint;
    private Path pointerPath;
    private Paint pointerPaint;
    private Rect valueRect;
    private Paint valuePaint;
    private Rect typeRect;
    private Paint typePaint;
    private Paint scalePaint;
    private RectF scaleRect;
    private int width;
    private int height;

    public RoundIndicatorView(Context context) {
        super(context);
        initData();
    }


    public RoundIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public RoundIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RoundIndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initData();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
//        canvas.translate((width - roundIndicatorBean.getPanel()[0]) / 2, (height - roundIndicatorBean.getPanel()[1]) / 2);
        System.out.println(" width =" + width + ",height =" + height);
//        System.out.println(" 屏幕宽度 =" + AppConfig.S_scrWidth + ",屏幕高度 =" + AppConfig.S_scrHeight);
//        System.out.println(" 100 width =" + UiTool.dpToPx(100));
        canvas.drawColor(Color.WHITE);
        drawPanelFrame(canvas);
        drawScale(canvas);
        drawRule(canvas);
        drawProcess(canvas);

        drawPointer(canvas);


        drawFont(canvas);
        canvas.restore();
    }

    private void initData() {
        roundIndicatorBean = new RoundIndicatorBean(280);
        ovalRect = new RectF();
        ovalPaint = new Paint();
        pointerPath = new Path();
        pointerPaint = new Paint();
        scalePaint = new Paint();
        scaleRect = new RectF();
        valuePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        valueRect = new Rect();
        typePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        typeRect = new Rect();
    }

    private void drawPanelFrame(Canvas canvas) {
        //RectF对象
        ovalRect.left = roundIndicatorBean.getPanelFrame().strokeWidth;                              //左边
        ovalRect.top = roundIndicatorBean.getPanelFrame().strokeWidth;                                   //上边
        ovalRect.right = roundIndicatorBean.getPanel()[0] - roundIndicatorBean.getPanelFrame().strokeWidth;                             //右边
        ovalRect.bottom = roundIndicatorBean.getPanel()[0] - roundIndicatorBean.getPanelFrame().strokeWidth;


        ovalPaint.setStyle(Paint.Style.STROKE);
        ovalPaint.setAntiAlias(true);
        ovalPaint.setColor(roundIndicatorBean.getPanelFrame().bgColor);
        ovalPaint.setStrokeWidth(roundIndicatorBean.getPanelFrame().strokeWidth);

        canvas.drawArc(ovalRect, roundIndicatorBean.getPanelFrame().startAngle, roundIndicatorBean.getPanelFrame().sweepAngle, false, ovalPaint);
    }

    private void drawRule(Canvas canvas) {
        //RectF对象
        ovalRect.left = roundIndicatorBean.getPanel()[0] / 2 - roundIndicatorBean.getDashRule().radius;                              //左边
        ovalRect.top = roundIndicatorBean.getPanel()[0] / 2 - roundIndicatorBean.getDashRule().radius;                                   //上边
        ovalRect.right = roundIndicatorBean.getPanel()[0] / 2 + roundIndicatorBean.getDashRule().radius;                             //右边
        ovalRect.bottom = roundIndicatorBean.getPanel()[0] / 2 + roundIndicatorBean.getDashRule().radius;


        ovalPaint.setStyle(Paint.Style.STROKE);
        ovalPaint.setAntiAlias(true);
        ovalPaint.setColor(roundIndicatorBean.getDashRule().bgColor);
        ovalPaint.setStrokeWidth(roundIndicatorBean.getDashRule().strokeWidth);

        canvas.drawArc(ovalRect, roundIndicatorBean.getDashRule().startAngle, roundIndicatorBean.getDashRule().sweepAngle, false, ovalPaint);
    }

    private void drawProcess(Canvas canvas) {
        //RectF对象
        ovalRect.left = roundIndicatorBean.getPanel()[0] / 2 - roundIndicatorBean.getProcess().radius;                              //左边
        ovalRect.top = roundIndicatorBean.getPanel()[0] / 2 - roundIndicatorBean.getProcess().radius;                                   //上边
        ovalRect.right = roundIndicatorBean.getPanel()[0] / 2 + roundIndicatorBean.getProcess().radius;                             //右边
        ovalRect.bottom = roundIndicatorBean.getPanel()[0] / 2 + roundIndicatorBean.getProcess().radius;


        ovalPaint.setStyle(Paint.Style.STROKE);
        ovalPaint.setAntiAlias(true);
        ovalPaint.setColor(roundIndicatorBean.getProcess().bgColor);
        ovalPaint.setStrokeWidth(roundIndicatorBean.getProcess().strokeWidth);

        canvas.drawArc(ovalRect, roundIndicatorBean.getProcess().startAngle, roundIndicatorBean.getProcess().sweepAngle, false, ovalPaint);
    }

    private void drawPointer(Canvas canvas) {

        // 绘制这个三角形,你可以绘制任意多边形
        float left = roundIndicatorBean.getPanel()[0] / 2 - roundIndicatorBean.getProcess().radius - roundIndicatorBean.getProcess().strokeWidth - roundIndicatorBean.getPointerLen()/2;
        float top = roundIndicatorBean.getPanel()[0] / 2 - roundIndicatorBean.getPointerLen() / 2;
        float bottom = top + roundIndicatorBean.getPointerLen();
        float right = left + roundIndicatorBean.getPointerLen() ;
        float off=left/3;
        pointerPath.moveTo(left-off, top);// 此点为多边形的起点
        pointerPath.lineTo(left-off, bottom);
        pointerPath.lineTo(right-off, bottom - roundIndicatorBean.getPointerLen()/2);
        pointerPath.close(); // 使这些点构成封闭的多边形


//        paint.setStyle(Paint.Style.STROKE);
        pointerPaint.setAntiAlias(true);
        pointerPaint.setColor(roundIndicatorBean.getProcess().bgColor);
        pointerPaint.setStrokeWidth(roundIndicatorBean.getPointerLen());

        canvas.save(Canvas.MATRIX_SAVE_FLAG); //Saving the canvas and later restoring it so only this image will be rotated.
        canvas.rotate(roundIndicatorBean.getProcess().sweepAngle, roundIndicatorBean.getPanel()[0] / 2, roundIndicatorBean.getPanel()[0] / 2);
        canvas.drawPath(pointerPath, pointerPaint);
        canvas.restore();
    }

    private void drawScale(Canvas canvas) {

        scalePaint.setStyle(Paint.Style.STROKE);
        scalePaint.setAntiAlias(true);
        scalePaint.setColor(roundIndicatorBean.getProcess().bgColor);
        scalePaint.setStrokeWidth(roundIndicatorBean.getScale().strokeWidth);

        //RectF对象
        scaleRect.left = roundIndicatorBean.getPanel()[0]/2 - roundIndicatorBean.getScale().radius;                              //左边
        scaleRect.top = roundIndicatorBean.getPanel()[0]/2 - roundIndicatorBean.getScale().radius ;                                   //上边
        scaleRect.right = roundIndicatorBean.getPanel()[0]/2 + roundIndicatorBean.getScale().radius;                             //右边
        scaleRect.bottom = roundIndicatorBean.getPanel()[0]/2 + roundIndicatorBean.getScale().radius ;

        canvas.save(Canvas.MATRIX_SAVE_FLAG); //Saving the canvas and later restoring it so only this image will be rotated.
        canvas.rotate(roundIndicatorBean.getScale().off, roundIndicatorBean.getPanel()[0] / 2, roundIndicatorBean.getPanel()[0] / 2);//正数 ，顺时针旋转
        for (int i = 0; i < roundIndicatorBean.getScale().scaleNum; i++) {
            int color = roundIndicatorBean.getScale().getColor(i);
            scalePaint.setColor(color);
            canvas.drawArc(scaleRect, 180 + roundIndicatorBean.getScale().arcAngle/2,- roundIndicatorBean.getScale().arcAngle, false, scalePaint);
            canvas.rotate(roundIndicatorBean.getScale().scaleSize, roundIndicatorBean.getPanel()[0] / 2, roundIndicatorBean.getPanel()[0] / 2);
        }
        canvas.restore();
    }

    private void drawFont(Canvas canvas) {

//        typePaint.setStrokeWidth(3);
        typePaint.setTextSize(roundIndicatorBean.getType().fontSize);
        typePaint.setColor(roundIndicatorBean.getType().fontColor);
        typePaint.setTextAlign(Paint.Align.LEFT);
//        typePaint.setTypeface(Typeface.DEFAULT_BOLD);
        typePaint.getTextBounds(roundIndicatorBean.getType().text, 0, roundIndicatorBean.getType().text.length(), typeRect);
        canvas.drawText(roundIndicatorBean.getType().text, roundIndicatorBean.getPanel()[0] / 2 - typeRect.width() / 2, roundIndicatorBean.getPanel()[0] / 2, typePaint);


        valuePaint.setTextSize(roundIndicatorBean.getValue().fontSize);
        valuePaint.setColor(roundIndicatorBean.getValue().fontColor);
        valuePaint.setTextAlign(Paint.Align.LEFT);
        valuePaint.setTypeface(Typeface.DEFAULT_BOLD);

        valuePaint.getTextBounds(roundIndicatorBean.getValue().text, 0, roundIndicatorBean.getValue().text.length(), valueRect);
        //数值
        canvas.drawText(roundIndicatorBean.getValue().text, roundIndicatorBean.getPanel()[0] / 2 - valueRect.width() / 2, roundIndicatorBean.getPanel()[0] / 2 - dp2px(6) - typeRect.height(), valuePaint);
    }

    public void setAnimationRadio(String ratio) {
        float value = 0;
        int rate = 0;
        if (ratio != null || !ratio.equals("") || ratio.contains("%")) {
            rate = (int) Float.parseFloat(ratio.replace("%", ""));//周回报率 都是整数
            value = (rate / 100f);
        }
        final float valueCell = (value > 2.8 ? 2.8f : value) / 1500;
        ValueAnimator mAnimator = ValueAnimator.ofFloat(0, valueCell);
        final float finalValue = value;
        final int finalRate = rate;
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float animatedValue = (Float) valueAnimator.getAnimatedValue();
                if (valueCell == animatedValue)
                    setRadio(finalRate + "%");
                else
                    setRadio((animatedValue * 500) + "%");
            }
        });
        mAnimator.setDuration(1500);//分为的片段书
        //5.为ValueAnimator设置目标对象并开始执行动画
        mAnimator.start();
    }

    /**
     * {@link #setAnimationRadio(String)}
     *
     * @param ratio
     */
    public void setRadio(String ratio) {//回报率为整数，加上百分号
        float value = 0;//回报率除100，转化为浮点型
        int rate = 0;
        if (ratio != null || !ratio.equals("") || ratio.contains("%")) {
            rate = (int) Float.parseFloat(ratio.replace("%", ""));
            value = rate / 100f;
        }
        if (value <= 0) {
            roundIndicatorBean.getProcess().sweepAngle = roundIndicatorBean.getScale().off;

        } else if (value <= 1.6) {//0~160%，每刻度占用20%
            float allAngle = roundIndicatorBean.getScale().scaleSize * 8;
            float allValue = 1.6f;
            roundIndicatorBean.getProcess().sweepAngle = roundIndicatorBean.getScale().off + allAngle / allValue * value;
        } else if (value <= 2.8) {//每40%用一个刻度
            float allAngle = roundIndicatorBean.getScale().scaleSize * 3;
            float allValue = 2.8f - 1.6f;
            float offAngle = roundIndicatorBean.getScale().off + roundIndicatorBean.getScale().scaleSize * 8;
            roundIndicatorBean.getProcess().sweepAngle = offAngle + allAngle / allValue * (value - 1.6f);
        } else {
            roundIndicatorBean.getProcess().sweepAngle = roundIndicatorBean.getScale().off + roundIndicatorBean.getScale().scaleSize * (roundIndicatorBean.getScale().scaleNum-1);
        }
        roundIndicatorBean.getValue().text = (rate) + "%";
        roundIndicatorBean.getScale().maxScale = getOverScale(value);
        Log.i("roundIndicatorBean", value + " " + roundIndicatorBean.getProcess().sweepAngle);
        invalidate();
    }

    public RoundIndicatorBean getRoundIndicatorBean() {
        return roundIndicatorBean;
    }

    public int getOverScale(float currentValue) {//0.2
        float[] scale = {0, 0.2f, 0.4f, 0.6f, 0.8f, 1f, 1.2f, 1.4f, 1.6f, 2.f, 2.4f, 2.8f,2.8f};
        if (currentValue <= 0)
            return 0;
        for (int i = 0; i < scale.length; i++) {
            if (currentValue < scale[i])
                return i-1;
        }
        if (currentValue==2.8f){
            return scale.length-2 ;
        }
        return scale.length-1 ;
    }

/*
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        width = getDefaultSize(UiTool.dpToPx(100), widthMeasureSpec);
//        height = getDefaultSize(UiTool.dpToPx(50), heightMeasureSpec);
        if (width > 2 * height) {
            width = 2 * height;
        } else {
            height = width / 2;
        }
        setMeasuredDimension(width, height);
    }
*/


/*    public static int getDefaultSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            // Mode = UNSPECIFIED,AT_MOST时使用提供的默认大小  
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                // Mode = EXACTLY时使用测量的大小   
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }*/

}
