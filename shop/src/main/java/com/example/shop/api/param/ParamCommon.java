package com.example.shop.api.param;

import androidx.annotation.Nullable;

import java.util.HashMap;


public class ParamCommon extends HashMap<String,String> {
    @Nullable
    public Object put(String key, Object value) {
        return super.put(key, String.valueOf(value));
    }
}
