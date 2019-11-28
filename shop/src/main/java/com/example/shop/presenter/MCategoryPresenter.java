package com.example.shop.presenter;

import com.example.shop.bean.array.MCategoryList;
import com.example.shop.mode.IMCategoryMode;
import com.example.shop.mode.impl.MenuCategoryMode;
import com.example.shop.viewImpl.IMCategoryView;
import com.example.worktools.model.CallBack;
import com.example.worktools.presenter.BasePresenter;

public class MCategoryPresenter extends BasePresenter<IMCategoryMode, IMCategoryView> {
    @Override
    public void getData() {
        getMode().getMenuCategory(false, new CallBack<MCategoryList>() {
            @Override
            public void onSuccess(MCategoryList mCategoryList) {
                getView().onLoadData(mCategoryList);
            }

            @Override
            public void onFail(String msg) {
                getView().onLoadFail(msg);
            }
        });
    }

    @Override
    public IMCategoryMode initMode() {
        return new MenuCategoryMode();
    }
}
