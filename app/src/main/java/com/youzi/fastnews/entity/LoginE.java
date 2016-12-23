package com.youzi.fastnews.entity;

/**
 * Created by fish on 16-12-23.
 */

public class LoginE {
    private String code;
    private long expire_time;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(long expire_time) {
        this.expire_time = expire_time;
    }
}
