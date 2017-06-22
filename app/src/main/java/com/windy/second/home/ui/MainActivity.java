package com.windy.second.home.ui;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SlidingPaneLayout;

import com.windy.second.R;
import com.windy.second.baselibrary.BaseActivity;

import java.lang.reflect.Field;

public class MainActivity extends BaseActivity {
    private static final int OVER_HANG_SIZE = 402;

    @Override
    protected void initWindow() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        SlidingPaneLayout view = (SlidingPaneLayout) findViewById(R.id.spl_root);
        try {
            Field overhangSize = SlidingPaneLayout.class.getDeclaredField("mOverhangSize");
            overhangSize.setAccessible(true);
            overhangSize.set(view, OVER_HANG_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.left, new LeftFragment());
        transaction.replace(R.id.content, new ContentFragment());
        transaction.commitAllowingStateLoss();

        view.setParallaxDistance(150);
        view.setSliderFadeColor(getResources().getColor(R.color.a0aaaaaa));
    }
}