package com.windy.second.tools;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Method;

/**
 * Created by kieth on 3/15.
 */
public final class DeviceTool {
    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
    private static final String TAG = DeviceTool.class.getSimpleName();

    private static Context mAppContext;

    static {
        mAppContext = AppDelegate.getAppContext();
    }
    /**********************************单位转化************************************/

    /**
     * dp转px
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, mAppContext.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, mAppContext.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     */
    public static float px2dp(float pxVal) {
        final float scale = mAppContext.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     */
    public static float px2sp(float pxVal) {
        return (pxVal / mAppContext.getResources().getDisplayMetrics().scaledDensity);
    }

    /**********************************SDCard************************************/

    /**
     * 判断SDCard是否可用
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

    }

    /**
     * 获取SD卡路径
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    /**********************************Screen************************************/

    private static int sScreenWidth;
    private static int sScreenHeight;

    /**
     * 获得屏幕宽度（随屏幕旋转改变）
     * 如果获取失败，则返回720
     *
     * @return 屏幕高度，或720
     */
    public static int getScreenWidth() {
        if (sScreenWidth != 0) {
            return sScreenWidth;
        }
        WindowManager wm = (WindowManager) mAppContext.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null && wm.getDefaultDisplay() != null) {
            DisplayMetrics outMetrics = new DisplayMetrics();
            Display display = wm.getDefaultDisplay();
            display.getMetrics(outMetrics);
            switch (display.getRotation()) {
                case Surface.ROTATION_0:
                case Surface.ROTATION_180://fall through
                    sScreenWidth = outMetrics.widthPixels;
                    sScreenHeight = outMetrics.heightPixels;
                    break;
                case Surface.ROTATION_90:
                case Surface.ROTATION_270://fall through
                    sScreenWidth = outMetrics.heightPixels;
                    sScreenHeight = outMetrics.widthPixels;
                    break;
            }
            return sScreenWidth;
        } else {
            return 720;
        }
    }

