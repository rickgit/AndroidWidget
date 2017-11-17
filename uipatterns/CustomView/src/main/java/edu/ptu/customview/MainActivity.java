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
//                genView.startGen();
//                child.setNumber(3,System.currentTimeMillis()+5000,300);
                int dur = 1000;
                Random random = new Random();
                for (int i = 0; i < animators.size(); i++) {
                    long currentPlayTime = animators.get(i).getCurrentPlayTime();
                    if (animators.get(i).isRunning())
                        return;
                }
                System.out.println("数字=");
                System.out.println("数字");
                for (int i = 0; i < whellView.size(); i++) {
                    final int finalI = i;


//                    int itemHeight = (int) whellView.get(finalI).getItemHeight();
//                    int initValue = whellView.get(finalI).getCurrentItem() - 0;
//                    whellView.get(finalI).setTotalScrollY(initValue*itemHeight+itemHeight*3);
//                    whellView.get(finalI).invalidate();


//                    int itemHeight = (int) whellView.get(i).getItemHeight();
//                    int itemHeightStart = whellView.get(i).getCurrentItem() * itemHeight;
//                    final int endTIme = itemHeightStart + itemHeight * 3;
//                    ValueAnimator animator = ValueAnimator.ofInt(itemHeightStart, endTIme);
//
//                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(final ValueAnimator valueAnimator) {
//                            Integer animatedValue = (Integer) valueAnimator.getAnimatedValue();
//
//
//                            whellView.get(finalI).setTotalScrollY(animatedValue);
//                            whellView.get(finalI).invalidate();
//                            if (endTIme==animatedValue)
//                                whellView.get(finalI).smoothScroll(WheelView.ACTION.FLING);
//                        }
//                    });
//                    animator.setDuration(5000);
//                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
//                    animator.start();


//                    animators.get(i).cancel();
                    int currentItem = whellView.get(finalI).getCurrentItem();
                    whellView.get(finalI).setCurrentItem(currentItem);

                    animators.get(i).setDuration(dur);
                    int num = random.nextInt(31) + 1;
                    int nexValueDiff = num - (32 - currentItem);
                    float change = -whellView.get(finalI).getItemHeight() * nexValueDiff - whellView.get(finalI).getItemHeight() * 32;
                    System.out.println("数字 " + num);
                    dur += 30 + random.nextInt(300);
                    animators.get(finalI).setFloatValues(0, change);
                    animators.get(i).start();
                }
                System.out.println("数字=");
                System.out.println("数字");

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
//            wheel.setAdapter(new NumericWheelAdapter(1, 32, "%02d"));
            wheel.setAdapter(new ArrayWheelAdapter(new ArrayList() {{
                for (int j = 0; j < 32; j++) {

                    add(String.format("%02d", 32 - j));
                }
            }}));
            wheel.setCurrentItem(31);
            final int finalI = i;
            wheel.addOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
//                    if (index!=finalI)
//                    wheel.setCurrentItem(finalI);
                }
            });

            float scrollLength = -wheel.getItemHeight() * vgWheel.getChildCount() - new Random().nextInt(32) * wheel.getItemHeight();
            ValueAnimator animator = ValueAnimator.ofFloat(0, -1 * wheel.getItemHeight());
            final int finalI1 = i;
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(final ValueAnimator valueAnimator) {
//                    System.out.println("动画的值 "+ finalI1 +" : "+ valueAnimator.getAnimatedValue());
//                    wheel.scrollBy((Float) valueAnimator.getAnimatedValue());
                    Float animatedValue = (Float) valueAnimator.getAnimatedValue();
                    wheel.setTotalScrollY((int) (float) animatedValue);
                    wheel.invalidate();
                }
            });
            animator.setDuration(1000);
            animator.setFloatValues();
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
