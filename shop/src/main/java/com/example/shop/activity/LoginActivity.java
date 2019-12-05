package com.example.shop.activity;

import android.os.Bundle;

import com.example.shop.AppBaseActivity;
import com.example.shop.R;
import com.example.worktools.baseview.BaseActivity;
import com.example.worktools.presenter.BasePresenter;

public class LoginActivity extends AppBaseActivity {
    @Override
    protected int setContentView() {
        return R.layout.activity_login_layout
                ;
    }

    @Override
    protected void initAppData(Bundle bundle) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
}
