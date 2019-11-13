package com.example.shop.mode;

import com.example.shop.bean.Product;
import com.example.shop.bean.array.Pagination;
import com.example.shop.bean.array.ProductList;
import com.example.worktools.model.CallBack;

import java.util.List;

public interface IProductMode{
    void getProductList(Pagination pagination,CallBack<ProductList>callBack);
    void getProductDetail(int id,CallBack<Product.Data>callBack);
}
