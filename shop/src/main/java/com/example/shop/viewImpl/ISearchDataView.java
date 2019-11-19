package com.example.shop.viewImpl;

import com.example.shop.bean.Product;
import com.example.worktools.baseview.BaseView;

import java.util.List;

public interface ISearchDataView extends BaseView {
    void onLoadRefresh(List<Product.Data> dataList,int totalPage);
    void onLoadMore(List<Product.Data> dataList,int totalPage);
}
