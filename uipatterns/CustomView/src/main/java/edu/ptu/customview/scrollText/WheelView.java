package edu.ptu.customview.scrollText;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import edu.ptu.customview.R;
import edu.ptu.customview.scrollText.adapter.WheelAdapter;


/**
 * 上下滚动文本动画
 *
 * @author anshu.wang
 * @version 1.0
 * @time 2017-11-15 10:30:23
 */
public class WheelView extends View {
    Context context;

    Paint paintOuterText;
    Paint paintCenterText;
    Paint paintIndicator;

    WheelAdapter adapter;

    private String label;// 附加单位
    int textSize;
    int maxTextWidth;
    int maxTextHeight;
    float itemHeight;// 每行高度

    int textColorOut;
    int textColorCenter;
    int dividerColor;

    // 条目间距倍数
    static final float lineSpacingMultiplier = 1.4F;
    boolean isLoop;

    // 第一条线Y坐标值
    float firstLineY;
    // 第二条线Y坐标
    float secondLineY;
    // 中间Y坐标
    float centerY;

    // 滚动总高度y值
    int totalScrollY;
    // 初始化默认选中第几个
    int initPosition;
    // 选中的Item是第几个
    private int selectedItem;
    int preCurrentIndex;
    // 滚动偏移值,用于记录滚动了多少个item
    int change;

    // 显示几个条目
    int itemsVisible = 3;

    int measuredHeight;
    int measuredWidth;

    // 半圆周长
    int halfCircumference;
    // 半径
    int radius;

    int widthMeasureSpec;

    private int mGravity = Gravity.CENTER;
    private int drawCenterContentStart = 0;// 中间选中文字开始绘制位置
    private int drawOutContentStart = 0;// 非中间文字开始绘制位置
    private static final float SCALECONTENT = 0.8F;// 非中间文字则用此控制高度，压扁形成3d错觉
    private static final float CENTERCONTENTOFFSET = 0;// 中间文字文字居中需要此偏移值
    private static final String GETPICKERVIEWTEXT = "getName";// 反射的方法名

