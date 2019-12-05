package com.example.shop.api.param.impl;

import com.example.shop.api.common.ParamCommon;
import com.example.shop.api.param.IMethodParam;
import com.example.shop.api.param.ProductParam;
import com.example.shop.api.param.UserParam;
import com.example.shop.bean.array.Pagination;
import com.example.worktools.util.StringUtil;

import static com.example.shop.api.common.ParamKey.*;

public class MethodParamImpl implements IMethodParam {
    @Override
    public ParamCommon getProductList(ProductParam productParam) {
        ParamCommon paramCommon = ParamCommon.getInstance();
        Pagination pagination = productParam.getPagination();
        paramCommon.put(PAGE, pagination.getPage());
        paramCommon.put(SIZE, pagination.getSize());
        if (productParam.getId() != 0) {
            paramCommon.put(ID, productParam.getId());
        }
        if (productParam.getCategory_id() != 0) {
            paramCommon.put(CATEGORY_ID, productParam.getCategory_id());
        }
        if (productParam.getCategory_item_id() != 0) {
            paramCommon.put(CATEGORY_ITEM_ID, productParam.getCategory_item_id());
        }
        if (productParam.getMenu_id() != 0) {
            paramCommon.put(MENU_ID, productParam.getMenu_id());
        }
        if (StringUtil.isNoEmpty(productParam.getSort())) {
            paramCommon.put(SORT, productParam.getSort());
        }
        if (StringUtil.isNoEmpty(productParam.getKeyWord())) {
            paramCommon.put(KEY_WORD, productParam.getKeyWord());
        }
        if (StringUtil.isNoEmpty(productParam.getMinPrice())) {
            paramCommon.put(MIN_PRICE, productParam.getMinPrice());
        }
        if (StringUtil.isNoEmpty(productParam.getMaxPrice())) {
            paramCommon.put(MAX_PRICE, productParam.getMaxPrice());
        }
        String[] types = productParam.getTypes();
        if (types != null && types.length > 0) {
            StringBuffer stringBuffer = new StringBuffer(types[0]);
            for (int i = 1; i < types.length; i++) {
                stringBuffer.append(",");
                stringBuffer.append(types[i]);
            }
            paramCommon.put(TYPES, stringBuffer.toString());
        }
        return paramCommon;
    }

    @Override
    public ParamCommon getProductList(int id) {
        ParamCommon paramCommon = ParamCommon.getInstance();
        paramCommon.put(ID, id);
        return paramCommon;
    }

    @Override
    public ParamCommon login(UserParam userParam) {
        ParamCommon paramCommon =ParamCommon.getInstance();
        paramCommon.put(USER_NAME,userParam.getUserName());
        paramCommon.put(PASS_WORD,userParam.getPassWord());
        return paramCommon;
    }
}
