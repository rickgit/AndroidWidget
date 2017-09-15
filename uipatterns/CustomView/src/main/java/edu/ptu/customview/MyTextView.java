package edu.ptu.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import edu.ptu.customview.utils.LogUtils;

/**
 * Created by rick.wang on 2017/9/15.
 */

@SuppressLint("AppCompatCustomView")
public class MyTextView extends TextView {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            LogUtils.logMainInfo("按下按钮");
        }
        return super.onTouchEvent(event);
    }
}
