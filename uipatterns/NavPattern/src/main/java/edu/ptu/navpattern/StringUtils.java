package edu.ptu.navpattern;

import android.text.TextUtils;

/**
 * Created by anshu.wang on 2017/7/6.
 */

class StringUtils {
    public static boolean isEmpty(String msg) {
        if (TextUtils.isEmpty(msg))
            return true;
        return false;
    }
}
