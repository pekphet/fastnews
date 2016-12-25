package com.youzi.fastnews.entity;

/**
 * Created by fish on 16-12-23.
 */

public class BaseResp {
    private int         code;
    private String      message;

    public String getMsg() {
        return message;
    }

    public void setMsg(String message) {
        this.message = message;
    }

    public int getCode() {

        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
