package com.example.shop.api.common;

import androidx.annotation.Nullable;

import java.util.HashMap;


public class ParamCommon extends HashMap<String,String> {
    public static ParamCommon getInstance(){
        return new ParamCommon();
    }
    @Nullable
    public Object put(String key, Object value) {
        return super.put(key, String.valueOf(value));
    }
}
