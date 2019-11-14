package com.example.worktools.exception;

/**
 * Created by xuhao on 2018/4/28.
 */

public class UtilException extends Exception {
    public UtilException(RequestCommon requestCommon) {
        super(requestCommon.toString());
    }
}