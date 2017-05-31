/*
 * Copyright 2016 Martin Guillon & Davide Steduto (Hyper-Optimized for FlexibleAdapter project)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.ptu.recyclerviewdemo;

import android.animation.Animator;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.FlexibleAdapter.OnStickyHeaderChangeListener;
import eu.davidea.flexibleadapter.items.IHeader;
import eu.davidea.viewholders.ViewHolder;

/**
 * A sticky header helper, to use only with {@link HeaderAdapter}.
 * <p>Header ViewHolders must be of type {@link HeaderViewHolder}.</p>
 *
 * @since 25/03/2016 Created
 */
public final class StickyHeaderHelper extends OnScrollListener {

    private static final String TAG = StickyHeaderHelper.class.getSimpleName();

    private HeaderAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ViewGroup mStickyHolderLayout;
    private HeaderViewHolder mStickyHeaderViewHolder;
    private OnStickyHeaderChangeListener mStickyHeaderChangeListener;
    private int mHeaderPosition = RecyclerView.NO_POSITION;
    private boolean displayWithAnimation = false;
    private float mElevation;

    public StickyHeaderHelper(HeaderAdapter adapter,
                              OnStickyHeaderChangeListener stickyHeaderChangeListener,
                              ViewGroup stickyHolderLayout) {
        mAdapter = adapter;
        mStickyHeaderChangeListener = stickyHeaderChangeListener;
        mStickyHolderLayout = stickyHolderLayout;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        displayWithAnimation = mRecyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE;
        updateOrClearHeader(false);
    }

    public void attachToRecyclerView(RecyclerView parent) {
        if (mRecyclerView != null) {
            mRecyclerView.removeOnScrollListener(this);
            clearHeader();
        }
        if (parent == null) {
            throw new IllegalStateException("Adapter is not attached to RecyclerView. Enable sticky headers after setting adapter to RecyclerView.");
        }
        mRecyclerView = parent;
        mRecyclerView.addOnScrollListener(this);
        initStickyHeadersHolder();
    }

    public void detachFromRecyclerView() {
        mRecyclerView.removeOnScrollListener(this);
        mRecyclerView = null;
        clearHeaderWithAnimation();
//		if (FlexibleAdapter.DEBUG) Log.i(TAG, "StickyHolderLayout detached");
    }

    private FrameLayout createContainer(int width, int height) {
        FrameLayout frameLayout = new FrameLayout(mRecyclerView.getContext());
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        return frameLayout;
    }

    private ViewGroup getParent(View view) {
        return (ViewGroup) view.getParent();
    }

    private void initStickyHeadersHolder() {
        if (mStickyHolderLayout == null) {
            // Create stickyContainer for shadow elevation
            FrameLayout stickyContainer = createContainer(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            ViewGroup oldParentLayout = getParent(mRecyclerView);
            oldParentLayout.addView(stickyContainer);
            // Initialize Holder Layout
            mStickyHolderLayout = (ViewGroup) LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.list_item, stickyContainer);
//			if (FlexibleAdapter.DEBUG) Log.i(TAG, "Default StickyHolderLayout initialized");
        }
//		else if (FlexibleAdapter.DEBUG) {
//			Log.i(TAG, "User defined StickyHolderLayout initialized");
//		}
        // Show sticky header if exists already
        updateOrClearHeader(false);
    }

    public int getStickyPosition() {
        return mHeaderPosition;
    }

    private boolean hasStickyHeaderTranslated(int position) {
        RecyclerView.ViewHolder vh = mRecyclerView.findViewHolderForAdapterPosition(position);
        return vh != null && (vh.itemView.getX() < 0 || vh.itemView.getY() < 0);
    }

    private void onStickyHeaderChange(int sectionIndex) {
        if (mStickyHeaderChangeListener != null) {
            mStickyHeaderChangeListener.onStickyHeaderChange(sectionIndex);
        }
    }

    public void updateOrClearHeader(boolean updateHeaderContent) {
        if (mAdapter.getItemCount() == 0) {//!mAdapter.areHeadersShown() || mAdapter.hasSearchText() ||
            clearHeaderWithAnimation();
            return;
        }
        int firstHeaderPosition = getStickyPosition(RecyclerView.NO_POSITION);
        if (firstHeaderPosition >= 0) {
            updateHeader(firstHeaderPosition, updateHeaderContent);
        } else {
            clearHeader();
        }
    }

