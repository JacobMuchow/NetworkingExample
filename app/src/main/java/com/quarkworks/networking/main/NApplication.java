package com.quarkworks.networking.main;

import android.app.Application;

/**
 * @author jacobamuchow@gmail.com (Jacob Muchow)
 */
public class NApplication extends Application {

    private static NApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static NApplication getInstance() {
        return application;
    }
}
