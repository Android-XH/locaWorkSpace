package com.example.shop.bean.array;

import com.example.shop.bean.Product;

import java.util.List;

public class ProductList extends BaseArray {
   private List<Product.Data>data;

    public List<Product.Data> getData() {
        return data;
    }

    public void setData(List<Product.Data> data) {
        this.data = data;
    }
}
