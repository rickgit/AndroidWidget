package edu.ptu.demo.test.gridview;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import edu.ptu.demo.R;
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
        child.postDelayed(new Runnable() {
            @Override
            public void run() {
                child.setValue("23%");
            }
        },100);

        child.postDelayed(new Runnable() {
            @Override
            public void run() {
                child.setValue("130%");
            }
        },5000);
        child.postDelayed(new Runnable() {
            @Override
            public void run() {
                child.setValue("280%");
            }
        },15000);
        vg.addView(child,new RelativeLayout.LayoutParams(240,120));
    }
}
