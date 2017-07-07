package com.roshane.tickets.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by roshanedesilva on 7/7/17.
 */

public class RootApplication extends Application {
    private static RootApplication mInstance;

    /**
     * Singleton method for Root Application
     *
     * @return RootApplication
     */
    public static synchronized RootApplication getInstance() {
        return mInstance;
    }

    /**
     * Get the Common Application Context
     *
     * @return Context
     */
    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

    }
}


