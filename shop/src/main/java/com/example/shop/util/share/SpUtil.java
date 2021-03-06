package com.example.shop.util.share;

import android.content.Context;

import com.example.shop.bean.User;
import com.example.shop.bean.array.MCategoryList;
import com.example.worktools.util.BaseSharedUtil;

public class SpUtil extends BaseSharedUtil {
    private final String defaultValue="";
    private final boolean defaultBoolean=false;
    private final int defaultInt=0;
    private final long defaultLong=0;
    private static SpUtil spUtil;
    public static SpUtil register(Context context){
        if(spUtil==null){
            spUtil=new SpUtil();
            spUtil.init(context);
        }
        return spUtil;
    }
    public static SpUtil getInstance(){
        return spUtil;
    }

    @Override
    public void init(Context context) {
        super.init(context);
    }
    public void setMCategoryList(MCategoryList mCategoryList){
        putData(SharedKey.M_CATEGORY_LIST,mCategoryList);
    }
    public MCategoryList getMCategoryList(){
        return (MCategoryList) getData(SharedKey.M_CATEGORY_LIST,new MCategoryList(-1));
    }
    public void setUserMessage(User.Data data){
        putData(SharedKey.TOKEN,data.getToken());
    }
    public String getToken(){
        return (String) getData(SharedKey.TOKEN,defaultValue);
    }

    @Override
    public void onDestory() {
        super.onDestory();
    }
}
