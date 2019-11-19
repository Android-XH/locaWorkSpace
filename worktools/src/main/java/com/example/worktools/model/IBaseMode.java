package com.example.worktools.model;

import java.util.HashMap;

/**
 * Created by xuhao on 2017/11/23.
 */

public interface IBaseMode {
    void onDestroy();
    void get(String method,CallBack callBack);
    void get(String method,HashMap<String,String> map, CallBack callBack);
    void post(String method,HashMap<String,String>map,CallBack callBack);
    void uploadFile(String method,String filePath,HashMap<String,String>map,ProgressCallBack callBack);
}
