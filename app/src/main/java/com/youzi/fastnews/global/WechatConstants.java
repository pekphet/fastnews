package com.youzi.fastnews.global;

/**
 * Created by ywb on 2016/8/22.
 */
public class WechatConstants {

    public static final String APP_ID = "wx768d80d4915546bd";
    public static final String App_Secret = "bdd68f7209b7f3a09bdea280a933e439";

    public static final String GET_REQUEST_ACCESS_TOKEN =
            "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    public static final String GET_REQUEST_USER_INFO =
            "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

    public static final String GET_REQUEST_ACCESS_TOKEN_Errcode = "40029";

    public static final int WXSceneSession = 0;
    public static final int WXSceneTimeline = 1;


}
