package com.example.shop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.example.shop.AppBaseActivity;
import com.example.shop.R;
import com.example.shop.adapter.ProductAdapter;
import com.example.shop.bean.Product;
import com.example.shop.presenter.HomePresenter;
import com.example.shop.util.StartUtil;
import com.example.shop.viewImpl.IHomeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

public class HomeActivity extends AppBaseActivity<HomePresenter> implements IHomeView, OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.product_list)
    ListView productList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private ProductAdapter adapter;

    @Override
    protected int setContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    protected void initAppData(Bundle bundle) {
        adapter=new ProductAdapter(this);
        productList.setAdapter(adapter);
    }

    @Override
    protected void initView() {
        setTitle(R.string.home);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StartUtil.getInstance().startProductDetail(HomeActivity.this,adapter.getItem(position));
            }
        });
    }

    @Override
    public void onLoadList(List<Product.Data> dataList) {
        adapter.setList(dataList);
    }

    @Override
    public void onFinishRefresh(boolean isSuccess) {
        refreshLayout.finishRefresh(isSuccess);
    }

    @Override
    public void onLoadMore(List<Product.Data> dataList) {
        adapter.addList(dataList);
    }

    @Override
    public void onFinishMore(boolean isSuccess) {
        refreshLayout.finishLoadMore(isSuccess);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getPresenter().loadRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getPresenter().loadMore();
    }
}
