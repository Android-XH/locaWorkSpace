package com.example.worktools.baseview;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.worktools.R;
import com.example.worktools.dialog.CustomProgressDialog;
import com.example.worktools.presenter.BasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xuhao on 2017/2/15.
 */

public abstract class BaseFragment<T extends BasePresenter,A extends Activity> extends Fragment implements BaseView{
    private View view;
    private T presenter;
    private Unbinder mUnbinder;
    private CustomProgressDialog progressDialog;
    private boolean UserVisible;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(getLayout(),null);
        mUnbinder= ButterKnife.bind(this,view);
        initView();
        initData();
        presenter=initPresent();
        if(presenter!=null){
            presenter.onStart(this);
        }
        return view;
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    protected abstract int getLayout();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract T initPresent();
    protected void showToast(String content) {
        if(getActivity()!=null){
            getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show());
        }
    }
    public T getPresenter(){
        if(presenter==null){
            presenter=initPresent();
        }
        return presenter;
    }
    @Override
    public void onResume() {
        super.onResume();
        if(!isHidden()&&isAdded()){
            if(presenter!=null){
                presenter.onLoadData();
            }else{
                presenter=initPresent();
            }
        }
        onFragmentResume();
    }
    public void onFragmentResume(){

    }

    @Override
    public void loadMore() {
        showToastMsg(getString(R.string.load_all));
    }

    @Override
    public void missLoading() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void showLoading(String msg) {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(getAppActivity());
        }
        if (!progressDialog.isShowing()) {
            progressDialog.setMessage(msg).show();
        }
    }

    @Override
    public void showToastMsg(String msg) {
        Toast.makeText(getAppActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder=null;
        }
        if(progressDialog!=null){
            progressDialog=null;
        }
        if(presenter!=null){
            presenter.onDestroy();
            presenter=null;
        }
    }
    public A getAppActivity(){
        return (A)getActivity();
    }
    public View getFragmentView(){
        return  view;
    }
}
