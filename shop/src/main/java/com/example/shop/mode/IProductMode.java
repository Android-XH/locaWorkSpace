package com.example.shop.mode;

import com.example.shop.api.param.BaseParam;
import com.example.shop.bean.Product;
import com.example.shop.bean.TaoKey;
import com.example.shop.bean.array.ProductList;
import com.example.worktools.model.CallBack;
import com.example.worktools.model.IBaseMode;


public interface IProductMode extends IBaseMode {
    void getProductList(BaseParam baseParam, CallBack<ProductList>callBack);
    void getProductDetail(int id,CallBack<Product.Data>callBack);
    void getRecommend(CallBack<ProductList>callBack);
    void getTaoKey(int id, CallBack<TaoKey>callBack);
}
