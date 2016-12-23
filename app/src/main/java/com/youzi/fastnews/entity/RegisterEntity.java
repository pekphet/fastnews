package com.youzi.fastnews.entity;

/**
 * Created by ywb on 2016/12/23.
 */

public class RegisterEntity {


    private String code;
    private int expire_time;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(int expire_time) {
        this.expire_time = expire_time;
    }
}
