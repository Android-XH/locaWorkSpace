package com.example.worktools.rxjava;


import android.annotation.SuppressLint;

import com.example.worktools.model.CallBack;
import com.example.worktools.model.ProgressCallBack;
import com.example.worktools.rxjava.factory.RetroFactory;
import com.example.worktools.rxjava.observer.BaseObserver;
import com.example.worktools.rxjava.observer.AbstractFileUploadObserver;
import com.example.worktools.rxjava.observer.UploadFileRequestBody;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by xuhao on 2017/6/13.
 */

public class RetroSubscrube {
    public static RetroSubscrube retroSubscrube;
    public static synchronized RetroSubscrube getInstance() {
        if (retroSubscrube == null) {
            retroSubscrube = new RetroSubscrube();

        }
        return retroSubscrube;
    }


    /**
     * get获取数据
     * @param map
     * @param callBack
     */
    public void getSubscrube(String host,String method,Map<String, String> map,CallBack callBack) {
        BaseObserver observer=new BaseObserver(callBack);
        Observable<Object> observable = RetroFactory.getInstance(host).get(getBaseUrl(host,method),map);
        observable.compose(RxSchedulers.compose()).subscribe(observer);
    }
    /**
     * get数据
     * @param callBack
     */
    public void getSubscrube(String host,String method,CallBack callBack) {
        BaseObserver observer=new BaseObserver(callBack);
        Observable<Object> observable = RetroFactory.getInstance(host).get(getBaseUrl(host,method));
        observable.compose(RxSchedulers.compose()).subscribe(observer);
    }
    /**
     * post数据
     * @param map
     * @param callBack
     */
    public void postSubscrube(String host,String method,Map<String, String> map,CallBack callBack) {
        BaseObserver observer=new BaseObserver(callBack);
        Observable<Object> observable = RetroFactory.getInstance(host).post(getBaseUrl(host,method),map);
        observable.compose(RxSchedulers.compose()).subscribe(observer);
    }
    /**
     * 提交数据到服务器
     */
    @SuppressLint("CheckResult")
    public void postFile(String host,String method, File file, Map<String, String> map, final ProgressCallBack callBack) {
        AbstractFileUploadObserver fileUploadObserver = AbstractFileUploadObserver.build(callBack);
        UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(file, fileUploadObserver);
        Observable<Object> observable = RetroFactory.getInstance(host).uploadFile(getBaseUrl(host,method),MultipartBuilder.fileToMultipartBody(file,map,uploadFileRequestBody));
        observable.compose(RxSchedulers.compose()).subscribe(fileUploadObserver);
    }
    private String getBaseUrl(String host,String method){
        return host+method;
    }
}
