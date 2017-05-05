package edu.ptu.demo.test.gridview;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import edu.ptu.demo.R;
import edu.ptu.demo.test.roundindicator.CustomDialog;
import edu.ptu.demo.test.roundindicator.RoundIndicatorView;

/**
 * Created by anshu.wang on 2017/4/18.
 */

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewGroup vg = (ViewGroup) findViewById(R.id.activity_main);
        vg.removeAllViews();
        final RoundIndicatorView child = new RoundIndicatorView(this);
//        ValueAnimator mAnimator = ValueAnimator.ofInt(0, 380);
//        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Integer animatedValue = (Integer) animation.getAnimatedValue();
//                child.setRadio((animatedValue)+"%");
//            }
//        });
//        mAnimator.setDuration(62000);
//        mAnimator.setRepeatCount(3);
//        mAnimator.setRepeatMode(ValueAnimator.REVERSE);
//        //5.为ValueAnimator设置目标对象并开始执行动画
//        mAnimator.start();


        child.setRadio((200)+"%");
        vg.addView(child, new RelativeLayout.LayoutParams(560, 560));

//        CustomDialog.createCustomDialog(this);
    }
}
