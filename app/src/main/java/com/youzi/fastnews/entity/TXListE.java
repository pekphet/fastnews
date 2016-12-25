package com.youzi.fastnews.entity;

/**
 * Created by Pekphet on 16/12/25.
 */


public class TXListE {
    private int     id;
    private String  ord;
    private int     user_id;
    private float   point;
    private String  create_time;
    private long    look_time;
    private String  status;
    private int     withdraw_type;
    private String  truename;
    private String  user_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrd() {
        return ord;
    }

    public void setOrd(String ord) {
        this.ord = ord;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public long getLook_time() {
        return look_time;
    }

    public void setLook_time(long look_time) {
        this.look_time = look_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWithdraw_type() {
        return withdraw_type;
    }

    public void setWithdraw_type(int withdraw_type) {
        this.withdraw_type = withdraw_type;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
