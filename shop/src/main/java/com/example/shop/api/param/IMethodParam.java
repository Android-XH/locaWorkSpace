package com.example.shop.api.param;

import com.example.shop.api.common.ParamCommon;

public interface IMethodParam {
    //获取商品列表
    ParamCommon getProductList(ProductParam productParam);
    //获取商品列表
    ParamCommon getProductList(int id);

    ParamCommon login(UserParam userParam);
}
