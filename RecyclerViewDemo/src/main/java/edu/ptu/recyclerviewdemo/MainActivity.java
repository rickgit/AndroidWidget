package edu.ptu.recyclerviewdemo;

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

import java.util.ArrayList;
import java.util.List;

import edu.ptu.recyclerviewdemo.expandablelayout.ExpandableLayoutItem;
import edu.ptu.recyclerviewdemo.recycleritemanimator.NoAlphaDefaultItemAnimator;
import edu.ptu.recyclerviewdemo.stickheader.StickyHeaderHelper;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;

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
        RecyclerView rcv = (RecyclerView) findViewById(R.id.rcv);
        rcv.setBackgroundColor(0xffcc89aa);
        rcv.setLayoutManager(new SmoothScrollLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcv.setHasFixedSize(true);
        rcv.setItemAnimator(new NoAlphaDefaultItemAnimator());
        adapter = createrAdapter();
        rcv.setAdapter(adapter);
        StickyHeaderHelper mStickyHeaderHelper = new StickyHeaderHelper((StickyHeaderHelper.HeaderAdapter) adapter, null, null);
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
                View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                if (viewType == 0) {
                    inflate.setBackgroundColor(0xffaa90cc);

                } else {
                    inflate.setBackgroundColor(0xffcc89aa);

                }
                return new HeaderViewHolderTem(inflate, this);
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
                    adapter.notifyItemRangeInserted(flatList.indexOf(group) + 1, group.datas.size());
                } else {
                    for (int i = 0; i < group.datas.size(); i++) {
                        flatList.remove(flatList.indexOf(group) + 1);
                    }
                    adapter.notifyItemRangeRemoved(flatList.indexOf(group) + 1, group.datas.size());

                }
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

        public HeaderViewHolderTem(View view, StickyHeaderHelper.HeaderAdapter adapter) {
            super(view, adapter);
            tv = (TextView) view.findViewById(R.id.tv);
        }
    }
}
