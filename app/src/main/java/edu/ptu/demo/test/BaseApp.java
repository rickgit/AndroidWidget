package edu.ptu.demo.test;

import android.app.Application;

/**
 * Created by anshu.wang on 2016/11/21.
 */

public class BaseApp extends Application {
    public static BaseApp mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mApp=this;
    }

}
