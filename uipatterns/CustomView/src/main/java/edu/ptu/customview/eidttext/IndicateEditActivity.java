package edu.ptu.customview.eidttext;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import edu.ptu.customview.R;

/**
 * @author anshu.wang
 * @version 1.0
 * @time 2017/9/30.
 */
public class IndicateEditActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicate_edit);
        ViewGroup vg = findViewById(R.id.vg);

    }
}