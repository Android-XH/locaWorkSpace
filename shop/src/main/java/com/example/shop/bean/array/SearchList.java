package com.example.shop.bean.array;

import com.example.shop.bean.Search;

import java.util.List;

public class SearchList extends BaseArray {
    private List<Search.Data>data;

    public List<Search.Data> getData() {
        return data;
    }

    public void setData(List<Search.Data> data) {
        this.data = data;
    }
}
