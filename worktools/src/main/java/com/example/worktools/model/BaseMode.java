package com.example.worktools.model;


import com.example.worktools.rxjava.MultipartBuilder;
import com.example.worktools.rxjava.RxSchedulers;
import com.example.worktools.rxjava.observer.UploadFileRequestBody;
import com.example.worktools.rxjava.factory.RetroFactory;
import com.example.worktools.rxjava.observer.BaseObserver;
import com.example.worktools.rxjava.observer.AbstractFileUploadObserver;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observable;

/**
 *
 * @author xuhao
 * @date 2017/11/21
 */

public abstract class BaseMode implements IBaseMode{
    /**
     * 设置服务器域名
     * @return 服务器域名
     */
    protected abstract String getHost();
    private BaseObserver observer;
    @Override
    public void onDestroy() {
        if(observer!=null){
            observer.setDestory(true);
            observer.onComplete();
        }
    }
    private String getBaseUrl(String method){
        return getHost()+method;
    }
    @Override
    public void get(String method,CallBack callBack) {
        observer=new BaseObserver(callBack);
        Observable<Object> observable = RetroFactory.getInstance(getHost()).get(getBaseUrl(method));
        observable.compose(RxSchedulers.compose()).subscribe(observer);
    }
    @Override
    public void get(String method,HashMap<String,String> map, CallBack callBack) {
        observer=new BaseObserver(callBack);
        Observable<Object> observable = RetroFactory.getInstance(getHost()).get(getBaseUrl(method),map);
        observable.compose(RxSchedulers.compose()).subscribe(observer);
    }

    @Override
    public void post(String method,HashMap<String, String> map, CallBack callBack) {
        observer=new BaseObserver(callBack);
        Observable<Object> observable = RetroFactory.getInstance(getHost()).post(getBaseUrl(method),map);
        observable.compose(RxSchedulers.compose()).subscribe(observer);
    }
    @Override
    public void uploadFile(String method,String filePath, HashMap<String, String> map,ProgressCallBack callBack) {
        File mFile = new File(filePath);
        if (mFile.exists()) {
            AbstractFileUploadObserver fileUploadObserver= AbstractFileUploadObserver.build(callBack);
            UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(mFile, fileUploadObserver);
            Observable<Object> observable = RetroFactory.getInstance(getHost()).uploadFile(getBaseUrl(method),MultipartBuilder.fileToMultipartBody(mFile,map,uploadFileRequestBody));
            observable.compose(RxSchedulers.compose()).subscribe(fileUploadObserver);
        }else{
            callBack.onFail("文件不存在");
        }
    }
}
