package com.windy.second;

import android.app.ActivityManager;
import android.content.Context;

import com.windy.second.tools.AppDelegate;

import java.util.List;

/**
 * Created by windy on 2017/6/20.
 */

public class Application extends android.app.Application {
    public static Context sContext;
    static String sProcessName;
    private String sPackageName;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = base;
        sProcessName = getCurProcessName(base);
        sPackageName = getPackageName();
        AppDelegate.initContext(base, sPackageName.equals(sProcessName));
    }

    private String getCurProcessName(Context context) {
        final int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> processInfos = mActivityManager.getRunningAppProcesses();
        if (processInfos != null && !processInfos.isEmpty()) {
            final int size = processInfos.size();
            for (int i = 0; i < size; i++) {
                ActivityManager.RunningAppProcessInfo appProcess = processInfos.get(i);
                if (appProcess.pid == pid) {
                    return appProcess.processName;
                }
            }
        }
        return "";
    }
}
