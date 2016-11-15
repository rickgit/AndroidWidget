package edu.ptu.demo.test;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import edu.ptu.demo.R;

/**
 * Created by anshu.wang on 2016/11/12.
 */

public class GlideActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        View iv = findViewById(R.id.iv);
        View bg = findViewById(R.id.bg);
        Glide.with(this).load(R.drawable.gs)
                .into((ImageView) findViewById(R.id.iv));

        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.gs);
        Palette generate = Palette.from(drawable.getBitmap()).generate();
        bg.setBackgroundColor(generate.getDarkMutedSwatch().getRgb());
    }
}
