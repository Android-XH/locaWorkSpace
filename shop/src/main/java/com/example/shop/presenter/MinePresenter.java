package com.example.shop.presenter;

import com.example.shop.bean.User;
import com.example.shop.mode.IUserMode;
import com.example.shop.mode.impl.UserMode;
import com.example.shop.viewImpl.ILoginAndRegisterView;
import com.example.worktools.presenter.BasePresenter;

public class MinePresenter extends BasePresenter<IUserMode, ILoginAndRegisterView> {
    @Override
    public void getData() {

    }

    @Override
    public IUserMode initMode() {
        return new UserMode();
    }
}
