package com.example.shop.util;

import android.app.Activity;
import android.os.Bundle;

import com.example.shop.activity.ProductDetailActivity;
import com.example.shop.bean.Product;
import com.example.shop.common.IntentKey;
import com.example.worktools.util.BaseStart;

public class StartUtil extends BaseStart{
    private static StartUtil startUtil;
    public static StartUtil getInstance(){
        if(startUtil==null){
            startUtil=new StartUtil();
        }
        return startUtil;
    }
    public void startProductDetail(Activity activity, Product.Data data){
        Bundle bundle=new Bundle();
        bundle.putParcelable(IntentKey.PRODUCT_DATA,data);
        startActivity(activity,bundle,ProductDetailActivity.class);
    }
}
