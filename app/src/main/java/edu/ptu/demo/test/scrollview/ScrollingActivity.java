package edu.ptu.demo.test.scrollview;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.ptu.demo.R;

/**
 * Created by anshu.wang on 2016/11/16.
 */

public class ScrollingActivity extends FragmentActivity implements  SwipeRefreshLayout.OnRefreshListener {

    private FullyLinearLayoutManager layoutManager;
    private int lastVisibleItem = 0;
    private boolean isover = false;
    private SwipeRefreshLayout srfl_my_dynamic;
    private MyScrollView scrollView;//含有头的头布局和RecyclerView的
    private RecyclerView lvHpDynamicPost;
//    private AppBarLayout appbar;//上推隐藏；下拉显示的头布局

    private RelativeLayout rl_head_bg;//表示标题头
    private int currentPage = 0;//定义当前页为第1页
    private int pageSize = 20;//定义每页加载20条
    private MadeListAdapter dynamticListAdapter;//动态适配器
    private ArrayList<String> dataList;

//    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        srfl_my_dynamic = (SwipeRefreshLayout) findViewById(R.id.srfl_my_dynamic);
        scrollView = (MyScrollView) findViewById(R.id.scrollView);
        scrollView.smoothScrollTo(0, 0);
        lvHpDynamicPost = (RecyclerView) findViewById(R.id.recview);
        rl_head_bg = (RelativeLayout) findViewById(R.id.rl_head_bg);

        //设置刷新时动画的颜色，可以设置4个
        srfl_my_dynamic.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);


        scrollView.setOnScrollToBottomLintener(new MyScrollView.OnScrollToBottomListener() {

            @Override
            public void onScrollBottomListener(boolean isBottom) {
                /**这里遇到一个问题，当数据加载完成后，向上滑动ScrollView,还会提示一遍“没有更多数据了”，所以多加了一个向下滑动的标记isTop;如果是判断向下滑动，并且isBottom是滑动到了最低端才加载数据**/
                if (isBottom&&scrollView.isTop()) {
                    //GetToast.showToast(ScrollingActivity.this,isBottom+"");
                    if (srfl_my_dynamic.isRefreshing()) {
                        srfl_my_dynamic.setRefreshing(false);
                    }
                    currentPage++;
                    if (currentPage <= 4) {
                        queryDynamtic(currentPage);
                    } else {
                        Toast.makeText(ScrollingActivity.this, "没有更多数据了",Toast.LENGTH_LONG).show();
                    }
                }else{
                    //GetToast.showToast(ScrollingActivity.this,isBottom+"");
                }
            }
        });
        lvHpDynamicPost.setHasFixedSize(true);
        layoutManager = new FullyLinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setSmoothScrollbarEnabled(true);
        lvHpDynamicPost.setLayoutManager(layoutManager);
        dataList = new ArrayList<>();
        queryDynamtic(currentPage);
        dynamticListAdapter = new MadeListAdapter(dataList);
        lvHpDynamicPost.addItemDecoration(new DividerItemDecoration(
                this, LinearLayoutManager.HORIZONTAL, 10, getResources().getColor(R.color.colorPrimary)));

        srfl_my_dynamic.setOnRefreshListener(this);

        /*lvHpDynamicPost.addOnScrollListener(new OnVerticalScrollListener()
        );*/
        lvHpDynamicPost.setAdapter(dynamticListAdapter);
    }

    private void queryDynamtic(int currentPage) {
        for (int i = currentPage * 20 + currentPage; i < 20 + currentPage * 20; i++) {
            dataList.add("张三莉莉" + i);
        }
        if (null != dynamticListAdapter) {
            dynamticListAdapter.notifyDataSetChanged();
        }
    }


    /**
     * RecyclerView 滚动到顶端
     *
     * @param recyclerView
     * @return
     */
    public static boolean isSlideToTop(RecyclerView recyclerView) {
        return recyclerView.computeVerticalScrollOffset() <= 0;
    }


    @Override
    public void onRefresh() {
        currentPage = 0;
       Toast.makeText(ScrollingActivity.this, String.valueOf(currentPage),Toast.LENGTH_LONG).show();
        dataList.clear();
        queryDynamtic(currentPage);
        if (srfl_my_dynamic.isRefreshing()) {
            srfl_my_dynamic.setRefreshing(false);
        }
    }
    /****（上滑 up）（下滑 down）（顶部 top）（底部 bottom）
     * 这个方法利用了View的一个方法。public boolean canScrollVertically (int direction)
     这个方法是判断View在竖直方向是否还能 向上，向下 滑动。

     根据上面的例子，应该可以看出。 -1 表示 向上， 1 表示向下。

     同理还有 public boolean canScrollHorizontally (int direction) 方法用来判断 水平方向的滑动。 具体的使用方法可以参考 官方文档

     实现这个自定义的Listener之后你就可以在RecycyclerView的setOnScrollListener方法中使用了，像系统的使用方法一样。
     * ****/
    /*public class OnVerticalScrollListener
            extends RecyclerView.OnScrollListener {
    @Override
    public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        //解决RecyclerView和SwipeRefreshLayout共用存在的bug
        srfl_my_dynamic.setEnabled(layoutManager.findFirstCompletelyVisibleItemPosition() == 0);
        if (!recyclerView.canScrollVertically(-1)) {
            onScrolledToTop();
        } else if (!recyclerView.canScrollVertically(1)) {
            onScrolledToBottom();
        } else if (dy < 0) {
            onScrolledUp();
        } else if (dy > 0) {
            onScrolledDown();
        }
    }

    public void onScrolledUp() {
    }

    public void onScrolledDown() {
    }

    public void onScrolledToTop() {
        isTop = true;
        Toast.makeText(ScrollingActivity.this, "滑动到了顶端", Toast.LENGTH_SHORT).show();
    }

    public void onScrolledToBottom() {
        Toast.makeText(ScrollingActivity.this, "底部", Toast.LENGTH_SHORT).show();
        //if (newState == RecyclerView.SCROLL_STATE_IDLE ) {
        if (srfl_my_dynamic.isRefreshing()) {
            srfl_my_dynamic.setRefreshing(false);
        }
        currentPage++;
        if (currentPage <= 4) {
            Toast.makeText(ScrollingActivity.this, currentPage + "", Toast.LENGTH_SHORT).show();
            queryDynamtic(currentPage);
        } else {
            Toast.makeText(ScrollingActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
        }
        //}
    }
}
            */


    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_zan;
        ViewHolder(View view) {
            super(view);
            tv_zan= (TextView) view.findViewById(android.R.id.text1);
        }
    }
    private class MadeListAdapter  extends RecyclerView.Adapter{
        ArrayList<String> data;

        public MadeListAdapter(ArrayList<String> data) {
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            String text= data.get(position);
            viewHolder.tv_zan.setText(text);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    /*protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        //super.onOffsetChanged(appBarLayout, i);
        如果您使用的是LinearLayoutManager或StaggeredGridLayoutManager，
        它们都有一个scrollToPositionWithOffset（int position,int offset）方法，第一个参数是item的position值，
        第二个参数是第一个参数对应的item距离RecyclerView的顶部（Top）的距离（px）
        if (srfl_my_dynamic == null) return;
        layoutManager.scrollToPositionWithOffset(0,10);
        if(isSlideToTop(lvHpDynamicPost)){
            srfl_my_dynamic.setEnabled(i >= 0 ? true : false);
            //appbar.setVisibility(View.VISIBLE);
        }else{
            //appbar.setVisibility(View.GONE);
        }
    }
    */

}