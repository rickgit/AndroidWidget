package edu.ptu.demo.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
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
        Glide.with(this).load(R.drawable.ic_launcher)
                .into((ImageView) findViewById(R.id.iv));
    }
}
