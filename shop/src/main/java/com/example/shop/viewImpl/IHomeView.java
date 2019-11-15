package com.example.shop.viewImpl;

import com.example.shop.bean.Product;
import com.example.worktools.baseview.BaseView;

import java.util.List;

public interface IHomeView extends BaseView {
    void onLoadList(List<Product.Data>dataList);
    void onFinishRefresh(boolean isSuccess);
    void onLoadMore(List<Product.Data>dataList);
    void onFinishMore(boolean isSuccess);
}
