package edu.ptucustomviewordrawable.customDrawLine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import edu.ptucustomviewordrawable.R;

/**
 * Created by rick.wang on 2017/8/1.
 */

public class DrawNum extends LinearLayout{

    public DrawNum(@NonNull Context context) {
        super(context);
    }

    public DrawNum(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawNum(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DrawNum(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public boolean isDraw=false;
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (isDraw)
        canvas.drawBitmap(((BitmapDrawable)getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap(),200,20,new Paint());
    }
}
