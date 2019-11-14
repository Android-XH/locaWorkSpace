package com.example.worktools.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by xuhao on 2017/11/29.
 */

public class BaseStart {
    public void startActivity(Activity activity, Class<?> c){
        Intent intent = new Intent(activity, c);
        StartActivityOfActivity(activity, intent);
    }
    public void startActivity(Context context, Class<?> c){
        Intent intent = new Intent(context, c);
        StartActivityOfContext(context, intent);
    }
    public void startActivity(Activity activity, Bundle aBundle, Class<?> c){
        Intent intent = new Intent(activity,c);
        if (null != aBundle) {
            intent.putExtras(aBundle);
        }
        StartActivityOfActivity(activity,intent);
    }
    public void startActivity(Context context, Bundle aBundle, Class<?> c){
        Intent intent = new Intent(context,c);
        if (null != aBundle) {
            intent.putExtras(aBundle);
        }
        StartActivityOfContext(context,intent);
    }
    public void startActivityForResult(Activity activity, Bundle aBundle,Class<?> c,int code){
        Intent intent = new Intent(activity,c);
        if (null != aBundle) {
            intent.putExtras(aBundle);
        }
       StartActivityForResult(activity,intent,code);
    }
    private void StartActivityOfContext(Context context,Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    private void StartActivityOfActivity(Activity activity,Intent intent){
        activity.startActivity(intent);
    }
    private void StartActivityForResult(Activity activity,Intent intent,int code){
        activity.startActivityForResult(intent,code);
    }
}
