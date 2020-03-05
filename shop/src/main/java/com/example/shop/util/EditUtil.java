package com.example.shop.util;

import android.text.TextUtils;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

/**
 *
 * @author xuhao
 * @date 2017/12/12
 */

public class EditUtil {
    public static String getString(EditText editText){
        if(editText!=null){
            return String.valueOf(editText.getText());
        }
        return null;
    }
    /**
     * 验证是否为空
     * @param account
     * @return
     */
    public static boolean isEmpty(String account) {
        if (TextUtils.isEmpty(account)) {
            return true;
        }
        return false;
    }
    public static boolean isEmpty(TextInputLayout textInputLayout, String message, String errMessage){
        if(isEmpty(message)){
            showError(textInputLayout,errMessage);
            return true;
        }else{
            clearError(textInputLayout);
            return false;
        }
    }
    /**
     * 显示错误提示，并获取焦点
     *
     * @param textInputLayout
     * @param error
     */
    public static void showError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }
    public static void clearError(TextInputLayout textInputLayout){
        textInputLayout.setErrorEnabled(false);
    }
}
