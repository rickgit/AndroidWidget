package edu.ptu.customview;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;

import edu.ptu.customview.animation.LotteryNumGenView;
import edu.ptu.customview.animation.LotteryScrollNumView;
import edu.ptu.customview.scrollText.WheelView;
import edu.ptu.customview.scrollText.adapter.ArrayWheelAdapter;

public class MainActivity extends AppCompatActivity {

    private LotteryScrollNumView child;
    private WheelView whellView;
    private ValueAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_rote);
        final LotteryNumGenView genView = (LotteryNumGenView) findViewById(R.id.tv_num);
        findViewById(R.id.tv_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                genView.startGen();
//                child.setNumber(3,System.currentTimeMillis()+5000,300);
                animator = ValueAnimator.ofFloat(21f,800.0f);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        whellView.scrollBy((Float) valueAnimator.getAnimatedValue());
                    }
                });
                animator.setDuration(15000);
                animator.setInterpolator(new LinearInterpolator());
                animator.start();

            }
        });
        findViewById(R.id.tv_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                child.addOffset(0);
                animator.cancel();
            }
        });

        LinearLayout vg = (LinearLayout) findViewById(R.id.vg_lottery_num);
        child =new LotteryScrollNumView(this);

        vg.addView(child);

        whellView = (WheelView) findViewById(R.id.wheelview);
        whellView.setAdapter(new ArrayWheelAdapter(new ArrayList(){
            {
                for (int i = 0; i < 32; i++) {
                    add(String.format("%02d",i));
                }
            }
        }));

    }
}
