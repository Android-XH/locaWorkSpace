package com.example.shop.api.param;

import com.example.shop.bean.array.Pagination;
import com.example.shop.util.StringUtil;

import static com.example.shop.api.param.ParamKey.*;

public class MethodParamImpl implements IMethodParam {
    @Override
    public ParamCommon getProductList(BaseParam baseParam) {
        ParamCommon paramCommon=new ParamCommon();
        Pagination pagination=baseParam.getPagination();
        paramCommon.put(PAGE,pagination.getPage());
        paramCommon.put(SIZE,pagination.getSize());
        if(baseParam.getId()!=0){
            paramCommon.put(ID,baseParam.getId());
        }
        if(baseParam.getCategory_id()!=0){
            paramCommon.put(CATEGORY_ID,baseParam.getCategory_id());
        }
       if(baseParam.getCategory_item_id()!=0){
           paramCommon.put(CATEGORY_ITEM_ID,baseParam.getCategory_item_id());
       }
        if(StringUtil.isNoEmpty(baseParam.getSort())){
            paramCommon.put(SORT,baseParam.getSort());
        }
       if(StringUtil.isNoEmpty(baseParam.getKeyWord())){
           paramCommon.put(KEY_WORD,baseParam.getKeyWord());
       }
        return paramCommon;
    }

    @Override
    public ParamCommon getProductList(int id) {
        ParamCommon paramCommon=new ParamCommon();
        paramCommon.put(ID,id);
        return paramCommon;
    }
}
