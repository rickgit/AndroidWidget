package edu.ptu.demo.test.roundindicator;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import edu.ptu.demo.R;

/**
 * Created by anshu.wang on 2017/4/22.
 */

public class CustomDialog {
    public static void  createCustomDialog(Activity context){
        Dialog dialog = new Dialog(context, R.style.control_custom_popwindow);
        dialog.setContentView(R.layout.item_wrap);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setBackgroundDrawable(new ColorDrawable());
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.RIGHT | Gravity.TOP);
//        lp.alpha = 0f;
        // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
        // dialog.onWindowAttributesChanged(lp);
        dialogWindow.setAttributes(lp);
        dialog.show();
    }
}
