package com.example.shop.mode.impl;

import com.example.shop.api.Method;
import com.example.shop.api.param.BaseParam;
import com.example.shop.api.param.ErrMessage;
import com.example.shop.bean.array.ProductList;
import com.example.shop.bean.array.SearchList;
import com.example.shop.mode.ISearchMode;
import com.example.worktools.model.CallBack;
import com.example.worktools.util.GsonUtil;

public class SearchModeImpl extends BaseAppMode implements ISearchMode {
    @Override
    public void getSearchKey(CallBack<SearchList> callBack) {
        get(Method.GET_SEARCH_KEY, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                SearchList searchList= GsonUtil.parseData(o,SearchList.class);
                if(searchList.isSuccess()){
                    callBack.onSuccess(searchList);
                }else{
                    callBack.onFail(ErrMessage.NULL_BEAN);
                }
            }

            @Override
            public void onFail(String msg) {
                callBack.onFail(msg);
            }
        });
    }

}
