package com.coco.screenadapt;


import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * author : Oliver
 * date   : 2018/12/14
 * desc   : 设备尺寸工具类
 *
 * <pre>
 *     getStatusBarHeight           获取状态栏高度
 *     getScreenHeight              获取屏幕高度
 *     getScreenWidth               获取屏幕宽度
 *     getScreenDensity             获取屏幕密度
 *     getScreenDensityDPI          获取屏幕密度
 *     getNavigationHeight          获取导航栏高度
 *     px2dp                        px转为dp
 *     dp2px                        dp转为px
 * </pre>
 */
public class DimensionUtils {

    public static final String TAG = "DimensionUtils";

    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = firstStatusBarHeight(context);
        if (statusBarHeight != 0) {
            return statusBarHeight;
        }
        return secondStatusBarHeight(context);
    }

    /**
     * 获取状态栏高度
     */
    public static int firstStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    private static int secondStatusBarHeight(Context context) {
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object dimenInstance = clazz.newInstance();
            Field statusBarHeightField = clazz.getField("status_bar_height");
            Object statusBarHeight = statusBarHeightField.get(dimenInstance);
            if (statusBarHeight != null) {
                return context.getResources().getDimensionPixelSize(Integer.parseInt(statusBarHeight.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获得屏幕高度
     *
     * @param context 上下文
     * @return 屏幕高度
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(outMetrics);
            return outMetrics.heightPixels;
        }
        Log.e(TAG, "获取WindowManager失败");
        return 0;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context 上下文
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(outMetrics);
            return outMetrics.widthPixels;
        }
        Log.e(TAG, "获取WindowManager失败");
        return 0;
    }

    /**
     * 获取屏幕密度
     *
     * @param context 上下文
     * @return 屏幕密度
     */
    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }


    /**
     * 获取屏幕密度(每寸像素：120/160/240/320)
     */
    public static float getScreenDensityDPI(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }


    /**
     * 获取导航栏高度
     */
    public static int getNavigationHeight(Context context) {
        int resourceId = 0;
        int rid = context.getResources().getIdentifier("config_showNavigationBar",
                "bool", "android");
        if (rid != 0) {
            resourceId = context.getResources().getIdentifier("navigation_bar_height",
                    "dimen", "android");
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static int px2dp(Context context, float px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }

    public static int dp2px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

}
