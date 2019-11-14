package com.example.shop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

public class HomeActivity extends AppBaseActivity<HomePresenter> implements IHomeView {
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
        refreshLayout.finishRefresh(true);
    }

    @Override
    public void onLoadMore(List<Product.Data> dataList) {
        adapter.addList(dataList);
        refreshLayout.finishLoadMore(true);
    }

}
