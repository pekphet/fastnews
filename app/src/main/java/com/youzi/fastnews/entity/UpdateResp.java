package com.youzi.fastnews.entity;

/**
 * Created by fish on 16-12-27.
 */

public class UpdateResp {
    private String  content;
    private String  url;
    private int     is_force;
    private int     new_version_code;
    private int     is_update;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIs_force() {
        return is_force;
    }

    public void setIs_force(int is_force) {
        this.is_force = is_force;
    }

    public int getNew_version_code() {
        return new_version_code;
    }

    public void setNew_version_code(int new_version_code) {
        this.new_version_code = new_version_code;
    }

    public int getIs_update() {
        return is_update;
    }

    public void setIs_update(int is_update) {
        this.is_update = is_update;
    }
}
