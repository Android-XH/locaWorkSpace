package com.example.shop.mode.impl;

import com.example.shop.api.Method;
import com.example.shop.bean.Product;
import com.example.shop.bean.array.BaseArray;
import com.example.shop.bean.array.Pagination;
import com.example.shop.bean.array.ProductList;
import com.example.shop.mode.IProductMode;
import com.example.worktools.model.CallBack;
import com.example.worktools.util.GsonUtil;

import java.util.List;

import static com.example.shop.api.param.ErrMessage.*;

public class ProductModeImpl extends BaseAppMode implements IProductMode {
    @Override
    public void getProductList(Pagination pagination, final CallBack<List<Product.Data>> callBack) {
        get(Method.GET_PRODUCT_LIST,getParam().getProductList(pagination), new CallBack() {
            @Override
            public void onSuccess(Object o) {
                ProductList productList= GsonUtil.parseData(o,ProductList.class);
                if(productList!=null){
                    if(productList.isSuccess()){
                        callBack.onSuccess(productList.getData());
                    }else{
                        callBack.onFail(productList.getMsg());
                    }
                }else{
                    callBack.onFail(NULL_BEAN);
                }

            }
            @Override
            public void onFail(String msg) {
                callBack.onFail(msg);
            }
        });
    }

    @Override
    public void getProductDetail(int id, CallBack<Product.Data> callBack) {

    }
}