package com.example.shop.activity;

import android.os.Bundle;
import android.view.View;

import com.example.shop.AppBaseActivity;
import com.example.shop.R;
import com.example.shop.api.param.UserParam;
import com.example.shop.bean.User;
import com.example.shop.presenter.LoginPresenter;
import com.example.shop.util.EditUtil;
import com.example.shop.util.share.SpUtil;
import com.example.shop.viewImpl.ILoginAndRegisterView;
import com.example.worktools.util.LogUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppBaseActivity<LoginPresenter> implements ILoginAndRegisterView {
    @BindView(R.id.et_user_name)
    TextInputEditText etUserName;
    @BindView(R.id.ti_user_name)
    TextInputLayout tiUserName;
    @BindView(R.id.et_user_pass)
    TextInputEditText etUserPass;
    @BindView(R.id.ti_user_pass)
    TextInputLayout tiUserPass;

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
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void onSuccess(User.Data data) {
        LogUtil.e(data.toString());
        SpUtil.getInstance().setUserMessage(data);
    }

    @Override
    public void onFail(String msg) {
        showToastMsg(msg);
    }

    @OnClick({R.id.btn_login_button, R.id.tv_create})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login_button:
                String phone = etUserName.getText().toString();
                if (EditUtil.isEmpty(tiUserName, phone, getString(R.string.err_name))) {
                    return;
                }
                String pass = etUserPass.getText().toString();
                if (EditUtil.isEmpty(tiUserPass, pass, getString(R.string.err_pwd))) {
                    return;
                }
                showLoading(getString(R.string.loading_login));
                UserParam userParam=new UserParam();
                userParam.setUserName(phone);
                userParam.setPassWord(pass);
                getPresenter().login(userParam);
                break;
            case R.id.tv_create:
                break;
        }
    }
}
