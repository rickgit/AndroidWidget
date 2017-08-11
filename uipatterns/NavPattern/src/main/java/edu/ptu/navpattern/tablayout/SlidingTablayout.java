package edu.ptu.navpattern.tablayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import edu.ptu.navpattern.LogUtils;
import edu.ptu.navpattern.R;

/**
 * Created by rick.wang on 2017/8/3.
 */

public class SlidingTablayout extends HorizontalScrollView {

    private LinearLayout tabsContainer;

    public SlidingTablayout(Context context) {
        super(context);
        initView(context);
    }

    public SlidingTablayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SlidingTablayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SlidingTablayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public void initView(Context context) {
        addView(tabsContainer = new LinearLayout(context));
    }

    public LinearLayout getTabsContainer() {
        return tabsContainer;
    }

    public void updateTabStyles(int position) {
        int mTabCount = tabsContainer.getChildCount();
        for (int i = 0; i < mTabCount; i++) {
            View v = tabsContainer.getChildAt(i);
//            v.setPadding((int) mTabPadding, v.getPaddingTop(), (int) mTabPadding, v.getPaddingBottom());
            TextView tv_tab_title = (TextView) v.findViewById(R.id.tv_tab_title);
            if (tv_tab_title != null) {

            }
        }
    }

    private int mCurrentTab;
    private float mCurrentPositionOffset;
    /**
     * 用于实现滚动居中
     */
    private Rect mTabRect = new Rect();
    /**
     * 用于绘制显示器
     */
    private Rect mIndicatorRect = new Rect();
    private int mLastScrollX;


    public void setmCurrentPositionOffset(float mCurrentPositionOffset) {
        this.mCurrentPositionOffset = mCurrentPositionOffset;
    }

    public void setmCurrentTab(int mCurrentTab) {
        this.mCurrentTab = mCurrentTab;
    }

    /**
     * HorizontalScrollView滚到当前tab,并且居中显示
     */
    public void scrollToCurrentTab() {
        int mTabCount = tabsContainer.getChildCount();
        if (mTabCount <= 0) {
            return;
        }

        int offset = (int) (mCurrentPositionOffset * tabsContainer.getChildAt(mCurrentTab).getWidth());
        /**当前Tab的left+当前Tab的Width乘以positionOffset*/
        int newScrollX = tabsContainer.getChildAt(mCurrentTab).getLeft() + offset;

        if (mCurrentTab > 0 || offset > 0) {
            /**HorizontalScrollView移动到当前tab,并居中*/
            newScrollX -= getWidth() / 2 - getPaddingLeft();
            calcIndicatorRect();
            newScrollX += ((mTabRect.right - mTabRect.left) / 2);
        }

        if (newScrollX != mLastScrollX) {
            mLastScrollX = newScrollX;
            /** scrollTo（int x,int y）:x,y代表的不是坐标点,而是偏移量
             *  x:表示离起始位置的x水平方向的偏移量
             *  y:表示离起始位置的y垂直方向的偏移量
             */
            scrollTo(newScrollX, 0);
        }
    }

