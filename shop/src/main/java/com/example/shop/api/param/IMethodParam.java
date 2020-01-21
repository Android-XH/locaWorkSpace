package com.example.shop.api.param;

import com.example.shop.api.common.ParamCommon;

public interface IMethodParam {
    /**
     *
     * @param productParam
     * @return
     */
    ParamCommon getProductList(ProductParam productParam);

    /**
     * 获取商品列表
     * @param id
     * @return
     */
    ParamCommon getProductList(int id);

    ParamCommon login(UserParam userParam);

    ParamCommon register(UserParam userParam);
}
