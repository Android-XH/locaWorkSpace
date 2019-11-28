package com.example.shop.mode;


import com.example.shop.bean.array.MCategoryList;
import com.example.worktools.model.CallBack;
import com.example.worktools.model.IBaseMode;

public interface IMCategoryMode extends IBaseMode {
    void getMenuCategory(boolean isService,CallBack<MCategoryList> callBack);
}
