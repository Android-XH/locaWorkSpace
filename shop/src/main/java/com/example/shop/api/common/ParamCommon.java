package com.example.shop.api.common;

import androidx.annotation.Nullable;

import java.util.HashMap;


public class ParamCommon extends HashMap<String,String> {
    public static ParamCommon getInstance(){
        return new ParamCommon();
    }
    @Nullable
    public void put(String key, Object value) {
        super.put(key, String.valueOf(value));
    }
}
