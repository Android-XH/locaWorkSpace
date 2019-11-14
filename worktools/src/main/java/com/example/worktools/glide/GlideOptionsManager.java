package com.example.worktools.glide;

import android.content.Context;

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.example.worktools.R;

/**
 * Created by xuhao on 2017/7/18.
 */

public class GlideOptionsManager {

    private static GlideOptionsManager instance;

    private RequestOptions mOptions,mBanner,circleOptions;

    private GlideOptionsManager() {

    }


    public static GlideOptionsManager getInstance() {
        if(instance == null) {
            synchronized (GlideOptionsManager.class) {
                if(instance == null) {
                    instance = new GlideOptionsManager();
                }
            }
        }
        return instance;
    }
    public RequestOptions getRequestOptions() {
        if(mOptions == null) {
            mOptions = new RequestOptions()
                    .centerCrop()
                    .priority(Priority.HIGH);
        }
        return mOptions;
    }
    public RequestOptions getCircleRequestOptions() {
        if(circleOptions == null) {
            circleOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.icon_default_avatar)
                    .error(R.drawable.icon_default_avatar)
                    .priority(Priority.HIGH)
                    .transform(new GlideCircleTransform());
        }
        return circleOptions;
    }
    public RequestOptions getCircleRequestOptions(Context context,int w, int color) {
        if(circleOptions == null) {
            circleOptions = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.icon_default_avatar)
                    .priority(Priority.HIGH)
                    .transform(new GlideCircleTransformWithBorder(context,w,color));
        }
        return circleOptions;
    }
    public RequestOptions getBannerOptions() {
        if(mBanner == null) {
            mBanner = new RequestOptions()
                    .centerCrop()
                    .priority(Priority.HIGH)
                    .transform(new GlideRoundTransform());
        }
        return mBanner;
    }
}
