package edu.ptu.demo.test.progress;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.v4.graphics.drawable.DrawableWrapper;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import edu.ptu.demo.R;

/**
 * Created by anshu.wang on 2016/11/29.
 */

public class CustomProgressBar {
    public static ProgressDialog createCustomProgressBar(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);//ProgressDialog.show(this, "", "", true, true);
//        dlg.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(56,56));
        frameLayout.setPadding(10,10,10,10);
        //setBackground
        float radius = 4;
        float[] outerR = new float[] {radius, radius, radius, radius, radius, radius, radius, radius};
        ShapeDrawable background = new ShapeDrawable(new RoundRectShape(outerR,null,null));
        background.getPaint().setColor(0xcc000000);
        background.getPaint().setPathEffect(new CornerPathEffect(10));
        frameLayout.setBackgroundDrawable(background);
        //
//        ProgressBar progressBar = new ProgressBar(context);
//        RotateDrawable rotateDrawable = new RotateDrawable();
//        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{rotateDrawable});
//        progressBar.setIndeterminateDrawable(layerDrawable);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(18, 18);
        params.gravity= Gravity.CENTER;
        progressDialog.setContentView(frameLayout);
        ImageView myImageView =  new ImageView(context);
        myImageView.setImageResource(R.drawable.progress_drawable);
        AnimationSet animSet = new AnimationSet(true);
        animSet.setInterpolator(new LinearInterpolator());
        final RotateAnimation animRotate = new RotateAnimation(0f,359f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animRotate.setDuration(1000);
        animRotate.setRepeatCount(Animation.INFINITE);
        animSet.addAnimation(animRotate);
        myImageView.startAnimation(animSet);
        myImageView.setLayoutParams(params);
        frameLayout.addView(myImageView);
        return progressDialog;
    }

}
