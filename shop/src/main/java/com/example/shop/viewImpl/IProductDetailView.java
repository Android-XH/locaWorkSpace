package com.example.shop.viewImpl;

import com.example.shop.bean.Product;
import com.example.worktools.baseview.BaseView;

public interface IProductDetailView extends BaseView {
    void onLoadProduct(Product.Data data);
}
