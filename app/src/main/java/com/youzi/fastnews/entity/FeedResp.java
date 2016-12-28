package com.youzi.fastnews.entity;

/**
 * Created by fish on 16-12-28.
 */

public class FeedResp {
    private String  share_link;
    private String  share_title;
    private String  share_description;

    public String getShare_link() {
        return share_link;
    }

    public void setShare_link(String share_link) {
        this.share_link = share_link;
    }

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }

    public String getShare_description() {
        return share_description;
    }

    public void setShare_description(String share_description) {
        this.share_description = share_description;
    }
}
