package com.example.shop.viewImpl;

import com.example.shop.bean.Product;
import com.example.worktools.baseview.BaseView;

import java.util.List;

public interface IHomeView extends BaseView {
    void onFinishRefresh(boolean isSuccess);
    void onLoadWomanClothes(List<Product.Data>dataList);
    void onLoadManClothes(List<Product.Data>dataList);
    void onLoadCosmetics(List<Product.Data>dataList);

}
