package com.example.shop.presenter;

import com.example.shop.bean.array.Pagination;
import com.example.shop.bean.array.ProductList;
import com.example.shop.mode.impl.ProductModeImpl;
import com.example.shop.viewImpl.IHomeView;
import com.example.worktools.adapter.LoadStatus;
import com.example.worktools.model.CallBack;
import com.example.worktools.presenter.BasePresenter;

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
        getProductList(LoadStatus.LOAD_MORE);
    }

    private void getProductList(LoadStatus status) {
        pagination.setPage(page);
        getMode().getProductList(pagination, new CallBack<ProductList>() {
            @Override
            public void onSuccess(ProductList productList) {
                switch (status) {
                    case LOAD_MORE:
                        getView().onLoadMore(productList.getData());
                        getView().onFinishMore(true);
                        break;
                    case LOAD_REFRESH:
                        getView().onLoadList(productList.getData());
                        getView().onFinishRefresh(true);
                        break;
                }

            }

            @Override
            public void onFail(String msg) {
                if (getView() != null) {
                    getView().showToastMsg(msg);
                    switch (status) {
                        case LOAD_MORE:
                            getView().onFinishMore(false);
                            break;
                        case LOAD_REFRESH:
                            getView().onFinishRefresh(false);
                            break;
                    }
                }

            }
        });
    }
}
