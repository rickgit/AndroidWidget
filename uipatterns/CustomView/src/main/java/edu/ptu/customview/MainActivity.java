package edu.ptu.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import edu.ptu.customview.animation.LotteryNumGenView;
import edu.ptu.customview.slidinglayout.SlidingLayout;
import edu.ptu.customview.utils.LogUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_rote);
        final LotteryNumGenView genView = (LotteryNumGenView) findViewById(R.id.tv_num);
        findViewById(R.id.tv_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genView.startGen();
            }
        });
        findViewById(R.id.tv_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genView.setText("16");
            }
        });
        findViewById(R.id.tv_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genView.stopGen();
            }
        });
    }
}
