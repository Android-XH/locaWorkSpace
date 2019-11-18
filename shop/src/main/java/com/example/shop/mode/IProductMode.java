package com.example.shop.mode;

import com.example.shop.api.param.BaseParam;
import com.example.shop.bean.Product;
import com.example.shop.bean.array.ProductList;
import com.example.worktools.model.CallBack;


public interface IProductMode{
    void getProductList(BaseParam baseParam, CallBack<ProductList>callBack);
    void getProductDetail(int id,CallBack<Product.Data>callBack);
}
