package com.example.shop.api;

import com.example.shop.BuildConfig;

/**
 * @author xuhao
 */
public class Host {
    private static final String HOST="https://www.zhenhuisheng.com/api/";
    private static final String LOC_HOST="http://192.168.100.17:8080/myshop/api/";
//    private static final String loc_host="http://192.168.31.210:8080/MyShop/api/";

    public static String getHost(){
        if(BuildConfig.DEBUG){
            return LOC_HOST;
        }else{
            return HOST;
        }
    }
}
