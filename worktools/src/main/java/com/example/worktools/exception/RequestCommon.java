package com.example.worktools.exception;

public enum RequestCommon {
    NOT_INIT(404,"该组件未初始化，请调用init()方法初始化！");
    private int code;
    private String message;

    private RequestCommon(int code, String message){
        this.code=code;
        this.message=message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
