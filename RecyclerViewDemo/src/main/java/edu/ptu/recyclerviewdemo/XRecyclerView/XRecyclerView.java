package edu.ptu.recyclerviewdemo.XRecyclerView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import edu.ptu.recyclerviewdemo.XRecyclerView.layoutmanager.XLLManager;

/**
 * @author anshu.wang
 * @version 1.0
 * @time 2017/11/25.
 */
public class XRecyclerView extends RecyclerView implements Runnable{
    public XRecyclerView(Context context) {
        super(context);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    private RecyclerViewScrollListener mOnScrollListener = new RecyclerViewScrollListener() {


        @Override
        public void scrollVerticallyBy(int dy) {
            /**dy > 0 is load more; dy < 0 is refresh*/
            if ( !isLoadingData && isTouching
                    && ((dy < 0 && Math.abs(mHeaderView.getLayoutParams().height) < mHeaderViewMaxHeight)
                    || (dy > 0 && Math.abs(mHeaderView.getLayoutParams().height) > mHeaderViewHeight))) {
                mHandler.obtainMessage(0, dy, 0, null).sendToTarget();
                onScrollChanged(0, 0, 0, 0);
            }else if( !isLoadingMoreData && isManualLoadMoreData && isTouching
                    &&(dy > 0 && Math.abs(mFooterView.getLayoutParams().height) < mFooterViewHeight)){
                mHandler.obtainMessage(2, dy, 0, null).sendToTarget();
            }
        }
    };
    @Override
    public void run() {
        final LayoutManager manager = getLayoutManager();
        if (manager instanceof XLLManager) {
            ((XLLManager) manager).setScrollListener(mOnScrollListener);
        }
//        if (((XAdapter) mAdapter).getFootersCount() > 0) {
//            mFootViews.get(0).setVisibility(GONE);
//        }
    }
}