    private void updateHeader(int headerPosition, boolean updateHeaderContent) {
        // Check if there is a new header to be sticky
        if (mHeaderPosition != headerPosition) {
            // #244 - Don't animate if header is already visible at the first layout position
            int firstVisibleItemPosition = Utils.findFirstVisibleItemPosition(mRecyclerView.getLayoutManager());
            // Animate if headers were hidden, but don't if configuration changed (rotation)
            if (displayWithAnimation && mHeaderPosition == RecyclerView.NO_POSITION &&
                    headerPosition != firstVisibleItemPosition) {
                displayWithAnimation = false;
                mStickyHolderLayout.setAlpha(0);
                mStickyHolderLayout.animate().alpha(1).start();
            } else {
                mStickyHolderLayout.setAlpha(1);
            }
            mHeaderPosition = headerPosition;
            HeaderViewHolder holder = getHeaderViewHolder(headerPosition);
//			if (FlexibleAdapter.DEBUG)
//				Log.d(TAG, "swapHeader newHeaderPosition=" + mHeaderPosition);
            swapHeader(holder);
        } else if (updateHeaderContent) {
            // #299 - ClassCastException after click on expanded sticky header when AutoCollapse is enabled
//			mStickyHeaderViewHolder = getHeaderViewHolder(headerPosition);
//			mStickyHeaderViewHolder.setBackupPosition(headerPosition);
//			mAdapter.onBindViewHolder(mStickyHeaderViewHolder, headerPosition);
//			ensureHeaderParent();
            //FIXME: update viewHolder when sticky
        }
        translateHeader();
    }

    private void configureLayoutElevation() {
        // 1. Take elevation from header item layout (most important)
        mElevation = ViewCompat.getElevation(mStickyHeaderViewHolder.getContentView());
        if (mElevation == 0f) {
            // 2. Take elevation settings
            mElevation = mAdapter.getStickyHeaderElevation();
        }
        if (mElevation > 0) {
            // Needed to elevate the view
            ViewCompat.setBackground(mStickyHolderLayout, mStickyHeaderViewHolder.getContentView().getBackground());
        }
    }

    private void translateHeader() {
        // Sticky at zero offset (no translation)
        int headerOffsetX = 0, headerOffsetY = 0;
        // Get calculated elevation
        float elevation = mElevation;

        // Search for the position where the next header item is found and translate the new offset
        for (int i = 0; i < mRecyclerView.getChildCount(); i++) {
            final View nextChild = mRecyclerView.getChildAt(i);
            if (nextChild != null) {
                int adapterPos = mRecyclerView.getChildAdapterPosition(nextChild);
                int nextHeaderPosition = getStickyPosition(adapterPos);
                if (mHeaderPosition != nextHeaderPosition) {
                    if (Utils.getOrientation(mRecyclerView.getLayoutManager()) == OrientationHelper.HORIZONTAL) {
                        if (nextChild.getLeft() > 0) {
                            int headerWidth = mStickyHolderLayout.getMeasuredWidth();
                            int nextHeaderOffsetX = nextChild.getLeft() - headerWidth;
                            headerOffsetX = Math.min(nextHeaderOffsetX, 0);
                            // Early remove the elevation/shadow to match with the next view
                            if (nextHeaderOffsetX < 5) elevation = 0f;
                            if (headerOffsetX < 0) break;
                        }
                    } else {
                        if (nextChild.getTop() > 0) {
                            int headerHeight = mStickyHolderLayout.getMeasuredHeight();
                            int nextHeaderOffsetY = nextChild.getTop() - headerHeight;
                            headerOffsetY = Math.min(nextHeaderOffsetY, 0);
                            // Early remove the elevation/shadow to match with the next view
                            if (nextHeaderOffsetY < 5) elevation = 0f;
                            if (headerOffsetY < 0) break;
                        }
                    }
                }
            }
        }
        // Apply the user elevation to the sticky container
        ViewCompat.setElevation(mStickyHolderLayout, elevation);
        // Apply translation (pushed up by another header)
        mStickyHolderLayout.setTranslationX(headerOffsetX);
        mStickyHolderLayout.setTranslationY(headerOffsetY);
        //Log.v(TAG, "TranslationX=" + headerOffsetX + " TranslationY=" + headerOffsetY);
    }

