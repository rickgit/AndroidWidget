package edu.ptucustomviewordrawable;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by anshu.wang on 2017/7/6.
 */

public class DeviceUtils {
    public static int[] getScreenSize(View view){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) view.getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return new int[]{width,height};
    }
}
