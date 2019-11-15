package com.example.shop;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.example.worktools.baseview.BaseActivity;
import com.example.worktools.presenter.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class AppBaseActivity<P extends BasePresenter> extends BaseActivity<BasePresenter> implements View.OnClickListener {
    @BindView(R.id.imb_back)
    ImageButton imbBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    protected abstract int setContentView();
    protected abstract void initAppData(Bundle bundle);
    protected abstract void initView();
    protected abstract P initPresenter();


    @Override
    protected int getLayout() {
        return setContentView();
    }

    @Override
    protected BasePresenter initPresent() {
        return initPresenter();
    }

    @Override
    protected void initData(Bundle bundle) {
        initAppData(bundle);
        initView();
    }
    public P getPresenter(){
        return (P) getBasePresenter();
    }
    public void setTitle(String title){
        if(tvTitle!=null){
            tvTitle.setText(title);
        }
    }
    public void setTitle(int resID){
        if(tvTitle!=null){
            tvTitle.setText(resID);
        }
    }
    public void showBackImb(){
        if(imbBack!=null){
            imbBack.setVisibility(View.VISIBLE);
            imbBack.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imb_back:
                finish();
                break;
        }
    }
}