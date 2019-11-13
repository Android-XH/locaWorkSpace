package com.example.shop.mode.impl;

import com.example.shop.api.Host;
import com.example.shop.api.param.MethodParamImpl;
import com.example.worktools.model.BaseMode;


public class BaseAppMode extends BaseMode{
    private MethodParamImpl methodParam;
    public MethodParamImpl getParam(){
        if(methodParam==null){
            methodParam=new MethodParamImpl();
        }
        return methodParam;
    }
    @Override
    protected String getHost() {
        return Host.getHost();
    }
}
