package com.example.shop.util;

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
