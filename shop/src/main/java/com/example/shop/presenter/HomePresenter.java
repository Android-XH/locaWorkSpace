package com.example.shop.presenter;

import com.example.shop.bean.Product;
import com.example.shop.bean.array.Pagination;
import com.example.shop.bean.array.ProductList;
import com.example.shop.mode.impl.ProductModeImpl;
import com.example.shop.viewImpl.IHomeView;
import com.example.worktools.adapter.LoadStatus;
import com.example.worktools.model.CallBack;
import com.example.worktools.presenter.BasePresenter;
import com.example.worktools.util.Logx;

import java.util.List;

public class HomePresenter extends BasePresenter<ProductModeImpl, IHomeView> {
    private Pagination pagination;
    private int page;

    public HomePresenter() {
        page = 1;
        pagination = new Pagination();
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
        page = 1;
        getProductList(LoadStatus.LOAD_REFRESH);
    }

    public void loadMore() {
        page += 1;
        getProductList(LoadStatus.LOAD_REFRESH);
    }

    private void getProductList(LoadStatus status) {
        pagination.setPage(page);
        getMode().getProductList(pagination, new CallBack<ProductList>() {
            @Override
            public void onSuccess(ProductList productList) {
                pagination=productList.getPagination();
                page=pagination.getPage();
                switch (status) {
                    case LOAD_MORE:
                        getView().onLoadMore(productList.getData());
                        break;
                    case LOAD_REFRESH:
                        getView().onLoadList(productList.getData());
                        break;
                }

            }

            @Override
            public void onFail(String msg) {
                if (getView() != null) {
                    getView().showToastMsg(msg);
                }

            }
        });
    }
}
