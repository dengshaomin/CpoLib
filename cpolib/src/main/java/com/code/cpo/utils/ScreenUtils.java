package com.code.cpo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by balacne on 2018/5/4.
 * Desc:
 */
public class ScreenUtils {

    private static int realWidth = 0;

    private static int realHeight = 0;

    private static int width = 0;

    private static int height = 0;

    public static int getScreenWidth(Context context) {
        boolean island = isLandscape(context);
        if (width > 0) {
            return island ? height : width;
        }
        DisplayMetrics metric = Resources.getSystem().getDisplayMetrics();
        if (metric != null) {
            width = island ? metric.heightPixels : metric.widthPixels;
            height = island ? metric.widthPixels : metric.heightPixels;
        }
        return island ? height : width;
    }

    /**
     * 获得屏幕高度
     */
    public static int getRealScreenWidth(Activity context) {
        boolean island = isLandscape(context);
        if (realWidth > 0) {
            return island ? realHeight : realWidth;
        }
/**
 * getRealMetrics - 屏幕的原始尺寸，即包含状态栏。
 * version >= 4.2.2
 */
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            realWidth = island ? metrics.heightPixels : metrics.widthPixels;
            realHeight = island ? metrics.widthPixels : metrics.heightPixels;
        } else {
            try {
                WindowManager wm = (WindowManager) context
                        .getSystemService(Context.WINDOW_SERVICE);
                DisplayMetrics outMetrics = new DisplayMetrics();
                wm.getDefaultDisplay().getMetrics(outMetrics);
                realWidth = island ? outMetrics.heightPixels : outMetrics.widthPixels;
                realHeight = island ? outMetrics.widthPixels : outMetrics.heightPixels;
            } catch (Exception e) {

            }
        }
        return island ? realHeight : realWidth;
    }

    public static int getScreenHeight(Context context) {
        boolean island = isLandscape(context);
        if (height > 0) {
            return island ? width : height;
        }
        DisplayMetrics metric = Resources.getSystem().getDisplayMetrics();
        if (metric != null) {
            width = island ? metric.heightPixels : metric.widthPixels;
            height = island ? metric.widthPixels : metric.heightPixels;
        }
        return island ? width : height;
    }

    public static int getRealScreenHeight(Activity context) {
        boolean island = isLandscape(context);
        if (realHeight > 0) {
            return island ? realWidth : realHeight;
        }
        /**
         * getRealMetrics - 屏幕的原始尺寸，即包含状态栏。
         * version >= 4.2.2
         */
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            realWidth = island ? metrics.heightPixels : metrics.widthPixels;
            realHeight = island ? metrics.widthPixels : metrics.heightPixels;
        } else {
            try {
                WindowManager wm = (WindowManager) context
                        .getSystemService(Context.WINDOW_SERVICE);
                DisplayMetrics outMetrics = new DisplayMetrics();
                wm.getDefaultDisplay().getMetrics(outMetrics);
                realWidth = island ? outMetrics.heightPixels : outMetrics.widthPixels;
                realHeight = island ? outMetrics.widthPixels : outMetrics.heightPixels;
            } catch (Exception e) {

            }
        }
        return island ? realWidth : realHeight;
    }


    public static boolean isLandscape(Context context) {

        try {
            return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        } catch (Exception e) {
            return false;
        }

    }
}
