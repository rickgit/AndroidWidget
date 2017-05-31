package edu.ptu.recyclerviewdemo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;

    public static class Group {
        public boolean isExtend = true;
        public String name ;
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
                Group group = (Group) flatList.get(0);
                group.isExtend = !group.isExtend;
                if (group.isExtend) {
                    for (int i = 0; i < group.datas.size(); i++) {
                        flatList.add(flatList.indexOf(group)+1, group.datas.get(group.datas.size()-i-1));
                    }
                    adapter.notifyItemRangeInserted(flatList.indexOf(group) + 1, flatList.indexOf(group) + 1+group.datas.size());
                } else {
                    for (int i = 0; i < group.datas.size(); i++) {
                        flatList.remove(flatList.indexOf(group) + 1);
                    }
                    adapter.notifyItemRangeRemoved(flatList.indexOf(group) + 1, flatList.indexOf(group) + 1+group.datas.size());
                }

            }
        });
        for (int i = 0; i < 10; i++) {
            Group e = new Group();
            e.name="group "+i;
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
        rcv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcv.setHasFixedSize(true);
        rcv.setItemAnimator(new DefaultItemAnimator());
        adapter = createrAdapter();
        rcv.setAdapter(adapter);
    }

    @NonNull
    private RecyclerView.Adapter createrAdapter() {
        return new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                if (viewType == 0) {
                    inflate.setBackgroundColor(0xffaa90cc);
                    return new RecyclerView.ViewHolder(inflate) {
                        @Override
                        public String toString() {
                            return super.toString();
                        }
                    };
                } else {
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
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                TextView tv = (TextView) ((ViewGroup) (holder.itemView)).getChildAt(0);
                Object customItem = flatList.get(position);
//                tv.setText(customItem.toString());
                System.out.println("custom " + customItem + " " + position);
                if (customItem instanceof Group) {
                    tv.setText("view " + ((Group) customItem).name);
                } else {
                    tv.setText("view " + flatList.get(position).toString());

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


    public static class StickHeaderHelper extends RecyclerView.OnScrollListener {

        private ViewGroup mStickyHolderLayout;
        private RecyclerView mRecyclerView;
        StickHeaderHelper(RecyclerView recyclerView){
            this.mRecyclerView=recyclerView;
        }

        public void getHeader(){
            FrameLayout stickyContainer = createContainer(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            ViewGroup oldParentLayout = getParent(mRecyclerView);
            oldParentLayout.addView(stickyContainer);
            // Initialize Holder Layout
            mStickyHolderLayout = (ViewGroup) LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.list_item, stickyContainer);

        }
        private ViewGroup getParent(View view) {
            return (ViewGroup) view.getParent();
        }
        private FrameLayout createContainer(int width, int height) {
            FrameLayout frameLayout = new FrameLayout(mRecyclerView.getContext());
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(width, height));
            return frameLayout;
        }
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }

}
