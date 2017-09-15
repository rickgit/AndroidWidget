package edu.ptu.customview;

import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SlidingLayout sl = (SlidingLayout) findViewById(R.id.sl);
        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "This is a SnackBar 1!", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.tv1).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {   Toast.makeText(view.getContext(), "This is a  LongClickListener 1!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "This is a SnackBar 2!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
