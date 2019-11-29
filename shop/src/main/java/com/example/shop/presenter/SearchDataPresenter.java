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

import java.util.List;

public class SearchDataPresenter extends BasePresenter<IProductMode, ISearchDataView> {
    private  BaseParam baseParam;
    private Pagination pagination;
    private int page;

    public SearchDataPresenter() {
        baseParam=new BaseParam();
        pagination=new Pagination(page,20);
    }

    @Override
    public void getData() {

    }

    @Override
    public IProductMode initMode() {
        return new ProductModeImpl();
    }

    public void setKeyWord(String keyWord) {
        baseParam.setKeyWord(keyWord);
    }
    public void setMenuID(int menuID) {
        baseParam.setMenu_id(menuID);
    }
    public void setCategoryID(int category_id) {
        baseParam.setCategory_id(category_id);
    }

    public void setCategoryItemID(int category_item_id) {
        baseParam.setCategory_item_id(category_item_id);
    }

    public void setMinPrice(String minPrice) {
        baseParam.setMinPrice(minPrice);
    }

    public void setMaxPrice(String maxPrice) {
        baseParam.setMaxPrice(maxPrice);
    }

    public void setType(String[] type) {
        baseParam.setTypes(type);
    }

    public void setLoadSort(String loadSort) {
        baseParam.setSort(loadSort);
    }

    public void loadRefresh(){
        page=1;
        pagination.setPage(page);
        baseParam.setPagination(pagination);
        getProductList(LoadStatus.LOAD_REFRESH);
    }
    public void loadMore(){
        page+=1;
        pagination.setPage(page);
        baseParam.setPagination(pagination);
        getProductList(LoadStatus.LOAD_MORE);
    }
    private void getProductList(LoadStatus status){
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
