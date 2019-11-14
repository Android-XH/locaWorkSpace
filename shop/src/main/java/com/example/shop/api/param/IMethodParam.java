package com.example.shop.api.param;

import com.example.shop.bean.array.BaseArray;
import com.example.shop.bean.array.Pagination;

public interface IMethodParam {
    //获取商品列表
    ParamCommon getProductList(Pagination pagination);
    //获取商品列表
    ParamCommon getProductList(int id);
}
