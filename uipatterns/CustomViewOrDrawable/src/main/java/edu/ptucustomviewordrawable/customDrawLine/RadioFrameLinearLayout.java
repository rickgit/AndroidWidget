package edu.ptucustomviewordrawable.customDrawLine;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.lang.ref.SoftReference;

import edu.ptucustomviewordrawable.DeviceUtils;
import edu.ptucustomviewordrawable.LogUtils;
import edu.ptucustomviewordrawable.R;


/**
 * Created by anshu.wang on 2017/7/3.
 */

public class RadioFrameLinearLayout extends LinearLayout {
    Paint mWPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int childCount;
    //FIXME 注意内存溢出
    private Bitmap bitmap;
    private Bitmap lastBitmap;
    private Bitmap selectBitmap;
    private Bitmap lastSelectBitmap;

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
        mWPaint.setColor(0xffffaacc);
//        mWPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        mWPaint.setStrokeWidth(2);
        mWPaint.setStyle(Paint.Style.STROKE);


    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawSeparateLines(canvas);
    }

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
            for (int i = 0; i < childCount; i++) {// { |█|█|█| } 共两条分割线
                boolean islast = i == childCount - 1;

                if (islast) {
                    lastBitmap = getBitmap(islast);
                    drawSelect(i, canvas, lastBitmap, mWPaint);
                } else {
                    bitmap = getBitmap(islast);
                    drawSelect(i, canvas, bitmap, mWPaint);
                }
            }
            if (select >= 0) {
                boolean islast = select == childCount - 1;
                LogUtils.logMainInfo("islast "+islast);
                if (islast) {
                    lastBitmap = getSelectBitmap(islast);
                    drawSelect(select, canvas, lastBitmap, mWPaint);
                } else {
                    selectBitmap = getSelectBitmap(islast);
                    drawSelect(select, canvas, selectBitmap, mWPaint);
                }
            }

        } else {//TODO　增加垂直方向的　segmentView

        }

    }

    private void drawSelect(int position, Canvas canvas, Bitmap drawable, Paint mWPaint) {
        float childWidth = (this.getWidth() - getPaddingLeft() - getPaddingRight()) / childCount;
        float startX = getPaddingLeft() + childWidth * position;
        float endX = startX + childWidth;

        canvas.drawBitmap(drawable, startX, 0, mWPaint);
    }

    public Bitmap drawableToBitmap(Drawable drawable, float paintStrokeWidth, boolean isLast) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            float childWidth = (this.getWidth() - getPaddingLeft() - getPaddingRight()) / childCount + (isLast ? (-mWPaint.getStrokeWidth()*2 ): mWPaint.getStrokeWidth());//加上drawable strokeWidth

            int height = (int) (getHeight() - getPaddingTop() - getPaddingBottom());

            bitmap = Bitmap.createBitmap((int) childWidth, height, Bitmap.Config.ARGB_8888);// Single color bitmap will be created of 1x1 pixel
        } else {
            float childWidth = (this.getWidth() - getPaddingLeft() - getPaddingRight() - paintStrokeWidth) / childCount;

            int height = getHeight() - getPaddingTop() - getPaddingBottom();
            LogUtils.logMainInfo("width " + childWidth + ": height " + height);
            bitmap = Bitmap.createBitmap((int) childWidth, height, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 必须在onDraw初始化，才能获取到宽高
     *
     * @return
     */
    public Bitmap getBitmap(boolean islast) {
        if (bitmap == null) {
            bitmap = drawableToBitmap(getResources().getDrawable(R.drawable.rect_center), mWPaint.getStrokeWidth(), islast);
//            bitmap = new SoftReference<Bitmap>(drawableToBitmap(getResources().getDrawable(R.drawable.rect_center), mWPaint.getStrokeWidth()));
            LogUtils.logMainInfo("创建新对象");
        }
        return bitmap;
    }

    /**
     * 必须在onDraw初始化，才能获取到宽高
     *
     * @return
     */
    public Bitmap getSelectBitmap(boolean islast) {
        if (selectBitmap == null) {
            selectBitmap = drawableToBitmap(getResources().getDrawable(R.drawable.rect_center_red), mWPaint.getStrokeWidth(), islast);
//            selectBitmap = new SoftReference<Bitmap>(drawableToBitmap(getResources().getDrawable(R.drawable.rect_center_red), mWPaint.getStrokeWidth()));
            LogUtils.logMainInfo("创建新对象");
        }

        return selectBitmap;
    }

    public void setSelect(int select) {
        this.select = select;
    }

    public void unSelect() {
        this.select = -1;
    }

}
