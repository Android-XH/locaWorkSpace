package com.example.shop.mode.impl;

import com.example.shop.api.Method;
import com.example.shop.api.common.ErrMessage;
import com.example.shop.api.param.UserParam;
import com.example.shop.bean.User;
import com.example.shop.mode.IUserMode;
import com.example.worktools.model.CallBack;
import com.example.worktools.util.GsonUtil;

public class UserMode extends BaseAppMode implements IUserMode {
    @Override
    public void login(UserParam userParam, CallBack<User.Data> callBack) {
        post(Method.LOGIN, getParam().login(userParam), new CallBack() {
            @Override
            public void onSuccess(Object o) {
                User user= GsonUtil.parseData(o,User.class);
                if(user!=null){
                    if(user.isSuccess()){
                        callBack.onSuccess(user.getData());
                    }else{
                        callBack.onFail(user.getMsg());
                    }
                }else{
                    callBack.onFail(ErrMessage.NULL_BEAN);
                }
            }

            @Override
            public void onFail(String msg) {
                callBack.onFail(msg);
            }
        });
    }

    @Override
    public void register(UserParam userParam, CallBack<User.Data> callBack) {
        post(Method.REGISTER, getParam().register(userParam), new CallBack() {
            @Override
            public void onSuccess(Object o) {
                User user= GsonUtil.parseData(o,User.class);
                if(user!=null){
                    if(user.isSuccess()){
                        callBack.onSuccess(user.getData());
                    }else{
                        callBack.onFail(user.getMsg());
                    }
                }else{
                    callBack.onFail(ErrMessage.NULL_BEAN);
                }
            }

            @Override
            public void onFail(String msg) {
                callBack.onFail(msg);
            }
        });
    }
}
