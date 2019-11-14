package com.example.shop.presenter;

import com.example.shop.bean.Product;
import com.example.shop.mode.impl.ProductModeImpl;
import com.example.shop.viewImpl.IProductDetailView;
import com.example.worktools.model.CallBack;
import com.example.worktools.presenter.BasePresenter;

public class ProductDetailPresenter extends BasePresenter<ProductModeImpl, IProductDetailView> {
    private int id;

    public ProductDetailPresenter(int id) {
        this.id = id;
    }

    @Override
    public void getData() {
        getProductDetail(id);
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
}
