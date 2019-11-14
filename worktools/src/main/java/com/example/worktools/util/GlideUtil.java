package com.example.worktools.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.worktools.exception.RequestCommon;
import com.example.worktools.exception.UtilException;
import com.example.worktools.glide.GlideOptionsManager;

public class GlideUtil {
    @SuppressLint("StaticFieldLeak")
    private static  GlideUtil glideUtil;
    private RequestManager requestManager;
    private Context mContext;
    public static GlideUtil getInstance(){
        if(glideUtil==null){
            glideUtil=new GlideUtil();
        }
        return glideUtil;
    }
    public void init(Context context){
        requestManager=Glide.with(context);
        this.mContext=context;
    }
    /**
     * 加载网络图片
     * @param imgUrl
     * @param options
     * @param imageView
     */
    private void loadImage(Object imgUrl, RequestOptions options, ImageView imageView){
        if(requestManager!=null){
            requestManager.load(imgUrl).apply(options).into(imageView);
        }else{
//           throw new UtilException(RequestCommon.NOT_INIT);
            LogUtil.errCommon(RequestCommon.NOT_INIT);
        }
    }

    /**
     * 加载普通图片
     * @param imgUrl
     * @param imageView
     */
    public void loadImg(Object imgUrl,ImageView imageView){
        loadImage(imgUrl,GlideOptionsManager.getInstance().getRequestOptions(),imageView);
    }

    /**
     * 加载圆图
     * @param imgUrl
     * @param imageView
     * @throws UtilException
     */
    public void loadCircleImg(Object imgUrl,ImageView imageView){
        loadImage(imgUrl,GlideOptionsManager.getInstance().getCircleRequestOptions(),imageView);
    }
    /**
     * 加载带边框圆图
     * @param imgUrl
     * @param imageView
     * @throws UtilException
     */
    public void loadCircleWithLine(Object imgUrl,ImageView imageView,int width,int color){
        loadImage(imgUrl, GlideOptionsManager.getInstance().getCircleRequestOptions(mContext,width,color),imageView);
    }
    /**
     * 加载广告图
     * @param imgUrl
     * @param imageView
     */
    public void loadBanner(Object imgUrl,ImageView imageView){
        loadImage(imgUrl,GlideOptionsManager.getInstance().getBannerOptions(),imageView);
    }

    public void loadGifImage(Object imgUrl,ImageView imageView){
        requestManager.load(imgUrl).into(imageView);
    }

}
