package edu.ptu.navpattern.tablayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

/**RadioGroup 不能重复check,不适合刷新
 * Created by anshu.wang on 2017/6/28.
 */

public class Tablayout extends LinearLayout {
    public Tablayout(Context context) {
        super(context);
    }

    public Tablayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
