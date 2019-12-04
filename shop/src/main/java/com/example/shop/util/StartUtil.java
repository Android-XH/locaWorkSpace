package com.example.shop.util;

import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Bundle;

import com.example.shop.activity.ProductDetailActivity;
import com.example.shop.activity.SearchActivity;
import com.example.shop.activity.SearchDataActivity;
import com.example.shop.bean.Category;
import com.example.shop.bean.CategoryItem;
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
    public void startSearch(Activity activity){
        startActivity(activity,SearchActivity.class);
    }
    public void startSearchData(Activity activity,Object value){
        Bundle bundle=new Bundle();
        if(value instanceof String){
            bundle.putString(IntentKey.SEARCH_VALUE,String.valueOf(value));
        }
        if(value instanceof Integer){
            bundle.putInt(IntentKey.SEARCH_VALUE, (Integer) value);
        }
        if(value instanceof Category.Data){
            bundle.putParcelable(IntentKey.SEARCH_VALUE,(Category.Data)value);
        }
        if(value instanceof CategoryItem.Data){
            bundle.putParcelable(IntentKey.SEARCH_VALUE,(CategoryItem.Data)value);
        }
        startActivity(activity,bundle, SearchDataActivity.class);
    }
}
