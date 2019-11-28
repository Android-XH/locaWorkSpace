package com.example.shop.mode.impl;

import com.example.shop.api.Method;
import com.example.shop.api.param.ErrMessage;
import com.example.shop.bean.array.MCategoryList;
import com.example.shop.mode.IMCategoryMode;
import com.example.shop.util.share.SpUtil;
import com.example.worktools.model.CallBack;
import com.example.worktools.util.GsonUtil;
import com.example.worktools.util.LogUtil;

public class MenuCategoryMode extends BaseAppMode implements IMCategoryMode {
    @Override
    public void getMenuCategory(boolean isService,CallBack<MCategoryList> callBack) {
        if(isService){
            get(Method.GET_MENU_CATEGORY, new CallBack() {
                @Override
                public void onSuccess(Object o) {
                    MCategoryList mCategoryList= GsonUtil.parseData(o,MCategoryList.class);
                    if(mCategoryList!=null){
                        if(mCategoryList.isSuccess()){
                            SpUtil.getInstance().setMCategoryList(mCategoryList);
                            callBack.onSuccess(mCategoryList);
                        }else{
                            callBack.onFail(mCategoryList.getMsg());
                        }
                    }else{
                        callBack.onFail(ErrMessage.NULL_BEAN);
                    }
                }

                @Override
                public void onFail(String msg) {
                    callBack.onFail(msg);
                }
            });
        }else{
            MCategoryList mCategoryList= SpUtil.getInstance().getMCategoryList();
            if(mCategoryList!=null&&mCategoryList.isSuccess()){
                callBack.onSuccess(mCategoryList);
            }else{
                getMenuCategory(true,callBack);
            }

        }
    }
}
