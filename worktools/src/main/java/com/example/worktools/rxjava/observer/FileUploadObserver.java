package com.example.worktools.rxjava.observer;

import com.example.worktools.model.CallBack;
import com.example.worktools.model.ProgressCallBack;
import com.example.worktools.util.Logx;

import io.reactivex.observers.DefaultObserver;
import okhttp3.ResponseBody;

/**
 * Created by xuhao on 2018/1/3.
 */

public abstract class FileUploadObserver<T> extends DefaultObserver<T> {
    private ProgressCallBack<T> callBack;

    public static FileUploadObserver<ResponseBody> build(ProgressCallBack callBack){
        FileUploadObserver fileUploadObserver=new FileUploadObserver(callBack) {
            @Override
            public void onComplete() {
                Logx.e("上传完成");
            }
        };
        return fileUploadObserver;
    }

    public FileUploadObserver(ProgressCallBack callBack) {
        this.callBack = callBack;
    }

    public ProgressCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(ProgressCallBack callBack) {
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

    // 监听进度的改变
    public void onProgressChange(long bytesWritten, long contentLength) {
        getCallBack().onProgress((int) (bytesWritten * 100 / contentLength));
    }
}