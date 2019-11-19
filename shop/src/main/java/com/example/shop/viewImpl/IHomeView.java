package com.example.shop.viewImpl;

import com.example.shop.bean.Product;
import com.example.worktools.baseview.BaseView;

import java.util.List;

public interface IHomeView extends BaseView {
    void onFinishRefresh(boolean isSuccess);
    void onLoadBanner(List<Product.Data>dataList);
    void onLoadRefresh(List<Product.Data>dataList);
    void onLoadMore(List<Product.Data>dataList);
}
