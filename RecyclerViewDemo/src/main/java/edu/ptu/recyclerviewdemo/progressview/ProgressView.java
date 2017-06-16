package edu.ptu.recyclerviewdemo.progressview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import edu.ptu.recyclerviewdemo.R;

/**
 * Created by anshu.wang on 2017/6/16.
 */
public class ProgressView extends ImageView {

    private Animation staggered;

    public ProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setAnimation(attrs);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAnimation(attrs);
    }

    public ProgressView(Context context) {
        super(context);
    }

    private void setAnimation(AttributeSet attrs) {
        setAnimation(12, 1000);
    }

    public void setAnimation(final int frameCount, final int duration) {
        Animation a = AnimationUtils.loadAnimation(getContext(), R.anim.progress_anim);
        a.setDuration(duration);
        a.setInterpolator(new Interpolator() {

            @Override
            public float getInterpolation(float input) {
                return (float)Math.floor(input*frameCount)/frameCount;
            }
        });
        startAnimation(a);

//        staggered = a;
    }
    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if( visibility == View.VISIBLE )
            startAnimation(staggered);
        else
            clearAnimation();

    }


}