package com.example.worktools.presenter;


import com.example.worktools.baseview.BaseView;
import com.example.worktools.model.BaseMode;

/**
 * Created by xuhao on 2017/11/21.
 */
public abstract class BasePresenter<M extends BaseMode, V extends BaseView> implements IBasePresenter {
    private M mMode;
    private V mView;
    private boolean isFirst = true;

    public abstract void getData();

    @Override
    public void onLoadData() {
        if(isFirst){
            getData();
            isFirst=false;
        }
    }

    public abstract M initMode();

    @Override
    public void onDestroy() {
        if (mMode != null) {
            mMode.onDestroy();
        }
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    @Override
    public void onStart(BaseView view) {
        mView = (V) view;
        mMode = initMode();
    }

    public M getMode() {
        if(mMode==null){
            mMode=initMode();
        }
        return mMode;
    }


    public V getView() {
        return mView;
    }
}