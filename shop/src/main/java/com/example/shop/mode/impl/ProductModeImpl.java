package com.example.shop.mode.impl;

import com.example.shop.api.Method;
import com.example.shop.api.param.BaseParam;
import com.example.shop.api.param.ErrMessage;
import com.example.shop.bean.Product;
import com.example.shop.bean.TaoKey;
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
    public void getProductList(BaseParam baseParam, CallBack<ProductList> callBack) {
        get(Method.GET_PRODUCT_LIST,getParam().getProductList(baseParam), new CallBack() {
            @Override
            public void onSuccess(Object o) {
                ProductList productList= GsonUtil.parseData(o,ProductList.class);
                if(productList!=null){
                    if(productList.isSuccess()){
                        callBack.onSuccess(productList);
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
        get(Method.GET_PRODUCT_DETAIL,getParam().getProductList(id), new CallBack() {
            @Override
            public void onSuccess(Object o) {
                Product product= GsonUtil.parseData(o,Product.class);
                if(product!=null){
                    if(product.isSuccess()){
                        callBack.onSuccess(product.getData());
                    }else{
                        callBack.onFail(product.getMsg());
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
    public void getRecommend(CallBack<ProductList> callBack) {
        get(Method.GET_RECOMMEND,new CallBack() {
            @Override
            public void onSuccess(Object o) {
                ProductList productList= GsonUtil.parseData(o,ProductList.class);
                if(productList!=null){
                    if(productList.isSuccess()){
                        callBack.onSuccess(productList);
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
    public void getTaoKey(int id, CallBack<TaoKey> callBack) {
        get(Method.GET_TAO_KEY, getParam().getProductList(id), new CallBack() {
            @Override
            public void onSuccess(Object o) {
                TaoKey taoKey=GsonUtil.parseData(o,TaoKey.class);
                if(taoKey!=null){
                    if(taoKey.isSuccess()){
                        callBack.onSuccess(taoKey);
                    }else{
                        callBack.onFail(taoKey.getMsg());
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
}
