package com.example.worktools.util;


import android.util.Log;

import com.example.worktools.BuildConfig;

/**
 * Created by xuhao on 2017/8/15.
 */

public class Logx {
    private static boolean IS_DEBUG;
    private static final String TAG="LOG_TAG";
    public static void e(String message){
        if(BuildConfig.DEBUG){
            Log.e(TAG,message);
        }
    }
    public static void e(String TAG,String message){
        if(BuildConfig.DEBUG){
            Log.e(TAG,message);
        }
    }
    public static void d(String o){
        if(BuildConfig.DEBUG){
            Log.d(TAG,o);
        }
    }
    public static void i(String o){
        if(BuildConfig.DEBUG){
            Log.i(TAG,o);
        }
    }
}
