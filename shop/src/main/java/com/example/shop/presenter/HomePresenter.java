package com.example.shop.presenter;

import com.example.shop.bean.Product;
import com.example.shop.bean.array.Pagination;
import com.example.shop.mode.impl.ProductModeImpl;
import com.example.shop.viewImpl.IHomeView;
import com.example.worktools.model.CallBack;
import com.example.worktools.presenter.BasePresenter;
import com.example.worktools.util.Logx;

import java.util.List;

public class HomePresenter extends BasePresenter<ProductModeImpl,IHomeView> {
    @Override
    public void getData() {
        getProductList();
    }

    @Override
    public ProductModeImpl initMode() {
        return new ProductModeImpl();
    }
    public void getProductList(){
        Pagination pagination=new Pagination();
        pagination.setPage(1);
        pagination.setSize(10);
        getMode().getProductList(pagination, new CallBack<List<Product.Data>>() {
            @Override
            public void onSuccess(List<Product.Data> data) {
                getView().onLoadList(data);
            }

            @Override
            public void onFail(String msg) {
                Logx.e(msg);
                if(getView()!=null){
                    getView().showToastMsg(msg);
                }

            }
        });
    }
}
