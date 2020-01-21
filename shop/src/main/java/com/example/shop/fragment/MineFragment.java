package com.example.shop.fragment;

import android.os.Bundle;

import com.example.shop.R;
import com.example.shop.activity.MainActivity;
import com.example.shop.bean.User;
import com.example.shop.presenter.MinePresenter;
import com.example.shop.viewImpl.ILoginAndRegisterView;
import com.example.worktools.baseview.BaseFragment;

public class MineFragment extends BaseFragment<MinePresenter, MainActivity>implements ILoginAndRegisterView
{
    public static MineFragment getInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }
    @Override
    protected int getLayout() {
        return R.layout.fragment_mine_layout;
    }

    @Override
    protected MinePresenter initPresent() {
        return new MinePresenter();
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onSuccess(User.Data data) {

    }

    @Override
    public void onFail(String msg) {

    }
}
