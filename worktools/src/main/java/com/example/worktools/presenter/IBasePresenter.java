package com.example.worktools.presenter;


import com.example.worktools.baseview.BaseView;

/**
 * Created by xuhao on 2017/11/23.
 */

public interface IBasePresenter<D extends BaseView> {
    void onStart(D view);
    void onLoadData();
    void onDestroy();
}
