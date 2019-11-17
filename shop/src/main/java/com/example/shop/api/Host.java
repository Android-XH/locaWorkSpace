package com.example.shop.api;

import com.example.shop.BuildConfig;

public class Host {
    private static final String host="https://www.zhenhuisheng.com/api/";
    private static final String loc_host="http://192.168.100.17:8080/myshop/api/";
    public static final String getHost(){
        if(!BuildConfig.DEBUG){
            return loc_host;
        }else{
            return host;
        }
    }
}
