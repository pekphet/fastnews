package com.youzi.fastnews.entity;

/**
 * Created by fish on 16-12-28.
 */

/*
{
        "user_id": "1",
        "share_count": "4", //分享数
        "user_name": "小宋啦啦???? ????么么哒你你哦",
    },
 */
public class ShareRankE {
    private int     user_id;
    private int     share_count;
    private int     share_point;
    private String  user_name;
    private String  avatar;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getShare_count() {
        return share_count;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getShare_point() {
        return share_point;
    }

    public void setShare_point(int share_point) {
        this.share_point = share_point;
    }
}
