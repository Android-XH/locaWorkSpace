package com.example.shop.bean.array;

public class Pagination{
    private int page;
    private int total;
    private int size;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size==0?20:size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}