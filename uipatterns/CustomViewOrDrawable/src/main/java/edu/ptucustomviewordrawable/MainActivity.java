package edu.ptucustomviewordrawable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import edu.ptucustomviewordrawable.customDrawLine.RadioFrameLinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RadioFrameLinearLayout vg = (RadioFrameLinearLayout) findViewById(R.id.rg);
        for (int i = 0; i < vg.getChildCount(); i++) {
            final int finalI = i;
            vg.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    vg.setSelect(finalI);
                    vg.postInvalidate();
                    for (int j = 0; j < vg.getChildCount(); j++) {
                        vg.getChildAt(j).setBackgroundColor(0x00ffffff);
                    }
//                    v.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            });
        }
        final View progress = findViewById(R.id.progress);
        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                animation.setDuration(500);
                progress.startAnimation(animation);
            }
        });

//        final DrawNum drawNum = (DrawNum) findViewById(R.id.drawpanel);
//
//        for (int i = 0; i < drawNum.getChildCount(); i++) {
//            drawNum.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
//                        drawNum.isDraw = true;
//                        drawNum.postInvalidate();
//                    } else if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
//                        drawNum.isDraw = false;
//                        drawNum.postInvalidate();
//                    }
//                    return true;
//                }
//            });
//        }
    }
}
