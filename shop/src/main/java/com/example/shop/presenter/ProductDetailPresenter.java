package com.example.shop.presenter;

import com.example.shop.api.param.ProductParam;
import com.example.shop.bean.Product;
import com.example.shop.bean.TaoKey;
import com.example.shop.bean.array.Pagination;
import com.example.shop.bean.array.ProductList;
import com.example.shop.mode.IProductMode;
import com.example.shop.mode.impl.ProductModeImpl;
import com.example.shop.viewImpl.IProductDetailView;
import com.example.worktools.model.CallBack;
import com.example.worktools.presenter.BasePresenter;

public class ProductDetailPresenter extends BasePresenter<IProductMode, IProductDetailView> {
    private int id;

    public ProductDetailPresenter(int id) {
        this.id = id;
    }

    @Override
    public void getData() {
        getProductDetail(id);
        getProductList(id);
    }

    @Override
    public ProductModeImpl initMode() {
        return new ProductModeImpl();
    }
    private void getProductDetail(int id){
        getMode().getProductDetail(id, new CallBack<Product.Data>() {
            @Override
            public void onSuccess(Product.Data data) {
                getView().onLoadProduct(data);
            }

            @Override
            public void onFail(String msg) {
                getView().showToastMsg(msg);
            }
        });
    }
    private void getProductList(int id){
        Pagination pagination=new Pagination();
        pagination.setPage(1);
        pagination.setSize(6);
        ProductParam productParam =new ProductParam();
        productParam.setId(id);
        productParam.setPagination(pagination);
        getMode().getProductList(productParam, new CallBack<ProductList>() {
            @Override
            public void onSuccess(ProductList productList) {
                getView().onLoadProductList(productList.getData());
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
    public void getTaoKey(){
        getMode().getTaoKey(id, new CallBack<TaoKey>() {
            @Override
            public void onSuccess(TaoKey taoKey) {
                getView().onLoadKey(taoKey.getData());
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
