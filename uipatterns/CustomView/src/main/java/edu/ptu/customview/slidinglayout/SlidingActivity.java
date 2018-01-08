package edu.ptu.customview.slidinglayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import edu.ptu.customview.R;
import edu.ptu.customview.utils.LogUtils;

public class SlidingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding);
        SlidingLayout sl = (SlidingLayout) findViewById(R.id.sl);
        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "This is a SnackBar 1!", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.tv1).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                LogUtils.logMainInfo("This is a SnackBar 2!");
                Toast.makeText(view.getContext(), "This is a  LongClickListener 1!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


    }
}
