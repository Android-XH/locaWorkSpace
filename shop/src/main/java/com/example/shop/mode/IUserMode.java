package com.example.shop.mode;

import com.example.shop.api.param.UserParam;
import com.example.worktools.model.CallBack;
import com.example.worktools.model.IBaseMode;

public interface IUserMode extends IBaseMode {
    void login(UserParam userParam, CallBack callBack);
}