    private void calcIndicatorRect() {
        View currentTabView = tabsContainer.getChildAt(this.mCurrentTab);
        float left = currentTabView.getLeft();
        float right = currentTabView.getRight();

        //for mIndicatorWidthEqualTitle
//        if (mIndicatorStyle == STYLE_NORMAL && mIndicatorWidthEqualTitle) {
//            TextView tab_title = (TextView) currentTabView.findViewById(R.id.tv_tab_title);
//            mTextPaint.setTextSize(mTextsize);
//            float textWidth = mTextPaint.measureText(tab_title.getText().toString());
//            margin = (right - left - textWidth) / 2;
//        }

        if (this.mCurrentTab < tabsContainer.getChildCount() - 1) {
            View nextTabView = tabsContainer.getChildAt(this.mCurrentTab + 1);
            float nextTabLeft = nextTabView.getLeft();
            float nextTabRight = nextTabView.getRight();

            left = left + mCurrentPositionOffset * (nextTabLeft - left);
            right = right + mCurrentPositionOffset * (nextTabRight - right);

            //for mIndicatorWidthEqualTitle
//            if (mIndicatorStyle == STYLE_NORMAL && mIndicatorWidthEqualTitle) {
//                TextView next_tab_title = (TextView) nextTabView.findViewById(R.id.tv_tab_title);
//                mTextPaint.setTextSize(mTextsize);
//                float nextTextWidth = mTextPaint.measureText(next_tab_title.getText().toString());
//                float nextMargin = (nextTabRight - nextTabLeft - nextTextWidth) / 2;
//                margin = margin + mCurrentPositionOffset * (nextMargin - margin);
//            }
        }

        mIndicatorRect.left = (int) left;
        mIndicatorRect.right = (int) right;
        //for mIndicatorWidthEqualTitle
//        if (mIndicatorStyle == STYLE_NORMAL && mIndicatorWidthEqualTitle) {
//            mIndicatorRect.left = (int) (left + margin - 1);
//            mIndicatorRect.right = (int) (right - margin - 1);
//        }

        mTabRect.left = (int) left;
        mTabRect.right = (int) right;

        if (mIndicatorWidth < 0) {   //indicatorWidth小于0时,原jpardogo's PagerSlidingTabStrip

        } else {//indicatorWidth大于0时,圆角矩形以及三角形
            float indicatorLeft = currentTabView.getLeft() + (currentTabView.getWidth() - mIndicatorWidth) / 2;

            if (this.mCurrentTab < tabsContainer.getChildCount() - 1) {
                View nextTab = tabsContainer.getChildAt(this.mCurrentTab + 1);
                indicatorLeft = indicatorLeft + mCurrentPositionOffset * (currentTabView.getWidth() / 2 + nextTab.getWidth() / 2);
            }

            mIndicatorRect.left = (int) indicatorLeft;
            mIndicatorRect.right = (int) (mIndicatorRect.left + mIndicatorWidth);
        }
        LogUtils.logMainInfo(" ");
    }

    /**
     * underline
     */
//    private int mUnderlineColor = 0xffcc11aa;
//    private float mUnderlineHeight = 8;
//    private Paint mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private GradientDrawable mIndicatorDrawable = new GradientDrawable();
    private float mIndicatorCornerRadius=1;
    private int mIndicatorColor=0xffaacc11;
    private float mIndicatorHeight=5;
    private float mIndicatorWidth=20;
    private float mIndicatorMarginLeft=5;
    private float mIndicatorMarginTop=5;
    private float mIndicatorMarginRight=5;
    private float mIndicatorMarginBottom=5;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode() || tabsContainer.getChildCount() <= 0) {
            return;
        }

        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        // draw divider
//        if (mDividerWidth > 0) {
//            mDividerPaint.setStrokeWidth(mDividerWidth);
//            mDividerPaint.setColor(mDividerColor);
//            for (int i = 0; i < mTabCount - 1; i++) {
//                View tab = mTabsContainer.getChildAt(i);
//                canvas.drawLine(paddingLeft + tab.getRight(), mDividerPadding, paddingLeft + tab.getRight(), height - mDividerPadding, mDividerPaint);
//            }
//        }

        // draw underline
//        if (mUnderlineHeight > 0) {
//            mRectPaint.setColor(mUnderlineColor);
//            canvas.drawRect(paddingLeft, height - mUnderlineHeight, tabsContainer.getWidth() + paddingLeft, height, mRectPaint);
//        }

        //draw indicator line

        calcIndicatorRect();

        if (mIndicatorHeight > 0) {
            mIndicatorDrawable.setColor(mIndicatorColor);
            mIndicatorDrawable.setBounds(paddingLeft + (int) mIndicatorMarginLeft + mIndicatorRect.left,
                    height - (int) mIndicatorHeight - (int) mIndicatorMarginBottom,
                    paddingLeft + mIndicatorRect.right - (int) mIndicatorMarginRight,
                    height - (int) mIndicatorMarginBottom);
            LogUtils.logMainInfo((paddingLeft + (int) mIndicatorMarginLeft + mIndicatorRect.left)+" :"
                            +" :"+( height - (int) mIndicatorHeight - (int) mIndicatorMarginBottom)+" :"+
                    ( paddingLeft + mIndicatorRect.right - (int) mIndicatorMarginRight)+" :"+(
                    height - (int) mIndicatorMarginBottom));

            mIndicatorDrawable.setCornerRadius(mIndicatorCornerRadius);
            mIndicatorDrawable.draw(canvas);
        }
    }
}