    private void swapHeader(HeaderViewHolder newHeader) {
        if (mStickyHeaderViewHolder != null) {
            resetHeader(mStickyHeaderViewHolder);
        }
        mStickyHeaderViewHolder = newHeader;
        if (mStickyHeaderViewHolder != null) {
            mStickyHeaderViewHolder.setIsRecyclable(false);
            ensureHeaderParent();
        }
        onStickyHeaderChange(mHeaderPosition);
    }

    private void ensureHeaderParent() {
        final View view = mStickyHeaderViewHolder.getContentView();
        // #121 - Make sure the measured height (width for horizontal layout) is kept if
        // WRAP_CONTENT has been set for the Header View
        mStickyHeaderViewHolder.itemView.getLayoutParams().width = view.getMeasuredWidth();
        mStickyHeaderViewHolder.itemView.getLayoutParams().height = view.getMeasuredHeight();
        // Ensure the itemView is hidden to avoid double background
        mStickyHeaderViewHolder.itemView.setVisibility(View.INVISIBLE);
        // #139 - Copy xml params instead of Measured params
        ViewGroup.LayoutParams params = mStickyHolderLayout.getLayoutParams();
        params.width = view.getLayoutParams().width;
        params.height = view.getLayoutParams().height;
        removeViewFromParent(view);
        mStickyHolderLayout.addView(view);
        configureLayoutElevation();
    }

    /**
     * On swing and on fast scroll some header items might still be invisible. We need
     * to identify them and restore visibility.
     */
    @SuppressWarnings("unchecked")
    private void restoreHeaderItemVisibility() {
        if (mRecyclerView == null) return;
        // Restore every header item visibility
        for (int i = 0; i < mRecyclerView.getChildCount(); i++) {
            View oldHeader = mRecyclerView.getChildAt(i);
            int headerPos = mRecyclerView.getChildAdapterPosition(oldHeader);
            if (mAdapter.isHeader(mAdapter.getItem(headerPos))) {
                oldHeader.setVisibility(View.VISIBLE);
            }
        }
    }

    private void resetHeader(HeaderViewHolder header) {
        restoreHeaderItemVisibility();
        // Clean the header container
        final View view = header.getContentView();
        removeViewFromParent(view);
        // Reset translation on removed header
        view.setTranslationX(0);
        view.setTranslationY(0);
        if (!header.itemView.equals(view))
            ((ViewGroup) header.itemView).addView(view);
        header.setIsRecyclable(true);
        // #294 - Expandable header is not resized / redrawn on automatic configuration change when sticky headers are enabled
        header.itemView.getLayoutParams().width = view.getLayoutParams().width;
        header.itemView.getLayoutParams().height = view.getLayoutParams().height;
    }

    private void clearHeader() {
        if (mStickyHeaderViewHolder != null) {
//			if (FlexibleAdapter.DEBUG) Log.d(TAG, "clearHeader");
            resetHeader(mStickyHeaderViewHolder);
            mStickyHolderLayout.setAlpha(0);
            mStickyHolderLayout.animate().cancel();
            mStickyHolderLayout.animate().setListener(null);
            mStickyHeaderViewHolder = null;
            restoreHeaderItemVisibility();
            mHeaderPosition = RecyclerView.NO_POSITION;
            onStickyHeaderChange(mHeaderPosition);
        }
    }

    public void clearHeaderWithAnimation() {
        if (mStickyHeaderViewHolder != null && mHeaderPosition != RecyclerView.NO_POSITION) {
            mStickyHolderLayout.animate().setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    mHeaderPosition = RecyclerView.NO_POSITION;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    displayWithAnimation = true; //This helps after clearing filter
                    mStickyHolderLayout.setAlpha(0);
                    clearHeader();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            mStickyHolderLayout.animate().alpha(0).start();
        }
    }

