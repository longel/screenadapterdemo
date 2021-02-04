package com.coco.screenadapt;

import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.annotation.NonNull;

public class DensityUtils {
    // 以Mi Note 3为基准
    // 698 * 393
    private static final int BASE_WIDTH = 393;

    public static void updateDensity(@NonNull Activity activity, @NonNull Application application) {

        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        // targetDensity：表示将当前设备的宽修改为基准设计图的宽之后，density的最新值
        float targetDensity = displayMetrics.widthPixels * 1.0f / BASE_WIDTH;
        // targetDensityDpi：表示将当前设备的宽修改为基准设计图的宽之后，density的最新dpi
        int targetDensityDpi = (int) (DisplayMetrics.DENSITY_DEFAULT * targetDensity);

        // 分别赋值给回去Application和Activity的DisplayMetrics对象，这样在TypedValue.applyDimension()
        // 中进行单位换算时，就可以得到合适的换算值
        displayMetrics.density = displayMetrics.scaledDensity = targetDensity;
        displayMetrics.densityDpi = targetDensityDpi;

        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        metrics.density = displayMetrics.scaledDensity = targetDensity;
        metrics.densityDpi = targetDensityDpi;
    }
}
