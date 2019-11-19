package com.example.shop.viewImpl;

import com.example.shop.bean.Search;
import com.example.worktools.baseview.BaseView;

import java.util.List;

public interface ISearchView extends BaseView {
    void onLoadData(List<Search.Data> dataList);
}
