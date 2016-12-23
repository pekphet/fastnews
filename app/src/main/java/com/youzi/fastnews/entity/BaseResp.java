package com.youzi.fastnews.entity;

/**
 * Created by fish on 16-12-23.
 */

public class BaseResp {
    private int         code;
    private String      msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {

        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
