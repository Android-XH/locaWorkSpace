package com.example.shop.api.param;

import com.example.shop.bean.array.Pagination;

import static com.example.shop.api.param.ParamKey.*;

public class MethodParamImpl implements IMethodParam {
    @Override
    public ParamCommon getProductList(Pagination pagination) {
        ParamCommon paramCommon=new ParamCommon();
        paramCommon.put(PAGE,pagination.getPage());
        paramCommon.put(SIZE,pagination.getSize());
        return paramCommon;
    }

    @Override
    public ParamCommon getProductList(int id) {
        ParamCommon paramCommon=new ParamCommon();
        paramCommon.put(ID,id);
        return paramCommon;
    }
}
