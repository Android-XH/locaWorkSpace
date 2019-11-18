package com.example.shop.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.AppBaseActivity;
import com.example.shop.R;
import com.example.shop.adapter.ProductVerticalAdapter;
import com.example.shop.bean.Product;
import com.example.shop.presenter.HomePresenter;
import com.example.shop.util.StartUtil;
import com.example.shop.viewImpl.IHomeView;
import com.example.worktools.adapter.listener.OnRecycleItemClickListener;
import com.example.worktools.view.ImageTextViewPager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppBaseActivity<HomePresenter> implements IHomeView, OnRefreshListener,OnRecycleItemClickListener<Product.Data> {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.vp_banner)
    ImageTextViewPager vpBanner;
    @BindView(R.id.rv_cosmetics)
    RecyclerView rvCosmetics;
    @BindView(R.id.rv_woman_clothes)
    RecyclerView rvWomanClothes;
    @BindView(R.id.rv_man_clothes)
    RecyclerView rvManClothes;
    private ProductVerticalAdapter womanAdapter,manAdapter, cosmeticsAdapter;

    private Context mContext;
    private final int spanCount=2;//列表每行列数
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
        this.mContext=this;
        womanAdapter = new ProductVerticalAdapter(this);
        manAdapter = new ProductVerticalAdapter(this);
        cosmeticsAdapter = new ProductVerticalAdapter(this);
    }

    @Override
    protected void initView() {
        setTitle(R.string.home);
        refreshLayout.setOnRefreshListener(this);
        womanAdapter.setOnRecycleItemClickListener(this);
        manAdapter.setOnRecycleItemClickListener(this);
        cosmeticsAdapter.setOnRecycleItemClickListener(this);
        rvWomanClothes.setLayoutManager(new GridLayoutManager(mContext, spanCount,GridLayoutManager.VERTICAL,false));
        rvManClothes.setLayoutManager(new GridLayoutManager(mContext, spanCount,GridLayoutManager.VERTICAL,false));
        rvCosmetics.setLayoutManager(new GridLayoutManager(mContext, spanCount,GridLayoutManager.VERTICAL,false));
        setNoScroll(rvWomanClothes);
        setNoScroll(rvManClothes);
        setNoScroll(rvCosmetics);
        rvWomanClothes.setAdapter(womanAdapter);
        rvManClothes.setAdapter(manAdapter);
        rvCosmetics.setAdapter(cosmeticsAdapter);
    }
    private void setNoScroll(RecyclerView recyclerView){
        recyclerView.setNestedScrollingEnabled(false); //禁止滑动
        recyclerView.setFocusable(false);
    }
    @Override
    public void onFinishRefresh(boolean isSuccess) {
        refreshLayout.finishRefresh(isSuccess);
    }


    @Override
    public void onLoadWomanClothes(List<Product.Data> dataList) {
        womanAdapter.setDataList(dataList);
    }

    @Override
    public void onLoadManClothes(List<Product.Data> dataList) {
        manAdapter.setDataList(dataList);
    }

    @Override
    public void onLoadCosmetics(List<Product.Data> dataList) {
        cosmeticsAdapter.setDataList(dataList);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getPresenter().loadRefresh();
    }

    @OnClick({R.id.tv_clothes, R.id.tv_accessories, R.id.tv_cosmetics, R.id.tv_bag})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_clothes:
                showToastMsg("衣服");
                break;
            case R.id.tv_accessories:
                showToastMsg("配饰");
                break;
            case R.id.tv_cosmetics:
                showToastMsg("美妆");
                break;
            case R.id.tv_bag:
                showToastMsg("箱包");
                break;
        }
    }

    @Override
    public void onItemClick(Product.Data data, int position) {
        StartUtil.getInstance().startProductDetail(this, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
