package edu.ptu.customview;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.ptu.customview.animation.LotteryNumGenView;
import edu.ptu.customview.animation.LotteryScrollNumView;
import edu.ptu.customview.scrollText.LotteryWheelView;
import edu.ptu.customview.scrollText.adapter.ArrayWheelAdapter;

public class MainActivity extends AppCompatActivity {

    private LotteryScrollNumView child;
    private List<LotteryWheelView> whellView = new ArrayList<>();
    private List<ValueAnimator> animators = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_rote);
        final LotteryNumGenView genView = (LotteryNumGenView) findViewById(R.id.tv_num);
        final Random random = new Random();
        findViewById(R.id.tv_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLotteryAnimation();

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
        initLotteryView(vgWheel);


    }
    /**
     *
     * @param
     * @return void
     * @author anshu.wang 2017-11-17 17:52:42
     **/
    private void startLotteryAnimation() {
        int dur = 1000;
        Random random = new Random();
        for (int i = 0; i < animators.size(); i++) {
            if (animators.get(i).isRunning())
                return;
        }
        System.out.println("数字");
        System.out.println("数字");
        for (int i = 0; i < whellView.size(); i++) {
            final int finalI = i;
            int currentItem = whellView.get(finalI).getCurrentItem();
            whellView.get(finalI).setCurrentItem(currentItem);
            int num = random.nextInt(31) + 1;
            int nexValueDiff = num - (32 - currentItem);
            int change = (int) (-whellView.get(finalI).getItemHeight() * nexValueDiff - whellView.get(finalI).getItemHeight() * 32);
            System.out.println("数字 " + num);
            dur += 30 + random.nextInt(300);
            animators.get(i).setDuration(dur);
            animators.get(finalI).setIntValues(0, change);
            animators.get(i).start();
        }
        System.out.println("数字=");
        System.out.println("数字");
    }
    /**
     *
     * @param vgWheel
     * @return void
     * @author anshu.wang 2017-11-17 17:52:56
     **/
    private void initLotteryView(ViewGroup vgWheel) {
        for (int i = 0; i < vgWheel.getChildCount(); i++) {
            final LotteryWheelView wheel = (LotteryWheelView) vgWheel.getChildAt(i);
            whellView.add(wheel);
            wheel.setAdapter(new ArrayWheelAdapter(new ArrayList() {{
                for (int j = 0; j < 32; j++) {
                    add(String.format("%02d", 32 - j));
                }
            }}));
            wheel.setCurrentItem(31);
            ValueAnimator animator = ValueAnimator.ofInt(0,0);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate( ValueAnimator valueAnimator) {
                    int animatedValue = (int) valueAnimator.getAnimatedValue();
                    wheel.setTotalScrollY(animatedValue);
                    wheel.invalidate();
                }
            });
            animator.setDuration(2000);
            animator.setFloatValues();
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animators.add(animator);
            if (i==vgWheel.getChildCount()-1||i==vgWheel.getChildCount()-2)
                wheel.setTextPaintColor(0xff35a3ff);
        }
    }
}
