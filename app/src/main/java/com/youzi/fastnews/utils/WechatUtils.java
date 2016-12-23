package com.youzi.fastnews.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.youzi.fastnews.R;

/**
 * Created by ywb on 2016/8/23.
 * <p>
 * 微信 登录 支付 分享 工具类
 */
public class WechatUtils {

    /**
     * IWXAPI初始化
     * */

    /*private IWXAPI api;
    api=WXAPIFactory.createWXAPI(this,WechatConstants.APP_ID);

    if(!api.isWXAppInstalled())

    {
        Toast.makeText(this, "请先安装微信应用", Toast.LENGTH_SHORT).show();
    }

    api.registerApp(WechatConstants.APP_ID);*/

    /**
     * 微信分享
     * <p>
     * IWXAPI 微信api初始化
     * mContext
     * flag 分享值好友还是朋友圈
     */

    public static void wechatShare(Context mContext, IWXAPI api, int flag, String shareUrl, String shareTitle, String shareCotent) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = shareUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = shareTitle;
        msg.description = shareCotent;

        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

    /**
     * 微信登陆
     */

    public static void wechatLogin(IWXAPI api) {

        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);

    }

    /**
     * 微信支付
     */
    public static void wechatPay(IWXAPI api, String appId, String partnerId,
                                 String prepayId, String packageValue,
                                 String nonceStr, String timeStamp, String sign) {

        if (api != null) {
            if (api.isWXAppInstalled()) {
                PayReq request = new PayReq();
                request.appId = appId;
                request.partnerId = partnerId;
                request.prepayId = prepayId;
                request.packageValue = packageValue;
                request.nonceStr = nonceStr;
                request.timeStamp = timeStamp;
                request.sign = sign;
                api.sendReq(request);
            }
        }


    }

}
