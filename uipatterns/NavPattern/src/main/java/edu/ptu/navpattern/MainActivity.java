package edu.ptu.navpattern;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import edu.ptu.navpattern.tooltip.OnItemClickListener;
import edu.ptu.navpattern.tooltip.Tooltip;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tooltip.Builder builder = new Tooltip.Builder(v)
                        .setItemText(new String[]{" s33333333333333333333333df", "asdf ", "sadf "}, new int[]{R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher})
//                        .setItemText(new String[]{" s33333333333333333333333df", "asdf ", "sadf "})
                        .setmOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Toast.makeText(MainActivity.this, "  " + which, Toast.LENGTH_LONG).show();
                            }
                        });

                builder.show();
            }
        });
    }
}
