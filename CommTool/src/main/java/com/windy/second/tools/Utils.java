package com.windy.second.tools;

public class Utils {

    private static long clickTime;

    public static boolean canClick() {
        if (System.currentTimeMillis() - clickTime > 500) {
            clickTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public static boolean canClick(long time) {
        if (System.currentTimeMillis() - clickTime > time) {
            clickTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }


}
