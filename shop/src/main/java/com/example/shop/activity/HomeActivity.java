package com.example.shop.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shop.R;
import com.example.shop.bean.Product;
import com.example.shop.presenter.HomePresenter;
import com.example.shop.viewImpl.IHomeView;
import com.example.worktools.baseview.BaseActivity;
import com.example.worktools.util.Logx;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity<HomePresenter> implements IHomeView {
    @BindView(R.id.product_list)
    ListView productList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected HomePresenter initPresent() {
        return new HomePresenter();
    }

    @Override
    protected void initData(Bundle bundle) {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    @Override
    public void onRefreshComplete() {

    }

    @Override
    public void onLoadList(List<Product.Data> dataList) {
        Logx.e("服务器返回数据" + dataList.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
