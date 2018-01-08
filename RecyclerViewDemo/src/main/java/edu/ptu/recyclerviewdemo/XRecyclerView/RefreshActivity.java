package edu.ptu.recyclerviewdemo.XRecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.ptu.recyclerviewdemo.R;

/**
 * Created by WangAnshu on 2017/11/26.
 */

public class RefreshActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xrecyclerview);
        RecyclerView xRecyclerView = (RecyclerView) findViewById(R.id.xrecyclerview);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        xRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                TextView itemView = new TextView(parent.getContext());
                itemView.setText("asdsf");
                return new RecyclerView.ViewHolder(itemView) {
                    @Override
                    public String toString() {
                        return super.toString();
                    }
                };
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                if (position % 2 == 0)
                    holder.itemView.setBackgroundColor(0xffccaa);
                else holder.itemView.setBackgroundColor(0x1077aa);
            }

            @Override
            public int getItemCount() {
                return 30;
            }
        });
    }
}
