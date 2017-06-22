package com.windy.second.tools;

import android.content.Context;

public class AppDelegate {
    private static Context mAppContext;
    private static boolean sIsMainProcess = false;

    public static Context getAppContext() {
        if (null == mAppContext) {
            throw new RuntimeException("must call method initContext");
        }
        return mAppContext;
    }

    public static boolean isMainProcess(){
        return sIsMainProcess;
    }

    /**
     * must execute this method when application onCreate
     * @param context
     */
    public static void initContext(Context context, boolean isMainProcess) {
        mAppContext = context;
        sIsMainProcess = isMainProcess;
    }
}
