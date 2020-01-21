package com.example.worktools.rxjava.observer;

import com.example.worktools.model.ProgressCallBack;
import com.example.worktools.util.LogUtil;

import io.reactivex.observers.DefaultObserver;

/**
 *
 * @author xuhao
 * @date 2018/1/3
 */

public abstract class AbstractFileUploadObserver<T> extends DefaultObserver<T> {
    private ProgressCallBack<T> callBack;

    public static AbstractFileUploadObserver build(ProgressCallBack callBack){
        return new AbstractFileUploadObserver(callBack){
            @Override
            public void onComplete() {
                LogUtil.e("上传完成");
            }
        };
    }

    AbstractFileUploadObserver(ProgressCallBack<T> callBack) {
        this.callBack = callBack;
    }

    public ProgressCallBack<T> getCallBack() {
        return callBack;
    }

    public void setCallBack(ProgressCallBack<T> callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onNext(T t) {
        getCallBack().onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        getCallBack().onFail(e.getMessage());
    }


    /**
     * 监听进度的改变
     * @param bytesWritten 已上传大小
     * @param contentLength 文件大小
     */
    void onProgressChange(long bytesWritten, long contentLength) {
        getCallBack().onProgress((int) (bytesWritten * 100 / contentLength));
    }
}