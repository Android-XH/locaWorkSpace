package com.example.shop.mode.impl;

import com.example.shop.api.Host;
import com.example.shop.api.param.IMethodParam;
import com.example.shop.api.param.impl.MethodParamImpl;
import com.example.worktools.model.BaseMode;


public class BaseAppMode extends BaseMode{
    private IMethodParam methodParam;
    public IMethodParam getParam(){
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
