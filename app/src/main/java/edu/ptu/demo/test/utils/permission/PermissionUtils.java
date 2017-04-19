package edu.ptu.demo.test.utils.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by anshu.wang on 2017/2/17.
 */

public class PermissionUtils {
    public static void testPermission(Activity context) {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.CALL_PHONE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Toast.makeText(context, "请打开读短信的权限", Toast.LENGTH_LONG).show();
//                ActivityCompat.requestPermissions(context,
//                        new String[]{Manifest.permission.CALL_PHONE},
//                        100);
                callPhone(context);
            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.CALL_PHONE},
                        100);
//                ActivityCompat.requestPermissions(context,
//                        new String[]{Manifest.permission.CALL_PHONE},
//                        100);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            //
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + "10086");
            intent.setData(data);
            context.startActivity(intent);
//            callPhone(context);
        }

    }

    public static void callPhone(Activity context) {
//        Intent intent = new Intent(Intent.ACTION_CALL);
//        Uri data = Uri.parse("tel:" + "10086");
//        intent.setData(data).setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        context.startActivity(intent);
    }
}
