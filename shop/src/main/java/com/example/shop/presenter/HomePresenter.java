package com.example.shop.presenter;

import com.example.shop.api.param.BaseParam;
import com.example.shop.bean.array.Pagination;
import com.example.shop.bean.array.ProductList;
import com.example.shop.common.ConfigCommon;
import com.example.shop.mode.impl.ProductModeImpl;
import com.example.shop.viewImpl.IHomeView;
import com.example.worktools.adapter.LoadStatus;
import com.example.worktools.model.CallBack;
import com.example.worktools.presenter.BasePresenter;

import static com.example.shop.common.ConfigCommon.*;

public class HomePresenter extends BasePresenter<ProductModeImpl, IHomeView> {

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
        getClothesList();
        getManClothes();
        getCosmetics();
    }

    private void getClothesList() {
        BaseParam baseParam=new BaseParam();
        baseParam.setPagination(new Pagination(1,6));
        baseParam.setCategory_id(WOMAN_CLOTHES);
        getMode().getProductList(baseParam, new CallBack<ProductList>() {
            @Override
            public void onSuccess(ProductList productList) {
                getView().onLoadWomanClothes(productList.getData());
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
    private void getManClothes(){
        BaseParam baseParam=new BaseParam();
        baseParam.setPagination(new Pagination(1,6));
        baseParam.setCategory_id(MAN_CLOTHES);
        getMode().getProductList(baseParam, new CallBack<ProductList>() {
            @Override
            public void onSuccess(ProductList productList) {
                getView().onLoadManClothes(productList.getData());
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
    private void getCosmetics(){
        BaseParam baseParam=new BaseParam();
        baseParam.setPagination(new Pagination(1,6));
        baseParam.setCategory_id(COSMETICS);
        getMode().getProductList(baseParam, new CallBack<ProductList>() {
            @Override
            public void onSuccess(ProductList productList) {
                getView().onLoadCosmetics(productList.getData());
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
