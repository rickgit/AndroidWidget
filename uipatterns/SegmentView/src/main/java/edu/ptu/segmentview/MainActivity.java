
package edu.ptu.segmentview;

import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.Menu;
import android.widget.RadioButton;
import android.widget.Toast;



public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_radio);

        SegmentRadioGroup rGroup = (SegmentRadioGroup) findViewById(R.id.rg);
        // 设置纵向排列
        rGroup.setOrientation(SegmentRadioGroup.HORIZONTAL);
        // 设置tabs
        rGroup.setTabs(new Spanned[] {
                new SpannableString("主页"),  new SpannableString("朋友圈"),  new SpannableString("搜索")
        });
        // 设置点击事件
        rGroup.setOnItemCheckedListener(new SegmentRadioGroup.OnItemCheckedListener() {

            @Override
            public void onCheck(RadioButton button, int position, String title) {
                Toast.makeText(getApplicationContext(),
                        "checked id = " + position + ", title = " + title,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
