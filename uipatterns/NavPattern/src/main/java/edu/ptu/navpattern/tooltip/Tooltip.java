/*
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2016. Viнt@rь
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package edu.ptu.navpattern.tooltip;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.content.res.ResourcesCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import edu.ptu.navpattern.R;

/**
 * Tooltip
 */
public final class Tooltip {

    private final boolean isCancelable;
    private final boolean isDismissOnClick;

    private final int mGravity;

    private final float mMargin;

    private final View mAnchorView;
    private final PopupWindow mPopupWindow;

    private OnDismissListener mOnDismissListener;

    private LinearLayout mContentView;
    private ImageView mArrowView;

    private Tooltip(Builder builder) {
        isCancelable = builder.isCancelable;
        isDismissOnClick = builder.isDismissOnClick;

        mGravity = builder.mGravity;
        mMargin = builder.mMargin;
        mAnchorView = builder.mAnchorView;
        mOnDismissListener = builder.mOnDismissListener;

        mPopupWindow = new PopupWindow(builder.mContext);
        mPopupWindow.setBackgroundDrawable(null);
        mPopupWindow.setClippingEnabled(false);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setContentView(getContentView(builder));
        mPopupWindow.setOutsideTouchable(builder.isCancelable);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mAnchorView.removeOnAttachStateChangeListener(mOnAttachStateChangeListener);

                if (mOnDismissListener != null) {
                    mOnDismissListener.onDismiss();
                }
            }
        });
    }

    private View getContentView(final Builder builder) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(builder.mBackgroundColor);
        drawable.setCornerRadius(builder.mCornerRadius);
        LinearLayout vgContent = new LinearLayout(builder.mContext);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            vgContent.setBackground(drawable);
        } else {
            //noinspection deprecation
            vgContent.setBackgroundDrawable(drawable);
        }
        int padding = (int) builder.mPadding;
        vgContent.setPadding(padding, padding, padding, padding);
        vgContent.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        textViewParams.gravity = Gravity.CENTER;
        textViewParams.topMargin = 1;
        vgContent.setLayoutParams(textViewParams);
        vgContent.setDividerDrawable(vgContent.getResources().getDrawable(R.drawable.divider_line));
        vgContent.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);

        if (builder.itemText != null && builder.itemText.length > 0) {
            for (int i = 0; i < builder.itemText.length; i++) {

                TextView textView = new TextView(builder.mContext);
                textView.setText(builder.itemText[i]);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,44);
                textView.setTextColor(0xffffffff);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                if (builder.itemLogo != null && builder.itemLogo.length > i) {
                    Drawable drawableLeft = builder.mContext.getResources().getDrawable(builder.itemLogo[i]);
/// 这一步必须要做,否则不会显示.
//                    drawableLeft.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                    textView.setCompoundDrawables(drawableLeft, null, null, null);
//                    textView.setCompoundDrawablePadding(4);
//                    textView.setBackgroundDrawable(drawableLeft);
                    LinearLayout linearLayout = new LinearLayout(builder.mContext);
                    linearLayout.setMinimumHeight((int) dpToPx(44f));
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout.setGravity(Gravity.CENTER_VERTICAL);
                    ImageView icon = new ImageView(builder.mContext);
                    icon.setImageDrawable(drawableLeft);
                    linearLayout.addView(icon);
                    linearLayout.addView(textView);
                    vgContent.addView(linearLayout);
                    final int position = i;
                    linearLayout.setClickable(false);
                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (builder.mOnItemClickListener != null) {
                                builder.mOnItemClickListener.onClick(position);
                            }
                            mTouchListener.onTouch(v,MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, v.getLeft()+5, v.getTop()+5, 0));
                        }
                    });
                } else {
                    vgContent.addView(textView);
                    final int position = i;
                    textView.setClickable(false);
                    textView.setMinimumHeight((int) dpToPx(44f));
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (builder.mOnItemClickListener != null) {
                                builder.mOnItemClickListener.onClick(position);
                            }
                            mTouchListener.onTouch(v,null);
                        }
                    });
                }
            }

        }

        mArrowView = new ImageView(builder.mContext);
        mArrowView.setImageDrawable(builder.mArrowDrawable);

        LinearLayout.LayoutParams arrowLayoutParams;
        if (mGravity == Gravity.TOP || mGravity == Gravity.BOTTOM) {
            arrowLayoutParams = new LinearLayout.LayoutParams((int) builder.mArrowWidth, (int) builder.mArrowHeight, 0);
        } else {
            arrowLayoutParams = new LinearLayout.LayoutParams((int) builder.mArrowHeight, (int) builder.mArrowWidth, 0);
        }
        arrowLayoutParams.gravity = Gravity.CENTER;
        mArrowView.setLayoutParams(arrowLayoutParams);

        mContentView = new LinearLayout(builder.mContext);
        mContentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mContentView.setOrientation(mGravity == Gravity.START || mGravity == Gravity.END ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL);

        padding = (int) dpToPx(5);

        switch (mGravity) {
            case Gravity.START:
                mContentView.setPadding(0, 0, padding, 0);
                break;
            case Gravity.TOP:
            case Gravity.BOTTOM:
                mContentView.setPadding(padding, 0, padding, 0);
                break;
            case Gravity.END:
                mContentView.setPadding(padding, 0, 0, 0);
                break;
        }

        if (mGravity == Gravity.TOP || mGravity == Gravity.START) {
            mContentView.addView(vgContent);
            mContentView.addView(mArrowView);
        } else {
            mContentView.addView(mArrowView);
            mContentView.addView(vgContent);
        }

        if (builder.isCancelable || builder.isDismissOnClick) {
            mContentView.setOnTouchListener(mTouchListener);
        }
        return mContentView;
    }

    /**
     * <p>Indicate whether this Tooltip is showing on screen.</p>
     *
     * @return true if the Tooltip is showing, false otherwise
     */
    public boolean isShowing() {
        return mPopupWindow.isShowing();
    }

    /**
     * Display the Tooltip anchored to the custom gravity of the anchor view.
     *
     * @see #dismiss()
     */
    public void show() {
        if (!isShowing()) {
            mContentView.getViewTreeObserver().addOnGlobalLayoutListener(mLocationLayoutListener);

            mAnchorView.addOnAttachStateChangeListener(mOnAttachStateChangeListener);
            mAnchorView.post(new Runnable() {
                @Override
                public void run() {
                    mPopupWindow.showAsDropDown(mAnchorView);
                }
            });
        }
    }

    /**
     * Disposes of the Tooltip. This method can be invoked only after
     * {@link #show()} has been executed. Failing
     * that, calling this method will have no effect.
     *
     * @see #show()
     */
    public void dismiss() {
        mPopupWindow.dismiss();
    }

    public void cancel() {

    }


    /**
     * Sets the listener to be called when Tooltip is dismissed.
     *
     * @param listener The listener.
     */
    public void setOnDismissListener(OnDismissListener listener) {
        mOnDismissListener = listener;
    }

    private PointF calculateLocation() {
        PointF location = new PointF();

        final RectF anchorRect = calculateRectInWindow(mAnchorView);
        final PointF anchorCenter = new PointF(anchorRect.centerX(), anchorRect.centerY());

        switch (mGravity) {
            case Gravity.START:
                location.x = anchorRect.left - mContentView.getWidth() - mMargin;
                location.y = anchorCenter.y - mContentView.getHeight() / 2f;
                break;
            case Gravity.END:
                location.x = anchorRect.right + mMargin;
                location.y = anchorCenter.y - mContentView.getHeight() / 2f;
                break;
            case Gravity.TOP:
                location.x = anchorCenter.x - mContentView.getWidth() / 2f;
                location.y = anchorRect.top - mContentView.getHeight() - mMargin;
                break;
            case Gravity.BOTTOM:
                location.x = anchorCenter.x - mContentView.getWidth() / 2f;
                location.y = anchorRect.bottom + mMargin;
                break;
        }

        return location;
    }

    private final View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if ((isCancelable && event.getAction() == MotionEvent.ACTION_OUTSIDE) || (isDismissOnClick && event.getAction() == MotionEvent.ACTION_UP)) {
                dismiss();
                return true;
            }
            return false;
        }
    };

    private final ViewTreeObserver.OnGlobalLayoutListener mLocationLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            removeOnGlobalLayoutListener(mContentView, this);

            mContentView.getViewTreeObserver().addOnGlobalLayoutListener(mArrowLayoutListener);
            PointF location = calculateLocation();
            mPopupWindow.setClippingEnabled(true);
            mPopupWindow.update((int) location.x, (int) location.y, mPopupWindow.getWidth(), mPopupWindow.getHeight());
        }
    };

    private final ViewTreeObserver.OnGlobalLayoutListener mArrowLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            removeOnGlobalLayoutListener(mContentView, this);

            RectF anchorRect = calculateRectOnScreen(mAnchorView);
            RectF contentViewRect = calculateRectOnScreen(mContentView);
            float x, y;
            if (mGravity == Gravity.BOTTOM || mGravity == Gravity.TOP) {
                x = mContentView.getPaddingLeft() + dpToPx(2);
                float centerX = (contentViewRect.width() / 2f) - (mArrowView.getWidth() / 2f);
                float newX = centerX - (contentViewRect.centerX() - anchorRect.centerX());
                if (newX > x) {
                    if (newX + mArrowView.getWidth() + x > contentViewRect.width()) {
                        x = contentViewRect.width() - mArrowView.getWidth() - x;
                    } else {
                        x = newX;
                    }
                }
                y = mArrowView.getTop();
                y = y + (mGravity == Gravity.TOP ? -1 : +1);
            } else {
                y = mContentView.getPaddingTop() + dpToPx(2);
                float centerY = (contentViewRect.height() / 2f) - (mArrowView.getHeight() / 2f);
                float newY = centerY - (contentViewRect.centerY() - anchorRect.centerY());
                if (newY > y) {
                    if (newY + mArrowView.getHeight() + y > contentViewRect.height()) {
                        y = contentViewRect.height() - mArrowView.getHeight() - y;
                    } else {
                        y = newY;
                    }
                }
                x = mArrowView.getLeft();
                x = x + (mGravity == Gravity.START ? -1 : +1);
            }
            mArrowView.setX(x);
            mArrowView.setY(y);
        }
    };

    private final View.OnAttachStateChangeListener mOnAttachStateChangeListener = new View.OnAttachStateChangeListener() {
        @Override
        public void onViewAttachedToWindow(View v) {

        }

        @Override
        public void onViewDetachedFromWindow(View v) {
            dismiss();
        }
    };

    public static final class Builder {
        private boolean isDismissOnClick=true;
        private boolean isCancelable=true;

        private int mGravity=Gravity.BOTTOM;
        private int mBackgroundColor = 0x39302f;

        private float mCornerRadius=dpToPx(3);
        private float mArrowHeight;
        private float mArrowWidth;
        private float mMargin;
        private float mPadding;
        private String[] itemText;
        private
        @DrawableRes
        int[] itemLogo;


        private Drawable mArrowDrawable;
        private Context mContext;
        private View mAnchorView;
        private OnDismissListener mOnDismissListener;
        private OnItemClickListener mOnItemClickListener;

        public Builder(@NonNull MenuItem anchorMenuItem) {
            this(anchorMenuItem, 0);
        }

        public Builder(@NonNull MenuItem anchorMenuItem, @StyleRes int resId) {
            View anchorView = anchorMenuItem.getActionView();
            if (anchorView != null) {
                init(anchorView.getContext(), anchorView, resId);
            } else {
                throw new NullPointerException("anchor menuItem haven`t actionViewClass");
            }
        }

        public Builder(@NonNull View anchorView) {
            this(anchorView, 0);
        }

        public Builder(@NonNull View anchorView, @StyleRes int resId) {
            init(anchorView.getContext(), anchorView, resId);
        }

        private void init(@NonNull Context context, @NonNull View anchorView, @StyleRes int resId) {
            mContext = context;
            mAnchorView = anchorView;
        }

        /**
         * Sets whether Tooltip is cancelable or not. Default is {@code false}.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setCancelable(boolean cancelable) {
            isCancelable = cancelable;
            return this;
        }

        /**
         * Sets whether Tooltip is dismissing on click or not. Default is {@code false}.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setDismissOnClick(boolean isDismissOnClick) {
            this.isDismissOnClick = isDismissOnClick;
            return this;
        }


        /**
         * Sets Tooltip background drawable corner radius.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setCornerRadius(float radius) {
            mCornerRadius = radius;
            return this;
        }

        /**
         * Sets Tooltip arrow height from resource.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setArrowHeight(@DimenRes int resId) {
            return setArrowHeight(mContext.getResources().getDimension(resId));
        }

        /**
         * Sets Tooltip arrow height.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setArrowHeight(float height) {
            mArrowHeight = height;
            return this;
        }

        /**
         * Sets Tooltip arrow width from resource.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setArrowWidth(@DimenRes int resId) {
            return setArrowWidth(mContext.getResources().getDimension(resId));
        }

        /**
         * Sets Tooltip arrow width.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setArrowWidth(float width) {
            mArrowWidth = width;
            return this;
        }

        /**
         * Sets Tooltip arrow drawable from resources.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setArrow(@DrawableRes int resId) {
            return setArrow(ResourcesCompat.getDrawable(mContext.getResources(), resId, null));
        }

        /**
         * Sets Tooltip arrow drawable.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setArrow(Drawable arrowDrawable) {
            mArrowDrawable = arrowDrawable;
            return this;
        }

        /**
         * Sets Tooltip margin from resource.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setMargin(@DimenRes int resId) {
            return setMargin(mContext.getResources().getDimension(resId));
        }

        /**
         * Sets Tooltip margin.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setMargin(float margin) {
            mMargin = margin;
            return this;
        }


        /**
         * Sets Tooltip padding from resource.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setPadding(@DimenRes int resId) {
            return setPadding(mContext.getResources().getDimension(resId));
        }

        /**
         * Sets Tooltip padding.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setPadding(float padding) {
            mPadding = padding;
            return this;
        }

        /**
         * Sets Tooltip gravity.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setGravity(int gravity) {
            mGravity = gravity;
            return this;
        }

        /**
         * Sets listener to be called when the Tooltip is dismissed.
         *
         * @param listener The listener.
         */
        public Builder setOnDismissListener(OnDismissListener listener) {
            mOnDismissListener = listener;
            return this;
        }

        public Builder setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
            return this;
        }

        /**
         * Creates a {@link Tooltip} with the arguments supplied to this builder. It does not
         * {@link Tooltip#show()} the tooltip. This allows the user to do any extra processing
         * before displaying the tooltip. Use {@link #show()} if you don't have any other processing
         * to do and want this to be created and displayed.
         */
        public Tooltip build() {
            if (!Gravity.isHorizontal(mGravity) && !Gravity.isVertical(mGravity)) {
                throw new IllegalArgumentException("Gravity must have be START, END, TOP or BOTTOM.");
            }

            mArrowHeight = dpToPx(7) - 1;
            mArrowWidth = dpToPx(12);
            if (mArrowDrawable == null) {
                mArrowDrawable = new ArrowDrawable(mBackgroundColor, mGravity);
            }
            if (mMargin == -1) {
                mMargin = 0;
            }
            mPadding = dpToPx(5);//5
            return new Tooltip(this);
        }

        /**
         * Builds a {@link Tooltip} with builder attributes and {@link Tooltip#show()}'s the tooltip.
         */
        public Tooltip show() {
            Tooltip tooltip = build();
            tooltip.show();
            return tooltip;
        }


        public Builder setItemText(String[] itemText, int[] itemLogo) {
            this.itemText = itemText;
            this.itemLogo = itemLogo;
            return this;
        }

        public Builder setItemText(String[] itemText) {
            this.itemText = itemText;
            return this;
        }
    }

    public static float dpToPx(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    public static RectF calculateRectInWindow(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return new RectF(location[0], location[1], location[0] + view.getMeasuredWidth(), location[1] + view.getMeasuredHeight());
    }

    public static void removeOnGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        } else {
            //noinspection deprecation
            view.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        }
    }

    public static RectF calculateRectOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new RectF(location[0], location[1], location[0] + view.getMeasuredWidth(), location[1] + view.getMeasuredHeight());
    }
}
