package com.example.worktools.baseview;


public interface BaseView {
    void loadMore();
    void missLoading();
    void showLoading(String msg);
    void showToastMsg(String msg);
}
