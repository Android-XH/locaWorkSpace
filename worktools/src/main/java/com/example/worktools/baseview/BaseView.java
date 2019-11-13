package com.example.worktools.baseview;


public interface BaseView {
    void loadMore();
    void missLoading(String msg);
    void showLoading(String msg);
    void showToastMsg(String msg);
    void onRefreshComplete();
}
