package com.example.shop.viewImpl;

import com.example.shop.bean.array.MCategoryList;
import com.example.worktools.baseview.BaseView;

import java.util.List;

public interface IMCategoryView extends BaseView {
    void onLoadData(MCategoryList dataList);
    void onLoadFail(String msg);
}
