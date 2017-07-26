package edu.ptu.viewpager.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import edu.ptu.viewpager.R;
import edu.ptu.viewpager.fragment.Simp1Fragment;

public class FragmentReplaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_replace);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, Simp1Fragment.getInstance(0),null).commit();
    }
}
