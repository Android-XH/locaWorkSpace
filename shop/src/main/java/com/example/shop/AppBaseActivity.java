package com.example.shop;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.example.shop.util.PgyUtil;
import com.example.worktools.baseview.BaseActivity;
import com.example.worktools.presenter.BasePresenter;
import com.pgyersdk.crash.PgyCrashManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class AppBaseActivity<P extends BasePresenter> extends BaseActivity<BasePresenter> implements View.OnClickListener {
    @Nullable
    @BindView(R.id.imb_back)
    ImageButton imbBack;
    @Nullable
    @BindView(R.id.tv_title)
    TextView tvTitle;

    protected abstract int setContentView();
    protected abstract void initAppData(Bundle bundle);
    protected abstract void initView();
    protected abstract P initPresenter();
    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.LEFT;
    }

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
        PgyUtil.register();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PgyUtil.unregister();
    }
}
