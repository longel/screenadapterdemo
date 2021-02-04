package com.coco.screenadapt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    /**
     * Pixel XL的设备物理尺寸
     * 1440 X 2560
     */
    private static final float PHYSICAL_SIZE_PIXEL_XL = 5.5f;

    /**
     * Pixel 2XL的设备物理尺寸
     * 1440 X 2880
     */
    private static final float PHYSICAL_SIZE_PIXEL_2XL = 5.99f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        DensityUtils.updateDensity(this, getApplication());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        if (displayMetrics.widthPixels == 1080) {
            setContentView(R.layout.activity_main_1080_1920);
        } else {
            setContentView(R.layout.activity_main);
        }

        // printWindowManagerDisplayMetricsParams();

        printAppResourceDisplayMetricsParams();

        printResourceDisplayMetricsParams();

        printBitmapInfo();

    }

    /**
     * 不同dpi的比例：
     * mdpi:hdpi:xhdpi:xxhdpi:xxxhdpi = 160dpi:240dpi:320dpi:480dpi:640dpi: = 1:1.5:2:3:4
     *
     * <pre>
     *     qrcode的大小：
     *      mdpi:16x16
     *      hdpi:24x24
     *      xhdpi:32x32
     *      xxhdpi:48x48
     *      xxxhdpi:64x64
     * </pre>
     * <pre>
     *     portrait的大小：
     *      mdpi:58x58
     *      xxxhdpi:232x232
     * </pre>
     */
    private void printBitmapInfo() {
        Log.d(TAG, "printBitmapInfo: qrcode:mdpi——hdpi——xhdpi——xxhdpi——xxxhdpi");
        TypedValue typedValue = new TypedValue();
        getResources().getValue(R.mipmap.qrcode, typedValue, true);
        Log.d(TAG, "printBitmapInfo: qrcode: " + typedValue.toString());
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.qrcode);
        Log.d(TAG, "printBitmapInfo: qrcode width: " + bitmap.getWidth());
        Log.d(TAG, "printBitmapInfo: qrcode height: " + bitmap.getHeight());

        Log.d(TAG, "printBitmapInfo: portrait:mdpi——xxhdpi");
        getResources().getValue(R.mipmap.portrait, typedValue, true);
        Log.d(TAG, "printBitmapInfo: portrait: " + typedValue.toString());
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.portrait);
        Log.d(TAG, "printBitmapInfo: portrait width: " + bitmap.getWidth());
        Log.d(TAG, "printBitmapInfo: portrait height: " + bitmap.getHeight());
        // 实际值：174*174  打印结果 203*203
        // 480 / 560 = 174 / x ==> x = (560 * 174) / 480 = 203
        // 也就是最终尺寸是以实际density进行加载计算的
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        Log.d(TAG, "printBitmapInfo: icon width: " + bitmap.getWidth());
        Log.d(TAG, "printBitmapInfo: icon height: " + bitmap.getHeight());
        // 20 x 20
        // drawable -> 55 x 55
        // drawable-nodpi -> 20 x 20
        // nodpi不会进行缩放
    }


    private void printResourceDisplayMetricsParams() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Log.d(TAG, "printResourceDisplayMetricsParams...displayMetrics.density: " + displayMetrics.density);
        Log.d(TAG, "printResourceDisplayMetricsParams...displayMetrics.densityDpi: " + displayMetrics.densityDpi);
        Log.d(TAG, "printResourceDisplayMetricsParams...displayMetrics.scaledDensity: " + displayMetrics.scaledDensity);
        Log.d(TAG, "width dp: " + DimensionUtils.px2dp(this, DimensionUtils.getScreenWidth(this)));
        Log.d(TAG, "height dp: " + DimensionUtils.px2dp(this, DimensionUtils.getScreenHeight(this)));
        Log.d(TAG, "printResourceDisplayMetricsParams...displayMetrics.toString: " + displayMetrics.toString());
        double manualDensity = Math.sqrt(Math.pow(displayMetrics.widthPixels, 2) + Math.pow(displayMetrics.heightPixels, 2)) / PHYSICAL_SIZE_PIXEL_XL;
        Log.d(TAG, "printResourceDisplayMetricsParams...屏幕密度: " + manualDensity);
        Log.d(TAG, "printWindowManagerDisplayMetricsParams...DENSITY_DEVICE_STABLE: " + DisplayMetrics.DENSITY_DEVICE_STABLE);

    }
    private void printAppResourceDisplayMetricsParams() {
        DisplayMetrics displayMetrics = getApplication().getResources().getDisplayMetrics();
        Log.d(TAG, "printAppResourceDisplayMetricsParams...displayMetrics.density: " + displayMetrics.density);
        Log.d(TAG, "printAppResourceDisplayMetricsParams...displayMetrics.densityDpi: " + displayMetrics.densityDpi);
        Log.d(TAG, "printAppResourceDisplayMetricsParams...displayMetrics.scaledDensity: " + displayMetrics.scaledDensity);
        Log.d(TAG, "width dp: " + DimensionUtils.px2dp(this, DimensionUtils.getScreenWidth(this)));
        Log.d(TAG, "height dp: " + DimensionUtils.px2dp(this, DimensionUtils.getScreenHeight(this)));
        Log.d(TAG, "printAppResourceDisplayMetricsParams...displayMetrics.toString: " + displayMetrics.toString());
        double manualDensity = Math.sqrt(Math.pow(displayMetrics.widthPixels, 2) + Math.pow(displayMetrics.heightPixels, 2)) / PHYSICAL_SIZE_PIXEL_XL;
        Log.d(TAG, "printAppResourceDisplayMetricsParams...屏幕密度: " + manualDensity);
        Log.d(TAG, "printAppResourceDisplayMetricsParams...DENSITY_DEVICE_STABLE: " + DisplayMetrics.DENSITY_DEVICE_STABLE);

    }


    private void printWindowManagerDisplayMetricsParams() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Log.d(TAG, "printWindowManagerDisplayMetricsParams...displayMetrics.density: " + displayMetrics.density);
        Log.d(TAG, "printWindowManagerDisplayMetricsParams...displayMetrics.densityDpi: " + displayMetrics.densityDpi);
        Log.d(TAG, "printWindowManagerDisplayMetricsParams...displayMetrics.scaledDensity: " + displayMetrics.scaledDensity);
        Log.d(TAG, "printWindowManagerDisplayMetricsParams...displayMetrics.toString: " + displayMetrics.toString());
        double manualDensity = Math.sqrt(Math.pow(displayMetrics.widthPixels, 2) + Math.pow(displayMetrics.heightPixels, 2)) / PHYSICAL_SIZE_PIXEL_XL;
        Log.d(TAG, "printWindowManagerDisplayMetricsParams...屏幕密度: " + manualDensity);
        Log.d(TAG, "printWindowManagerDisplayMetricsParams...DENSITY_DEVICE_STABLE: " + DisplayMetrics.DENSITY_DEVICE_STABLE);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 修改大字体回调
        printAppResourceDisplayMetricsParams();
        printResourceDisplayMetricsParams();
    }
}