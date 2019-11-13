package com.example.shop.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shop.R;
import com.example.shop.adapter.ProductAdapter;
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
    private ProductAdapter adapter;
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
        adapter=new ProductAdapter(this);
        productList.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getPresenter().loadRefresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                getPresenter().loadMore();
            }
        });
    }

    @Override
    public void onRefreshComplete() {

    }

    @Override
    public void onLoadList(List<Product.Data> dataList) {
        Logx.e("服务器返回数据" + dataList.toString());
        adapter.setList(dataList);
        refreshLayout.finishRefresh(true);
    }

    @Override
    public void onLoadMore(List<Product.Data> dataList) {
        adapter.addList(dataList);
        refreshLayout.finishLoadMore(true);
    }

}
