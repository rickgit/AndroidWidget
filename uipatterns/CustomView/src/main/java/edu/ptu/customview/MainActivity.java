package edu.ptu.customview;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.ptu.customview.animation.LotteryNumGenView;
import edu.ptu.customview.animation.LotteryScrollNumView;
import edu.ptu.customview.scrollText.WheelView;
import edu.ptu.customview.scrollText.adapter.ArrayWheelAdapter;
import edu.ptu.customview.scrollText.adapter.NumericWheelAdapter;
import edu.ptu.customview.scrollText.adapter.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {

    private LotteryScrollNumView child;
    private List<WheelView> whellView = new ArrayList<>();
    private  List<ValueAnimator> animators= new ArrayList<>();

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
                int dur=1000;
                Random random = new Random();
                for (int i = 0; i < animators.size(); i++) {
                    animators.get(i).cancel();
                    animators.get(i).setDuration(dur);
                    dur+=30+ random.nextInt(300);
                    animators.get(i).start();
                }

            }
        });
        findViewById(R.id.tv_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                child.addOffset(0);
                for (int i = 0; i < animators.size(); i++) {
                    animators.get(i).cancel();

                }
            }
        });

        LinearLayout vg = (LinearLayout) findViewById(R.id.vg_lottery_num);
        child = new LotteryScrollNumView(this);

        vg.addView(child);

        ViewGroup vgWheel = (ViewGroup) findViewById(R.id.wheelviews);
        for (int i = 0; i < vgWheel.getChildCount(); i++) {
            final WheelView wheel = (WheelView) vgWheel.getChildAt(i);
            whellView.add(wheel);
            wheel.setAdapter(new NumericWheelAdapter(1, 32, "%02d"));
            final int finalI = i;
            wheel.addOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    if (index!=finalI)
                    wheel.setCurrentItem(finalI);
                }
            });

            ValueAnimator animator = ValueAnimator.ofFloat(21f, 800.0f);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                    wheel.scrollBy((Float) valueAnimator.getAnimatedValue());
                }
            });
            animator.setDuration(5000);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animators.add(animator);
        }
//        whellView = (WheelView) vgWheelS;
//        whellView.setAdapter(new ArrayWheelAdapter(new ArrayList(){
//            {
//                for (int i = 0; i < 32; i++) {
//                    add(String.format("%02d",i));
//                }
//            }
//        }));

    }
}
