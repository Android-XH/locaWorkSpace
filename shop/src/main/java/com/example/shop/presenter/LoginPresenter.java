package com.example.shop.presenter;

import com.example.shop.api.param.UserParam;
import com.example.shop.bean.User;
import com.example.shop.mode.IUserMode;
import com.example.shop.mode.impl.UserMode;
import com.example.shop.viewImpl.ILoginAndRegisterView;
import com.example.worktools.model.CallBack;
import com.example.worktools.presenter.BasePresenter;

public class LoginPresenter extends BasePresenter<IUserMode, ILoginAndRegisterView> {
    @Override
    public void getData() {

    }

    @Override
    public IUserMode initMode() {
        return new UserMode();
    }
    public void login(UserParam userParam){
        getMode().login(userParam, new CallBack<User.Data>() {
            @Override
            public void onSuccess(User.Data data) {
                getView().onSuccess(data);
                getView().missLoading();
            }

            @Override
            public void onFail(String msg) {
                getView().onFail(msg);
                getView().missLoading();
            }
        });
    }
}
