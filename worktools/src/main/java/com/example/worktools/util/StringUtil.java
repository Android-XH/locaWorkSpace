package com.example.worktools.util;

public class StringUtil {
    public static boolean isEmpty(String str){
        if(null==str||str.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    public static boolean isNoEmpty(String str){
        return !isEmpty(str);
    }
}
