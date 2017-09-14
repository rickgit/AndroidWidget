package edu.ptu.customview.viewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edu.ptu.customview.R;
import edu.ptu.customview.SlidingLayout;

public class ViewPagerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_viewpager);
        ViewPagerSlidingLayout sl = (ViewPagerSlidingLayout) findViewById(R.id.sl);

    }
}
