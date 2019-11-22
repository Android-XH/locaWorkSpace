package com.example.shop.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.AppBaseActivity;
import com.example.shop.R;
import com.example.shop.adapter.ProductBannerAdapter;
import com.example.shop.adapter.ProductImageAdapter;
import com.example.shop.adapter.ProductVerticalAdapter;
import com.example.shop.bean.Product;
import com.example.shop.presenter.HomePresenter;
import com.example.shop.util.PgyUtil;
import com.example.shop.util.StartUtil;
import com.example.shop.viewImpl.IHomeView;
import com.example.worktools.adapter.ImageViewAdapter;
import com.example.worktools.adapter.listener.OnRecycleItemClickListener;
import com.example.worktools.baseview.BaseActivity;
import com.example.worktools.recycle.SpacesItemDecoration;
import com.example.worktools.util.LogUtil;
import com.example.worktools.view.ImageTextViewPager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.shop.common.ConfigCommon.INSTALL_PACKAGES_REQUEST_CODE;
import static com.example.shop.common.ConfigCommon.WRITE_EXTERNAL_STORAGE;
import static com.example.worktools.util.DpUtil.dip2px;


public class HomeActivity extends AppBaseActivity<HomePresenter> implements IHomeView, OnRefreshListener, OnRecycleItemClickListener<Product.Data>, OnLoadMoreListener, ImageViewAdapter.onItemClickListener<Product.Data>, BaseActivity.RequestPermissionCallBack {
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
        if (ContextCompat.checkSelfPermission(getBaseContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE);
        } else {
            if (Build.VERSION.SDK_INT >= 26) {
                boolean b = getPackageManager().canRequestPackageInstalls();
                LogUtil.e(b+"");
                if (b) {
                    PgyUtil.Update();
                } else {
                    requestPermissions(this,new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES},this);
                }
            } else {
                PgyUtil.Update();
            }
        }
        this.mContext=this;
        couponRecommendAdapter = new ProductVerticalAdapter(this);
        adapter = new ProductBannerAdapter(this);

    }

    @Override
    protected void initView() {
        setTitle(R.string.home);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        adapter.setOnItemClickListener(this);
        vpBanner.setWheel(true);
        vpBanner.setAdapter(adapter);
        couponRecommendAdapter.setOnRecycleItemClickListener(this);
        int lineWidth=5;
        rvCosmetics.setLayoutManager(new GridLayoutManager(mContext, spanCount,GridLayoutManager.VERTICAL,false));
        rvCosmetics.addItemDecoration(new SpacesItemDecoration(dip2px(lineWidth), dip2px(lineWidth), getResources().getColor(R.color.line)));
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
    //R.id.tv_clothes, R.id.tv_accessories, R.id.tv_cosmetics, R.id.tv_bag
    @OnClick({R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                StartUtil.getInstance().startSearch(this);
                break;
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
    public void onItemClick(Product.Data data) {
        StartUtil.getInstance().startProductDetail(this, data);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        vpBanner.setPause(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        vpBanner.setPause(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vpBanner.setDestroy(true);
    }

    @Override
    public void granted() {
        PgyUtil.Update();
    }

    @Override
    public void denied() {

    }
}
