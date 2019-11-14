package com.example.worktools.rxjava.observer;


import com.example.worktools.model.CallBack;
import com.example.worktools.util.LogUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by xuhao on 2017/6/12.
 */

public class BaseObserver implements Observer<Object> {
    private Disposable mDisposable;
    private CallBack callBack;
    private boolean isDestory;
    public BaseObserver(CallBack callBack){
        this.callBack=callBack;
    }
    public void setDestory(boolean isDestory){
        this.isDestory=isDestory;
        this.onComplete();
    }
    @Override
    public void onSubscribe(Disposable d) {
        LogUtil.e("开始获取数据");
        this.mDisposable = d;
    }

    @Override
    public void onNext(Object value) {
        LogUtil.e("获取数据成功");
        if(!isDestory){
            callBack.onSuccess(value);
        }
    }
    @Override
    public void onError(Throwable e) {
        LogUtil.e("获取数据失败"+e.getMessage());
        if(!isDestory){
            callBack.onFail(e.getMessage());
        }

    }
    @Override
    public void onComplete() {
        LogUtil.e("获取数据完成,销毁线程");
        if(mDisposable!=null){
            mDisposable.dispose();
        }
    }

}
