package com.example.worktools.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
public class CheckApkExist {
    public static String taobaoPkgName = "com.taobao.taobao";

    private static boolean checkApkExist(Context context, String packageName){
        if (TextUtils.isEmpty(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            LogUtil.e(info.toString());
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.e(e.toString());
            return false;
        }
    }

    public static boolean checkTaoBao(Context context){
        return checkApkExist(context, taobaoPkgName);
    }
}