    /**
     * 获得屏幕高度
     * 如果获取失败，则返回1080
     *
     * @return 屏幕高度，或1080
     */
    public static int getScreenHeight() {
        if (sScreenHeight != 0) {
            return sScreenHeight;
        }
        WindowManager wm = (WindowManager) mAppContext.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null && wm.getDefaultDisplay() != null) {
            DisplayMetrics outMetrics = new DisplayMetrics();
            Display display = wm.getDefaultDisplay();
            display.getMetrics(outMetrics);
            switch (display.getRotation()) {
                case Surface.ROTATION_0:
                case Surface.ROTATION_180://fall through
                    sScreenHeight = outMetrics.heightPixels;
                    sScreenWidth = outMetrics.widthPixels;
                    break;
                case Surface.ROTATION_90:
                case Surface.ROTATION_270://fall through
                    sScreenHeight = outMetrics.widthPixels;
                    sScreenWidth = outMetrics.heightPixels;
                    break;
            }
            return sScreenHeight;
        } else {
            return 1080;
        }
    }


    /**
     * 计算状态栏高度高度 从 MJTitleBar 抽取而来 不用反射
     *
     * @return
     */
    public static int getStatusBarHeight() {
        return getInternalDimensionSize(Resources.getSystem(), STATUS_BAR_HEIGHT_RES_NAME);
    }

    private static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取屏幕密度
     */
    public static float getDensity() {
        return mAppContext.getResources().getDisplayMetrics().density;
    }

    public static int getDensityDpi() {
        return mAppContext.getResources().getDisplayMetrics().densityDpi;
    }

    public static boolean isLargeScreenAndLowDensity() {
        boolean isMate7 = false;
        if (Build.DEVICE.equals("hwmt7") || Build.DEVICE.equals("mx4pro")) isMate7 = true;
        return isMate7;
    }

    public static Resources getResources() {
        return mAppContext.getResources();
    }

    public static float getActionBarHeight() {
        float h;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
            TypedArray actionbarSizeTypedArray = mAppContext.obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
            h = actionbarSizeTypedArray.getDimension(0, 48 * DeviceTool.getDensity());
            actionbarSizeTypedArray.recycle();
        } else {
            h = 48 * DeviceTool.getDensity();
        }
        return h;
    }

    public static float getDeminVal(int redId) {
        return mAppContext.getResources().getDimension(redId);
    }

    public static String[] getStringArray(int redId) {
        return mAppContext.getResources().getStringArray(redId);
    }

    public static int getNavigationBarHeight() {
        Resources resources = mAppContext.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        //获取NavigationBar的高度
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    /**
     * 是否有虚拟键
     */
    @SuppressLint("NewApi")
    public static boolean hasNavBar() {
        Resources res = mAppContext.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            String sNavBarOverride = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                try {
                    Class c = Class.forName("android.os.SystemProperties");
                    Method m = c.getDeclaredMethod("get", String.class);
                    m.setAccessible(true);
                    sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
                } catch (Throwable e) {
                    sNavBarOverride = "";
                }
            }
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag (see static block)
            if ("1".equals(sNavBarOverride)) hasNav = false;
            else if ("0".equals(sNavBarOverride)) hasNav = true;
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(mAppContext).hasPermanentMenuKey();
        }
    }

    /**
     * 判断设备当前是否亮屏状态
     *
     * @return true 代表亮屏
     */
    public static boolean isDeviceScreenOn() {
        boolean screen = true;
        try {
            if (Build.VERSION.SDK_INT > 20) {
                DisplayManager dm = (DisplayManager) mAppContext.getSystemService(Context.DISPLAY_SERVICE);
                for (Display display : dm.getDisplays()) {
                    if (Display.STATE_OFF == display.getState()) {
                        screen = false;
                    }
                }
            } else {
                PowerManager pm = (PowerManager) mAppContext.getSystemService(Context.POWER_SERVICE);
                screen = pm.isScreenOn();
            }
        } catch (Exception e) {

        }
        return screen;
    }

    /**********************************NetWork************************************/

    /**
     * 判断网络是否连接
     */
    public static boolean isConnected() {
//        fix  https://bugly.qq.com/v2/crash/apps/900010804/issues/12557545?pid=1
        try {
            ConnectivityManager connectivity = (ConnectivityManager) mAppContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null != connectivity) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (null != info && info.isConnected()) {
                    return info.getState() == NetworkInfo.State.CONNECTED && info.isAvailable();
                }
            }
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi() {
        try {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(mAppContext, Manifest.permission.ACCESS_NETWORK_STATE);
            if (checkCallPhonePermission == PackageManager.PERMISSION_GRANTED) {
                ConnectivityManager cm = (ConnectivityManager) mAppContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                return !(cm == null || cm.getActiveNetworkInfo() == null) && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    public static String getStringById(@StringRes int resId) {
        return mAppContext.getResources().getString(resId);
    }

    public static String getStringById(@StringRes int resId, Object... formatArgs) {
        return mAppContext.getResources().getString(resId, formatArgs);
    }

    public static Drawable getDrawableByID(int resId) {
        return mAppContext.getResources().getDrawable(resId);
    }

    @Deprecated
    public static Bitmap getBitmapById(int resId) {
        return getBitmapById(resId, null);
    }

    @Deprecated
    public static Bitmap getBitmapById(int resId, BitmapFactory.Options option) {
        InputStream is;
        try {
            is = mAppContext.getResources().openRawResource(resId);
        } catch (Exception e) {
            return null;
        }
        return BitmapFactory.decodeStream(is, null, option);
    }

    public static int getColorById(Context ctx, int resId) {
        return ctx.getResources().getColor(resId);
    }

    public static int getColorById(int resId) {
        return mAppContext.getResources().getColor(resId);
    }

    /**
     * 获取CPU序列号
     * 有的系统下获取不到序列号，结果都是"0000000000000000"
     *
     * @return
     */
    public static String getCPUSerial() {
        String str = "", strCPU = "", cpuAddress = "0000000000000000";
        try {
            Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream(), "UTF-8");
            LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    if (str.indexOf("Serial") > -1) {
                        strCPU = str.substring(str.indexOf(":") + 1, str.length());
                        cpuAddress = strCPU.trim();
                        break;
                    }
                } else {
                    break;
                }
            }
            input.close();
            ir.close();
        } catch (Exception ex) {
        }
        return cpuAddress;
    }

    /**
     * 显示输入法
     *
     * @param view
     */
    public static void showKeyboard(View view) {
        InputMethodManager imm =
                (InputMethodManager) mAppContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    /**
     * 隐藏输入法
     *
     * @param view
     */
    public static void hideKeyboard(View view) {
        InputMethodManager imm =
                (InputMethodManager) mAppContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
