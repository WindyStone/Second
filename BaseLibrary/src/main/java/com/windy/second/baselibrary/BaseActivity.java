package com.windy.second.baselibrary;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * Created by windy on 2017/6/21.
 */

public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTransparentStatusBar(getWindow());
        initWindow();
        initView();
        initEvent();
        initData();
    }

    protected void initWindow() {}
    protected void initView() {}
    protected void initEvent() {}
    protected void initData() {}

    /**
     * 设置透明顶部通知状态栏
     * <p>Android 4.4（含） 以上支持半透明<p/>
     * <p>Android 5.1（含） 以上支持全透明<p/>
     * <p>华为Android5.1（EMUI4.0） 只支持半透明<p/>
     *
     * @param window 用于设置的窗口Window
     */
    private void setTransparentStatusBar(Window window) {
        if (window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && getEMUIVersion() != 8) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private static final String EMUI_PROPERTIES = "ro.build.hw_emui_api_level";
    private static int sEMUIVersionName = -1;
    /**
     * 获得华为EMUI版本号
     *
     * @return 华为EMUI版本号，未取到时返回-1
     */
    private int getEMUIVersion() {
        if (sEMUIVersionName <= 0) {
            String EMUI_Version = getProperties(EMUI_PROPERTIES);
            if (!TextUtils.isEmpty(EMUI_Version)) {
                EMUI_Version = EMUI_Version.replaceAll("\\s+", "");
                if (isNumeric(EMUI_Version)) {
                    try {
                        sEMUIVersionName = Integer.parseInt(EMUI_Version);
                    } catch (NumberFormatException e) {

                    }
                }
            }
        }
        return sEMUIVersionName;
    }

    private String getProperties(String key) {
        String value = null;
        try {
            Method method = Build.class.getDeclaredMethod("getString", String.class);
            method.setAccessible(true);
            value = (String) method.invoke(new Build(), key);
        } catch (NoSuchMethodException e) {
        } catch (InvocationTargetException e) {
        } catch (IllegalAccessException e) {
        }
        return value;
    }

    private boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}
