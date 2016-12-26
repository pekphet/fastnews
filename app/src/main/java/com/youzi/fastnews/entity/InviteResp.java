package com.youzi.fastnews.entity;

/**
 * Created by fish on 16-12-26.
 */

/*
"qrcode_uri": "http://xz-hongbao.img-cn-beijing.aliyuncs.com/       qrcode/201612/8266e.png",
        "invite_link": "http://yzzc8.applinzi.com/user/invite/5",
        "invite_rule":  "邀请有效好友加入，奖励2元／位，\n有效好友定义：下载打开应用并转发一篇文章到朋友圈。"
 */
public class InviteResp {
    private String qrcode_uri;
    private String invite_link;
    private String invite_rule;

    public String getQrcode_uri() {
        return qrcode_uri;
    }

    public void setQrcode_uri(String qrcode_uri) {
        this.qrcode_uri = qrcode_uri;
    }

    public String getInvite_link() {
        return invite_link;
    }

    public void setInvite_link(String invite_link) {
        this.invite_link = invite_link;
    }

    public String getInvite_rule() {
        return invite_rule;
    }

    public void setInvite_rule(String invite_rule) {
        this.invite_rule = invite_rule;
    }
}
