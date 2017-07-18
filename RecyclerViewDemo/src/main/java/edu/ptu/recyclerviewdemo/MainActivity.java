package edu.ptu.recyclerviewdemo;

import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sch.rfview.AnimRFRecyclerView;
import com.sch.rfview.decoration.DividerItemDecoration;
import com.sch.rfview.manager.AnimRFLinearLayoutManager;
import com.xlibs.xrv.LayoutManager.XLinearLayoutManager;
import com.xlibs.xrv.listener.OnLoadMoreListener;
import com.xlibs.xrv.listener.OnRefreshListener;
import com.xlibs.xrv.view.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.ptu.recyclerviewdemo.expandablelayout.ExpandableLayoutItem;
import edu.ptu.recyclerviewdemo.recycleritemanimator.NoAlphaDefaultItemAnimator;
import edu.ptu.recyclerviewdemo.stickheader.StickyHeaderHelper;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private XRecyclerView rcv;

    public static class Group implements StickyHeaderHelper.IHeader {
        public boolean isExtend = true;
        public String name;
        public List<String> datas = new ArrayList<>();
    }

    //    public List<Group> groups = new ArrayList<>();
    public List<Object> flatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View vAdd = findViewById(R.id.add);
        vAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colloOrExpand((Group) flatList.get(0));

            }
        });
        for (int i = 0; i < 10; i++) {
            Group e = new Group();
            e.name = "group " + i;
            flatList.add(e);
            for (int j = 0; j < 5; j++) {
                e.datas.add("child " + j);
            }
            flatList.addAll(e.datas);
        }
        //gen flatList


        //
        rcv = (XRecyclerView) findViewById(R.id.rcv);
        rcv.setBackgroundColor(0xffcc89aa);
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_view, null);
        // 脚部
        View footerView = LayoutInflater.from(this).inflate(R.layout.footer_view, null);

        // 使用重写后的线性布局管理器
        AnimRFLinearLayoutManager manager = new AnimRFLinearLayoutManager(this);
        rcv.setLayoutManager(manager);
        rcv.addItemDecoration(new DividerItemDecoration(this, manager.getOrientation(), true));
//            // 添加头部和脚部，如果不添加就使用默认的头部和脚部
//            mRecyclerView.addHeaderView(headerView);
//            // 设置头部的最大拉伸倍率，默认1.5f，必须写在setHeaderImage()之前
//            mRecyclerView.setScaleRatio(1.7f);
//            // 设置下拉时拉伸的图片，不设置就使用默认的
//            mRecyclerView.setHeaderImage((ImageView) headerView.findViewById(R.id.iv_hander));
//            mRecyclerView.addFootView(footerView);
        // 设置刷新动画的颜色
