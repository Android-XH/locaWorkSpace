package com.example.shop.presenter;

import com.example.shop.api.param.ProductParam;
import com.example.shop.bean.array.Pagination;
import com.example.shop.bean.array.ProductList;
import com.example.shop.mode.IProductMode;
import com.example.shop.mode.impl.ProductModeImpl;
import com.example.shop.viewImpl.ISearchDataView;
import com.example.worktools.adapter.LoadStatus;
import com.example.worktools.model.CallBack;
import com.example.worktools.presenter.BasePresenter;

public class SearchDataPresenter extends BasePresenter<IProductMode, ISearchDataView> {
    private ProductParam productParam;
    private Pagination pagination;
    private int page;

    public SearchDataPresenter() {
        productParam =new ProductParam();
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
        productParam.setKeyWord(keyWord);
    }
    public void setMenuID(int menuID) {
        productParam.setMenu_id(menuID);
    }
    public void setCategoryID(int category_id) {
        productParam.setCategory_id(category_id);
    }

    public void setCategoryItemID(int category_item_id) {
        productParam.setCategory_item_id(category_item_id);
    }

    public void setMinPrice(String minPrice) {
        productParam.setMinPrice(minPrice);
    }

    public void setMaxPrice(String maxPrice) {
        productParam.setMaxPrice(maxPrice);
    }

    public void setType(String[] type) {
        productParam.setTypes(type);
    }

    public void setLoadSort(String loadSort) {
        productParam.setSort(loadSort);
    }

    public void loadRefresh(){
        page=1;
        pagination.setPage(page);
        productParam.setPagination(pagination);
        getProductList(LoadStatus.LOAD_REFRESH);
    }
    public void loadMore(){
        page+=1;
        pagination.setPage(page);
        productParam.setPagination(pagination);
        getProductList(LoadStatus.LOAD_MORE);
    }
    private void getProductList(LoadStatus status){
        getMode().getProductList(productParam, new CallBack<ProductList>() {
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