    private static void removeViewFromParent(final View view) {
        final ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }
    }

    @SuppressWarnings("unchecked")
    private int getStickyPosition(int adapterPosHere) {
        if (adapterPosHere == RecyclerView.NO_POSITION) {
            adapterPosHere = Utils.findFirstVisibleItemPosition(mRecyclerView.getLayoutManager());
            if (adapterPosHere == 0 && !hasStickyHeaderTranslated(0)) {
                return RecyclerView.NO_POSITION;
            }
        }
        IHeader header = mAdapter.getSectionHeader(adapterPosHere);
        // Header cannot be sticky if it's also an Expandable in collapsed status, RV will raise an exception
        if (header == null || mAdapter.isExpandable(header) && !mAdapter.isExpanded(header)) {
            return RecyclerView.NO_POSITION;
        }
        return mAdapter.getGlobalPositionOf(header);
    }

    /**
     * Gets the header view for the associated header position. If it doesn't exist yet, it will
     * be created, measured, and laid out.
     *
     * @param position the adapter position to get the header view
     * @return ViewHolder of type ViewHolder of the associated header position
     */
    @SuppressWarnings("unchecked")
    private HeaderViewHolder getHeaderViewHolder(int position) {
        // Find existing ViewHolder
        HeaderViewHolder holder = (HeaderViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
        if (holder == null) {
            // Create and binds a new ViewHolder
            holder = (HeaderViewHolder) mAdapter.createViewHolder(mRecyclerView, mAdapter.getItemViewType(position));
            mAdapter.bindViewHolder(holder, position);

            // Restore the Adapter position
            holder.setBackupPosition(position);

            // Calculate width and height
            int widthSpec;
            int heightSpec;
            if (Utils.getOrientation(mRecyclerView.getLayoutManager()) == OrientationHelper.VERTICAL) {
                widthSpec = View.MeasureSpec.makeMeasureSpec(mRecyclerView.getWidth(), View.MeasureSpec.EXACTLY);
                heightSpec = View.MeasureSpec.makeMeasureSpec(mRecyclerView.getHeight(), View.MeasureSpec.UNSPECIFIED);
            } else {
                widthSpec = View.MeasureSpec.makeMeasureSpec(mRecyclerView.getWidth(), View.MeasureSpec.UNSPECIFIED);
                heightSpec = View.MeasureSpec.makeMeasureSpec(mRecyclerView.getHeight(), View.MeasureSpec.EXACTLY);
            }

            // Measure and Layout the stickyView
            final View headerView = holder.getContentView();
            int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                    mRecyclerView.getPaddingLeft() + mRecyclerView.getPaddingRight(),
                    headerView.getLayoutParams().width);
            int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                    mRecyclerView.getPaddingTop() + mRecyclerView.getPaddingBottom(),
                    headerView.getLayoutParams().height);

            headerView.measure(childWidth, childHeight);
            headerView.layout(0, 0, headerView.getMeasuredWidth(), headerView.getMeasuredHeight());
        }
        return holder;
    }

    /**
     * @since 05/03/2016
     */
    public interface OnStickyHeaderChangeListener {
        /**
         * Called when the current sticky header changed.
         *
         * @param sectionIndex the position of header, -1 if no header is sticky
         * @since 5.0.0-b1
         */
        void onStickyHeaderChange(int sectionIndex);
    }

    public static class HeaderAdapter extends RecyclerView.Adapter {

        private float stickyHeaderElevation;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }


        public float getStickyHeaderElevation() {
            return stickyHeaderElevation;
        }

        public View getContentView() {
            return null;
        }

        ;

        public boolean isHeader(Object item) {
            return false;
        }

        ;

        public Object getItem(int headerPos) {
            return null;
        }

        public IHeader getSectionHeader(int adapterPosHere) {
            return null;
        }

        public boolean isExpandable(IHeader header) {
            return false;
        }

        public int getGlobalPositionOf(IHeader header) {
            return 0;
        }

        public boolean isExpanded(IHeader header) {
            return false;
        }
    }

    public static interface IHeader {

    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        private View contentView;
        private int backupPosition;

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }

        public View getContentView() {
            return contentView;
        }

        public void setBackupPosition(int backupPosition) {
            this.backupPosition = backupPosition;
        }
    }
}