package edu.ptucustomviewordrawable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

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

    }
}
