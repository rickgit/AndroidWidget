package edu.ptu.demo.test.progress;

import android.app.ProgressDialog;
import android.content.Context;

import edu.ptu.demo.R;

/**
 * Created by anshu.wang on 2016/11/29.
 */

public class CustomProgressBar {
    public static ProgressDialog createCustomProgressBar(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);//ProgressDialog.show(this, "", "", true, true);
//        dlg.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        return progressDialog;
    }
}