//        rcv.setColor(Color.RED, Color.BLUE);
//        // 设置头部恢复动画的执行时间，默认500毫秒
//        rcv.setHeaderImageDurationMillis(300);
//        // 设置拉伸到最高时头部的透明度，默认0.5f
//        rcv.setHeaderImageMinAlpha(0.6f);

        View mHeaderView = LayoutInflater.from(this).inflate(R.layout.header_view, null);
        View mFooterView = LayoutInflater.from(this).inflate(R.layout.footer_view, null);
        rcv.addHeaderView(mHeaderView, 50);
        rcv.addFootView(mFooterView, 50);
        rcv.setEnableRefreshAndLoadMore(true);
        rcv.setLayoutManager(new XLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rcv.refreshComplate();
                    }
                }, 3000);
            }
        });
        rcv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rcv.loadMoreComplate();
                    }
                }, 3000);
            }
        });
        rcv.setHasFixedSize(true);
        rcv.setItemAnimator(new NoAlphaDefaultItemAnimator());
        adapter = createrAdapter();
        rcv.setAdapter(adapter);
        StickyHeaderHelper mStickyHeaderHelper = new StickyHeaderHelper((StickyHeaderHelper.HeaderAdapter) adapter, null, null) {
            @Override
            public int getHeaderCount() {
                return 1;//多了个刷新的页面
            }
        };
        mStickyHeaderHelper.attachToRecyclerView(rcv);
    }

    private void colloOrExpand(Group groupPar) {
        Group group = (Group) groupPar;
        group.isExtend = !group.isExtend;
        if (group.isExtend) {
            for (int i = 0; i < group.datas.size(); i++) {
                flatList.add(flatList.indexOf(group) + 1, group.datas.get(group.datas.size() - i - 1));
            }
            adapter.notifyItemRangeInserted(flatList.indexOf(group) + 1, group.datas.size());
        } else {
            for (int i = 0; i < group.datas.size(); i++) {
                flatList.remove(flatList.indexOf(group) + 1);
            }
            adapter.notifyItemRangeRemoved(flatList.indexOf(group) + 1, group.datas.size());
        }
    }

    @NonNull
    private RecyclerView.Adapter createrAdapter() {
        return new StickyHeaderHelper.HeaderAdapter() {
            @Override
            public boolean isHeader(Object item) {
                if (item != null && item instanceof StickyHeaderHelper.IHeader)
                    return true;
                return false;
            }

            @Override
            public Object getItem(int headerPos) {
                return flatList.get(headerPos);
            }

            @Override
            public boolean isExpandable(StickyHeaderHelper.IHeader header) {
                if (header != null && header instanceof StickyHeaderHelper.IHeader)
                    return true;
                return false;
            }

            @Override
            public int getGlobalPositionOf(StickyHeaderHelper.IHeader header) {
                return flatList.indexOf(header);
            }

            @Override
            public boolean isExpanded(StickyHeaderHelper.IHeader header) {
                return ((Group) header).isExtend;
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                if (viewType == 0) {
                    View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                    ((ViewGroup)inflate).getChildAt(0).setBackgroundColor(0xffaa90cc);
                    return new HeaderViewHolderTem(inflate, this, rcv);
                } else {
                    View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                    inflate.setBackgroundColor(0xffcc89aa);
                    return new RecyclerView.ViewHolder(inflate) {
                        @Override
                        public String toString() {
                            return super.toString();
                        }
                    };
                }

            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
                super.onBindViewHolder(holder, position, payloads);
                Log.e("payLoad", "payload 用于局部刷新 不能为空");

            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                final Object customItem = flatList.get(position);
//                tv.setText(customItem.toString());
                System.out.println("custom " + customItem + " " + position);
                if (holder instanceof HeaderViewHolderTem) {
                    TextView tv = ((HeaderViewHolderTem) holder).tv;
                    if (tv != null) {
                        if (customItem instanceof Group) {
                            tv.setText("view " + ((Group) customItem).name);
                        } else {
                            tv.setText("view " + flatList.get(position).toString());

                        }
                    }
                }
            }

            public void colloOrExpandGroup(int groupposiziton) {
                Object item = flatList.get(groupposiziton);
                if (!(item instanceof Group))
                    return;
                Group group = (Group) item;
                group.isExtend = !group.isExtend;
                if (group.isExtend) {
                    for (int i = 0; i < group.datas.size(); i++) {
                        flatList.add(flatList.indexOf(group) + 1, group.datas.get(group.datas.size() - i - 1));
                    }
                    rcv.getAdapter().notifyItemRangeInserted(flatList.indexOf(group) + 1 + getRVHeaderCount(), group.datas.size());
                } else {
                    for (int i = 0; i < group.datas.size(); i++) {
                        flatList.remove(flatList.indexOf(group) + 1);
                    }
                    rcv.getAdapter().notifyItemRangeRemoved(flatList.indexOf(group) + 1 + getRVHeaderCount(), group.datas.size());

                }
//                rcv.getAdapter().notifyDataSetChanged();
            }

            public int getRVHeaderCount() {
                return 1;
            }

            @Override
            public int getItemViewType(int position) {
                if (flatList.get(position) instanceof Group)
                    return 0;
                else {
                    return 1;
                }


            }

            @Override
            public int getItemCount() {

                return flatList.size();
            }
        };
    }

    public static class HeaderViewHolderTem extends StickyHeaderHelper.HeaderViewHolder {
        TextView tv;

        public HeaderViewHolderTem(View view, StickyHeaderHelper.HeaderAdapter adapter, RecyclerView recyclerView) {
            super(view, adapter, recyclerView);
            tv = (TextView) view.findViewById(R.id.tv);

        }

        @Override
        public int getRecyclerHeaderCount() {
            return 1;
        }
    }
}
