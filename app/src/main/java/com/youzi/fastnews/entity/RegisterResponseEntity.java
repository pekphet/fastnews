package com.youzi.fastnews.entity;

/**
 * Created by ywb on 2016/12/23.
 */

public class RegisterResponseEntity extends BaseResp{

    private RegisterEntity data;

    public RegisterEntity getData() {
        return data;
    }

    public void setData(RegisterEntity data) {
        this.data = data;
    }
}

