package com.example.shop.viewImpl;

import com.example.shop.bean.User;
import com.example.worktools.baseview.BaseView;

public interface ILoginAndRegisterView extends BaseView {
    void onSuccess(User.Data data);
    void onFail(String msg);
}
