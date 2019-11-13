package com.example.worktools.model;


/**
 * Created by xuhao on 2017/11/21.
 */

public interface ProgressCallBack<T> extends CallBack<T>{
    void onProgress(int progress);
}
