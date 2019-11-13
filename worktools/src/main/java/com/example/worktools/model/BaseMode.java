package com.example.worktools.model;


import com.example.worktools.rxjava.MultipartBuilder;
import com.example.worktools.rxjava.RetroSubscrube;
import com.example.worktools.rxjava.RxSchedulers;
import com.example.worktools.rxjava.observer.UploadFileRequestBody;
import com.example.worktools.rxjava.factory.RetroFactory;
import com.example.worktools.rxjava.observer.BaseObserver;
import com.example.worktools.rxjava.observer.FileUploadObserver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by xuhao on 2017/11/21.
 */

public abstract class BaseMode implements IBaseMode{
    protected abstract String getHost();
    BaseObserver observer;
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
        File _file = new File(filePath);
        if (_file.exists()) {
            FileUploadObserver fileUploadObserver=FileUploadObserver.build(callBack);
            UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(_file, fileUploadObserver);
            Observable<Object> observable = RetroFactory.getInstance(getHost()).uploadFile(getBaseUrl(method),MultipartBuilder.fileToMultipartBody(_file,map,uploadFileRequestBody));
            observable.compose(RxSchedulers.compose()).subscribe(fileUploadObserver);
        }else{
            callBack.onFail("文件不存在");
        }

    }
}
