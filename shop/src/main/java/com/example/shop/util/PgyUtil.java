package com.example.shop.util;

import android.app.Activity;
import android.content.Context;

import com.example.shop.R;
import com.example.shop.activity.HomeActivity;
import com.example.worktools.dialog.TextDialog;
import com.example.worktools.util.AppVertionUtil;
import com.example.worktools.util.LogUtil;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.update.PgyUpdateManager;

public class PgyUtil {
    public static void unregister(){
        PgyCrashManager.unregister();
    }
    public static void register(){
        PgyCrashManager.register();
    }
    public static void Update(){
        new PgyUpdateManager.Builder() .register();
    }
}
