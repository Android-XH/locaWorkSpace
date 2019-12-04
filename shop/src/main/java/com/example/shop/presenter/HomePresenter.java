package com.example.shop.presenter;

import com.example.shop.api.param.BaseParam;
import com.example.shop.bean.array.Pagination;
import com.example.shop.bean.array.ProductList;
import com.example.shop.common.ConfigCommon;
import com.example.shop.mode.IProductMode;
import com.example.shop.mode.impl.ProductModeImpl;
import com.example.shop.viewImpl.IHomeView;
import com.example.worktools.adapter.LoadStatus;
import com.example.worktools.model.CallBack;
import com.example.worktools.presenter.BasePresenter;


public class HomePresenter extends BasePresenter<IProductMode, IHomeView> {
    private boolean loadBanner,loadCosmetics;
    private int page;
    public HomePresenter() {
    }

    @Override
    public void getData() {
        loadRefresh();
    }

    @Override
    public ProductModeImpl initMode() {
        return new ProductModeImpl();
    }

    public void loadRefresh() {
        loadBanner=false;
        loadCosmetics=false;
        page=1;
        getRecommend();
        getCosmetics(LoadStatus.LOAD_REFRESH);
    }
    public void loadMore() {
        loadBanner=true;
        page+=1;
        getCosmetics(LoadStatus.LOAD_MORE);
    }
    private void getRecommend(){
        getMode().getRecommend(new CallBack<ProductList>() {
            @Override
            public void onSuccess(ProductList productList) {
                getView().onLoadBanner(productList.getData());
                loadBanner=true;
                loadDone();
            }

            @Override
            public void onFail(String msg) {
                loadBanner=true;
                loadDone();
            }
        });
    }
    private void getCosmetics(LoadStatus status){
        BaseParam baseParam=new BaseParam();
        baseParam.setPagination(new Pagination(page,6));
        baseParam.setSort("volume");
        baseParam.setMinPrice("10");
        baseParam.setMaxPrice("200");
        getMode().getProductList(baseParam, new CallBack<ProductList>() {
            @Override
            public void onSuccess(ProductList productList) {
                switch (status){
                    case LOAD_MORE:
                        getView().onLoadMore(productList.getData());

                        break;
                    case LOAD_REFRESH:
                        getView().onLoadRefresh(productList.getData());

                        loadCosmetics=true;
                        loadDone();
                        break;
                }

            }

            @Override
            public void onFail(String msg) {
                loadCosmetics=true;
                loadDone();
            }
        });
    }
    private void loadDone(){
        if(loadBanner&&loadCosmetics){
            getView().onFinishRefresh(true);
        }
    }
}
