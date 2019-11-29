package com.example.shop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.R;
import com.example.shop.activity.MainActivity;
import com.example.shop.adapter.ProductBannerAdapter;
import com.example.shop.adapter.ProductVerticalAdapter;
import com.example.shop.bean.Product;
import com.example.shop.presenter.HomePresenter;
import com.example.shop.util.StartUtil;
import com.example.shop.viewImpl.IHomeView;
import com.example.worktools.adapter.ImageViewAdapter;
import com.example.worktools.adapter.listener.OnRecycleItemClickListener;
import com.example.worktools.baseview.BaseFragment;
import com.example.worktools.recycle.SpacesItemDecoration;
import com.example.worktools.view.ImageTextViewPager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.worktools.util.DpUtil.dip2px;

public class HomeFragment extends BaseFragment<HomePresenter, MainActivity> implements IHomeView, OnRefreshListener, OnLoadMoreListener, ImageViewAdapter.onItemClickListener<Product.Data>, OnRecycleItemClickListener<Product.Data> {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.vp_banner)
    ImageTextViewPager vpBanner;
    @BindView(R.id.rv_cosmetics)
    RecyclerView rvCosmetics;

    private ProductBannerAdapter adapter;

    private ProductVerticalAdapter couponRecommendAdapter;

    private Context mContext;
    private final int spanCount=2;//列表每行列数
    public static HomeFragment getInstance(){
        return new HomeFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }
    @Override
    protected void initData(Bundle bundle) {
        this.mContext=getAppActivity();
        couponRecommendAdapter = new ProductVerticalAdapter(mContext);
        adapter = new ProductBannerAdapter(mContext);
    }
    @Override
    protected void initView() {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        adapter.setOnItemClickListener(this);
        vpBanner.setWheel(true);
        vpBanner.setAdapter(adapter);
        couponRecommendAdapter.setOnRecycleItemClickListener(this);
        rvCosmetics.setLayoutManager(new GridLayoutManager(mContext, spanCount,GridLayoutManager.VERTICAL,false));
        rvCosmetics.setAdapter(couponRecommendAdapter);
    }
    @Override
    public void onFinishRefresh(boolean isSuccess) {
        refreshLayout.finishRefresh(isSuccess);
    }

    @Override
    public void onLoadBanner(List<Product.Data> dataList) {
        adapter.setData(dataList);
    }

    @Override
    public void onLoadRefresh(List<Product.Data> dataList) {
        couponRecommendAdapter.setDataList(dataList);
    }

    @Override
    public void onLoadMore(List<Product.Data> dataList) {
        couponRecommendAdapter.addDataList(dataList);
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getPresenter().loadRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getPresenter().loadMore();
    }
    @OnClick({R.id.tv_search,R.id.tv_clothes, R.id.tv_accessories, R.id.tv_cosmetics, R.id.tv_bag})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                StartUtil.getInstance().startSearch(getAppActivity());
                break;
            case R.id.tv_clothes:
                StartUtil.getInstance().startSearchData(getAppActivity(),2);
                break;
            case R.id.tv_accessories:
                StartUtil.getInstance().startSearchData(getAppActivity(),3);
                break;
            case R.id.tv_cosmetics:
                StartUtil.getInstance().startSearchData(getAppActivity(),1);
                break;
            case R.id.tv_bag:
                StartUtil.getInstance().startSearchData(getAppActivity(),4);
                break;
        }
    }

    @Override
    public void onItemClick(Product.Data data, int position) {
        StartUtil.getInstance().startProductDetail(getAppActivity(), data);
    }

    @Override
    public void onItemClick(Product.Data data) {
        StartUtil.getInstance().startProductDetail(getAppActivity(), data);
    }
    @Override
    protected HomePresenter initPresent() {
        return new HomePresenter();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        vpBanner.setDestroy(true);
    }
}
