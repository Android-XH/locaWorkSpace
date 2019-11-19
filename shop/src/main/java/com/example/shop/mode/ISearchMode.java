package com.example.shop.mode;

import com.example.shop.api.param.BaseParam;
import com.example.shop.bean.array.ProductList;
import com.example.shop.bean.array.SearchList;
import com.example.worktools.model.CallBack;
import com.example.worktools.model.IBaseMode;

public interface ISearchMode extends IBaseMode {
    void getSearchKey(CallBack<SearchList> callBack);
}