    public WheelView(Context context) {
        this(context, null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        textColorOut = getResources().getColor(R.color.pickerview_wheelview_textcolor_out);
        textColorCenter = getResources().getColor(R.color.pickerview_wheelview_textcolor_center);
        dividerColor = getResources().getColor(R.color.pickerview_wheelview_textcolor_divider);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.wheelview, 0, 0);
            mGravity = a.getInt(R.styleable.wheelview_gravity, Gravity.CENTER);
            textColorOut = a.getColor(R.styleable.wheelview_textColorOut, textColorOut);
            textColorCenter = a.getColor(R.styleable.wheelview_textColorCenter, textColorCenter);
            dividerColor = a.getColor(R.styleable.wheelview_dividerColor, dividerColor);
        }
        initLoopView(context);
    }

    private void initLoopView(Context context) {
        this.context = context;

        isLoop = true;
        textSize = 0;

        totalScrollY = 0;
        initPosition = -1;

        initPaints();

        setTextSize(16F);
    }

    private void initPaints() {
        paintOuterText = new Paint();
        paintOuterText.setColor(textColorOut);
        paintOuterText.setAntiAlias(true);
        paintOuterText.setTypeface(Typeface.MONOSPACE);
        paintOuterText.setTextSize(textSize);

        paintCenterText = new Paint();
        paintCenterText.setColor(textColorCenter);
        paintCenterText.setAntiAlias(true);
//		paintCenterText.setTextScaleX(1.1F);
//		paintCenterText.setTypeface(Typeface.MONOSPACE);
        paintCenterText.setTextSize(textSize);

        paintIndicator = new Paint();
        paintIndicator.setColor(dividerColor);
        paintIndicator.setAntiAlias(true);

        if (android.os.Build.VERSION.SDK_INT >= 11) {
            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }
    }

    private void remeasure() {
        if (adapter == null) {
            return;
        }

        measureTextWidthHeight();

        // 最大Text的高度乘间距倍数得到 可见文字实际的总高度，半圆的周长
        halfCircumference = (int) (itemHeight * (itemsVisible - 1));
        // 整个圆的周长除以PI得到直径，这个直径用作控件的总高度
        measuredHeight = (int) ((halfCircumference * 2) / Math.PI);
        // 求出半径
        radius = (int) (halfCircumference / Math.PI);
        // 控件宽度，这里支持weight
        measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        // 计算两条横线和控件中间点的Y位置
        firstLineY = (measuredHeight - itemHeight) / 2.0F;
        secondLineY = (measuredHeight + itemHeight) / 2.0F;
        centerY = (measuredHeight + maxTextHeight) / 2.0F - CENTERCONTENTOFFSET;
        // 初始化显示的item的position，根据是否loop
        if (initPosition == -1) {
            if (isLoop) {
                initPosition = (adapter.getItemsCount() + 1) / 2;
            } else {
                initPosition = 0;
            }
        }

        preCurrentIndex = initPosition;
    }

    /**
     * 计算最大len的Text的宽高度
     */
    private void measureTextWidthHeight() {
        Rect rect = new Rect();
        for (int i = 0; i < adapter.getItemsCount(); i++) {
            String s1 = getContentText(adapter.getItem(i));
            paintCenterText.getTextBounds(s1, 0, s1.length(), rect);
            int textWidth = rect.width();
            if (textWidth > maxTextWidth) {
                maxTextWidth = textWidth;
            }
            paintCenterText.getTextBounds("\u661F\u671F", 0, 2, rect); // 星期
            int textHeight = rect.height();
            if (textHeight > maxTextHeight) {
                maxTextHeight = textHeight;
            }
        }
        itemHeight = lineSpacingMultiplier * maxTextHeight;
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public final void setCyclic(boolean cyclic) {
        isLoop = cyclic;
    }

    public final void setTextSize(float size) {
        if (size > 0.0F) {
            textSize = (int) (context.getResources().getDisplayMetrics().density * size);
            paintOuterText.setTextSize(textSize);
            paintCenterText.setTextSize(textSize);
        }
    }

    public final void setCurrentItem(int currentItem) {
        this.initPosition = currentItem;
        totalScrollY = 0;// 回归顶部，不然重设setCurrentItem的话位置会偏移的，就会显示出不对位置的数据
        invalidate();
    }


    public final void setAdapter(WheelAdapter adapter) {
        this.adapter = adapter;
        remeasure();
        invalidate();
    }

    public final WheelAdapter getAdapter() {
        return adapter;
    }

    public final int getCurrentItem() {
        return selectedItem;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        if (adapter == null) {
            return;
        }
        // 可见的item数组
        Object visibles[] = new Object[itemsVisible];
        // 滚动的Y值高度除去每行Item的高度，得到滚动了多少个item，即change数
        change = (int) (totalScrollY / itemHeight);
        try {
            // 滚动中实际的预选中的item(即经过了中间位置的item) ＝ 滑动前的位置 ＋ 滑动相对位置
            preCurrentIndex = initPosition + change % adapter.getItemsCount();
        } catch (ArithmeticException e) {
            System.out.println("出错了！adapter.getItemsCount() == 0，联动数据不匹配");
        }
        if (!isLoop) {// 不循环的情况
            if (preCurrentIndex < 0) {
                preCurrentIndex = 0;
            }
            if (preCurrentIndex > adapter.getItemsCount() - 1) {
                preCurrentIndex = adapter.getItemsCount() - 1;
            }
        } else {// 循环
            if (preCurrentIndex < 0) {// 举个例子：如果总数是5，preCurrentIndex ＝
                // －1，那么preCurrentIndex按循环来说，其实是0的上面，也就是4的位置
                preCurrentIndex = adapter.getItemsCount() + preCurrentIndex;
            }
            if (preCurrentIndex > adapter.getItemsCount() - 1) {// 同理上面,自己脑补一下
                preCurrentIndex = preCurrentIndex - adapter.getItemsCount();
            }
        }

        // 跟滚动流畅度有关，总滑动距离与每个item高度取余，即并不是一格格的滚动，每个item不一定滚到对应Rect里的，这个item对应格子的偏移值
        int itemHeightOffset = (int) (totalScrollY % itemHeight);
        // 设置数组中每个元素的值
        int counter = 0;
        while (counter < itemsVisible) {
            int index = preCurrentIndex - (itemsVisible / 2 - counter);// 索引值，即当前在控件中间的item看作数据源的中间，计算出相对源数据源的index值

            // 判断是否循环，如果是循环数据源也使用相对循环的position获取对应的item值，如果不是循环则超出数据源范围使用""空白字符串填充，在界面上形成空白无数据的item项
            if (isLoop) {
                if (index < 0) {
                    index = index + adapter.getItemsCount();
                    if (index < 0) {
                        index = 0;
                    }
                }
                if (index > adapter.getItemsCount() - 1) {
                    index = index - adapter.getItemsCount();
                    if (index > adapter.getItemsCount() - 1) {
                        index = adapter.getItemsCount() - 1;
                    }
                }
                visibles[counter] = adapter.getItem(index);
            } else if (index < 0) {
                visibles[counter] = "";
            } else if (index > adapter.getItemsCount() - 1) {
                visibles[counter] = "";
            } else {
                visibles[counter] = adapter.getItem(index);
            }
            counter++;

        }
        Drawable background = getBackground();
        if (background!=null){
            System.out.println("显示内容");
        }
        // 中间两条横线
//        canvas.drawLine(0.0F, firstLineY, measuredWidth, firstLineY, paintIndicator);
//        canvas.drawLine(0.0F, secondLineY, measuredWidth, secondLineY, paintIndicator);
        // 单位的Label
        if (label != null) {
            int drawRightContentStart = measuredWidth - getTextWidth(paintCenterText, label);
            // 靠右并留出空隙
            canvas.drawText(label, drawRightContentStart - CENTERCONTENTOFFSET, centerY, paintCenterText);
        }
        counter = 0;
        while (counter < itemsVisible) {
            canvas.save();
            // L(弧长)=α（弧度）* r(半径) （弧度制）
            // 求弧度--> (L * π ) / (π * r) (弧长X派/半圆周长)
            float itemHeight = maxTextHeight * lineSpacingMultiplier;
            double radian = ((itemHeight * counter - itemHeightOffset) * Math.PI) / halfCircumference;
            // 弧度转换成角度(把半圆以Y轴为轴心向右转90度，使其处于第一象限及第四象限
            float angle = (float) (90D - (radian / Math.PI) * 180D);
            // 九十度以上的不绘制
            if (angle >= 90F || angle <= -90F) {
                canvas.restore();
            } else {
                String contentText = getContentText(visibles[counter]);

                // 计算开始绘制的位置
                measuredCenterContentStart(contentText);
                measuredOutContentStart(contentText);
                float translateY = (float) (radius - Math.cos(radian) * radius - (Math.sin(radian) * maxTextHeight) / 2D);
                // 根据Math.sin(radian)来更改canvas坐标系原点，然后缩放画布，使得文字高度进行缩放，形成弧形3d视觉差
                canvas.translate(0.0F, translateY);
                canvas.scale(1.0F, (float) Math.sin(radian));

                int alpha = (int) ((1 - Math.abs(angle) / 90) * 255);
                paintOuterText.setAlpha(alpha);

                if (translateY <= firstLineY && maxTextHeight + translateY >= firstLineY) {
                    // 条目经过第一条线
                    canvas.save();
                    canvas.clipRect(0, 0, measuredWidth, firstLineY - translateY);
                    canvas.scale(1.0F, (float) Math.sin(radian) * SCALECONTENT);
                    canvas.drawText(contentText, drawOutContentStart, maxTextHeight, paintOuterText);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0, firstLineY - translateY, measuredWidth, (int) (itemHeight));
                    canvas.scale(1.0F, (float) Math.sin(radian) * 1F);
                    canvas.drawText(contentText, drawCenterContentStart, maxTextHeight - CENTERCONTENTOFFSET, paintCenterText);
                    canvas.restore();
                } else if (translateY <= secondLineY && maxTextHeight + translateY >= secondLineY) {
                    // 条目经过第二条线
                    canvas.save();
                    canvas.clipRect(0, 0, measuredWidth, secondLineY - translateY);
                    canvas.scale(1.0F, (float) Math.sin(radian) * 1.0F);
                    canvas.drawText(contentText, drawCenterContentStart, maxTextHeight - CENTERCONTENTOFFSET, paintCenterText);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0, secondLineY - translateY, measuredWidth, (int) (itemHeight));
                    canvas.scale(1.0F, (float) Math.sin(radian) * SCALECONTENT);
                    canvas.drawText(contentText, drawOutContentStart, maxTextHeight, paintOuterText);
                    canvas.restore();
                } else if (translateY >= firstLineY && maxTextHeight + translateY <= secondLineY) {//切换中间文字的位置
                    // 中间条目
                    canvas.clipRect(0, 0, measuredWidth, (int) (itemHeight));
                    canvas.drawText(contentText, drawCenterContentStart, maxTextHeight - CENTERCONTENTOFFSET, paintCenterText);
                    int preSelectedItem = adapter.indexOf(visibles[counter]);
                    if (preSelectedItem != -1) {
                        selectedItem = preSelectedItem;
                    }
                } else {
                    // 其他条目
                    canvas.save();
                    canvas.clipRect(0, 0, measuredWidth, (int) (itemHeight));
                    canvas.scale(1.0F, (float) Math.sin(radian) * SCALECONTENT);
                    canvas.drawText(contentText, drawOutContentStart, maxTextHeight, paintOuterText);
                    canvas.restore();
                }
                canvas.restore();

            }
            counter++;
        }
    }

    /**
     * 根据传进来的对象反射出getPickerViewText()方法，来获取需要显示的值
     *
     * @param item
     * @return
     */
    private String getContentText(Object item) {
        String contentText = item.toString();
        try {
            Class<?> clz = item.getClass();
            Method m = clz.getMethod(GETPICKERVIEWTEXT);
            contentText = m.invoke(item, new Object[0]).toString();
        } catch (NoSuchMethodException e) {
        } catch (InvocationTargetException e) {
        } catch (IllegalAccessException e) {
        } catch (Exception e) {
        }
        return contentText;
    }

    private void measuredCenterContentStart(String content) {
        Rect rect = new Rect();
        paintCenterText.getTextBounds(content, 0, content.length(), rect);
        switch (mGravity) {
            case Gravity.CENTER:
                drawCenterContentStart = (int) ((measuredWidth - rect.width()) * 0.5);
                break;
            case Gravity.LEFT:
                drawCenterContentStart = 0;
                break;
            case Gravity.RIGHT:
                drawCenterContentStart = measuredWidth - rect.width();
                break;
        }
    }

    private void measuredOutContentStart(String content) {
        Rect rect = new Rect();
        paintOuterText.getTextBounds(content, 0, content.length(), rect);
        switch (mGravity) {
            case Gravity.CENTER:
                drawOutContentStart = (int) ((measuredWidth - rect.width()) * 0.5);
                break;
            case Gravity.LEFT:
                drawOutContentStart = 0;
                break;
            case Gravity.RIGHT:
                drawOutContentStart = measuredWidth - rect.width();
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.widthMeasureSpec = widthMeasureSpec;
        remeasure();
        int max = Math.max(measuredWidth, measuredHeight);
        setMeasuredDimension(max,max);
        System.out.println("打印宽和高："+measuredWidth+" : "+measuredHeight);
    }

    /**
     * 获取Item个数
     *
     * @return
     */
    public int getItemsCount() {
        return adapter != null ? adapter.getItemsCount() : 0;
    }

    /**
     * 附加在右边的单位字符串
     *
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    public void setGravity(int gravity) {
        this.mGravity = gravity;
    }

    public int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    public void setTotalScrollY(int totalScrollY) {
        this.totalScrollY = totalScrollY;
    }

    public int getTotalScrollY() {
        return totalScrollY;
    }

    public float getItemHeight() {
        return itemHeight;
    }

    public void setTextPaintColor(int textPaintColor) {
       textColorCenter=textPaintColor;
       textColorOut=textPaintColor;
       paintCenterText.setColor(textPaintColor);
       paintOuterText.setColor(textPaintColor);
       invalidate();
    }
}