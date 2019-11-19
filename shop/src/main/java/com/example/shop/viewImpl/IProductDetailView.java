package com.example.shop.viewImpl;

import com.example.shop.bean.Product;
import com.example.worktools.baseview.BaseView;

import java.util.List;

public interface IProductDetailView extends BaseView {
    void onLoadProduct(Product.Data data);
    void onLoadProductList(List<Product.Data> dataList);
    void onLoadKey(String key);
}
