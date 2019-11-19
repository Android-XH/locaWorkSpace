package com.example.shop.presenter;

import com.example.shop.R;
import com.example.shop.api.param.BaseParam;
import com.example.shop.bean.array.Pagination;
import com.example.shop.bean.array.ProductList;
import com.example.shop.mode.IProductMode;
import com.example.shop.mode.impl.ProductModeImpl;
import com.example.shop.viewImpl.ISearchDataView;
import com.example.worktools.adapter.LoadStatus;
import com.example.worktools.model.CallBack;
import com.example.worktools.presenter.BasePresenter;

public class SearchDataPresenter extends BasePresenter<IProductMode, ISearchDataView> {
    private String keyWord;
    private int page;
    public SearchDataPresenter(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public void getData() {
        loadRefresh();
    }

    @Override
    public IProductMode initMode() {
        return new ProductModeImpl();
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public void loadRefresh(){
        page=1;
        getProductList(LoadStatus.LOAD_REFRESH);
    }
    public void loadMore(){
        page+=1;
        getProductList(LoadStatus.LOAD_MORE);
    }
    private void getProductList(LoadStatus status){
        BaseParam baseParam=new BaseParam();
        baseParam.setKeyWord(keyWord);
        baseParam.setPagination(new Pagination(page,20));
        getMode().getProductList(baseParam, new CallBack<ProductList>() {
            @Override
            public void onSuccess(ProductList productList) {
                Pagination pagination=productList.getPagination();
                switch (status){
                    case LOAD_MORE:
                        getView().onLoadMore(productList.getData(),pagination.getTotal());
                        break;
                    case LOAD_REFRESH:
                        getView().onLoadRefresh(productList.getData(),pagination.getTotal());
                        break;
                }
            }

            @Override
            public void onFail(String msg) {
                getView().showToastMsg(msg);
            }
        });
    }
}
