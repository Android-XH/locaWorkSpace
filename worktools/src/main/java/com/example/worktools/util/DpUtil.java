package com.example.worktools.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.annotation.NonNull;

public class DpUtil {
    private static DisplayMetrics getMetrics(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }
    public static int getWidthPixels(Context context){
        return getMetrics(context).widthPixels;
    }
    public static int getHeightPixels(Context context){
        return  getMetrics(context).heightPixels;
    }
}
