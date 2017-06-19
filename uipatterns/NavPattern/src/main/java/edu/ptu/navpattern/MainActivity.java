package edu.ptu.navpattern;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

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
                        .setCancelable(true)
                        .setDismissOnClick(true)
                        .setCornerRadius(20f)
                        .setItemText(new String[]{" sdf","asdf ","sadf "},new int[]{R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher})
                        .setGravity(Gravity.BOTTOM);

                builder.show();
            }
        });
    }
}
