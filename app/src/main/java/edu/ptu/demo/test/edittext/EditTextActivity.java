package edu.ptu.demo.test.edittext;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import edu.ptu.demo.R;


public class EditTextActivity extends FragmentActivity {

    private EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        et1 = (EditText) findViewById(R.id.et1);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et1.requestFocus();
//                et1.getSelectionEnd();
            }
        });
    }
}
