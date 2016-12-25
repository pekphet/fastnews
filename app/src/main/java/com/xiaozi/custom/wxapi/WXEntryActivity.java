package com.xiaozi.custom.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.youzi.fastnews.App;
import com.youzi.fastnews.activity.ImmediatelyLoginActivity;
import com.youzi.fastnews.entity.RegisterResponseEntity;
import com.youzi.fastnews.entity.ResponseWechatLoginEntity;
import com.youzi.fastnews.entity.ResponseWechatUserInfoEntity;
import com.youzi.fastnews.global.WechatConstants;
import com.youzi.fastnews.net.INetCallback;
import com.youzi.fastnews.utils.ZToast;

import cc.fish.fishhttp.util.ZLog;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    private String code;
    private String accessToken;
    private String openID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, WechatConstants.APP_ID);
        api.handleIntent(getIntent(), this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }


    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();
                break;
            case ConstantsAPI.COMMAND_LAUNCH_BY_WX:
                Toast.makeText(this, "6", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {

        switch (resp.getType()) {

            case ConstantsAPI.COMMAND_SENDAUTH://登录回调

                if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
                    code = ((SendAuth.Resp) resp).code;
                    App.getNetManager().loadWecahtAccessTokenRequest(mWechatLoginIAsyncFresher, getTokenRequest(code));
                } else if (resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {//用户取消
                    ZToast.r(WXEntryActivity.this, "用户取消");
                    ZLog.e("LoginError", "用户取消");
                    finish();
                } else if (resp.errCode == BaseResp.ErrCode.ERR_COMM) {//一般错误
                    ZToast.r(WXEntryActivity.this, "一般错误");
                    ZLog.e("LoginError", "一般错误");
                    finish();
                } else if (resp.errCode == BaseResp.ErrCode.ERR_AUTH_DENIED) {//认证被否决
                    ZToast.r(WXEntryActivity.this, "认证被否决");
                    ZLog.e("LoginError", "认证被否决");
                    finish();
                } else if (resp.errCode == BaseResp.ErrCode.ERR_SENT_FAILED) {//发送失败
                    ZToast.r(WXEntryActivity.this, "发送失败");
                    ZLog.e("LoginError", "发送失败");
                    finish();
                } else if (resp.errCode == BaseResp.ErrCode.ERR_UNSUPPORT) {//不支持错误
                    ZToast.r(WXEntryActivity.this, "不支持错误");
                    ZLog.e("LoginError", "不支持错误");
                    finish();
                } else {
                    ZToast.r(WXEntryActivity.this, "默认错误");
                    ZLog.e("LoginError", "默认错误");
                    finish();
                }


                break;
            case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX://分享回调
                if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
                    App.getNetManager().clkZF(App.sCFID);
                    ZToast.r(WXEntryActivity.this, "恭喜您，分享成功");
                    finish();
                } else if (resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {//用户取消
                    ZToast.r(WXEntryActivity.this, "用户取消");
                    ZLog.e("LoginError", "用户取消");
                    finish();
                } else if (resp.errCode == BaseResp.ErrCode.ERR_COMM) {//一般错误
                    ZToast.r(WXEntryActivity.this, "一般错误");
                    ZLog.e("LoginError", "一般错误");
                    finish();
                } else if (resp.errCode == BaseResp.ErrCode.ERR_AUTH_DENIED) {//认证被否决
                    ZToast.r(WXEntryActivity.this, "认证被否决");
                    ZLog.e("LoginError", "认证被否决");
                    finish();
                } else if (resp.errCode == BaseResp.ErrCode.ERR_SENT_FAILED) {//发送失败
                    ZToast.r(WXEntryActivity.this, "发送失败");
                    ZLog.e("LoginError", "发送失败");
                    finish();
                } else if (resp.errCode == BaseResp.ErrCode.ERR_UNSUPPORT) {//不支持错误
                    ZToast.r(WXEntryActivity.this, "不支持错误");
                    ZLog.e("LoginError", "不支持错误");
                    finish();
                } else {
                    ZToast.r(WXEntryActivity.this, "默认错误");
                    ZLog.e("LoginError", "默认错误");
                    finish();
                }
                break;


        }

    }


    /**
     * 获取根据CODE得到的微信授权Token的Get请求地址
     *
     * @param code
     * @return
     */
    private static String getTokenRequest(String code) {
        String tokenRequest = WechatConstants.GET_REQUEST_ACCESS_TOKEN.replace("APPID", WechatConstants.APP_ID).
                replace("SECRET", WechatConstants.App_Secret).
                replace("CODE", code);
        return tokenRequest;
    }


    /**
     * 获取根据AccessToken得到的微信授权Userinfo的Get请求地址
     */
    private static String getUserInfoRequest(String accessToken, String openID) {
        String userInfoRequest = WechatConstants.GET_REQUEST_USER_INFO.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openID);
        return userInfoRequest;
    }

    private INetCallback<ResponseWechatLoginEntity> mWechatLoginIAsyncFresher = new INetCallback<ResponseWechatLoginEntity>() {
        @Override
        public void Success(ResponseWechatLoginEntity entity) {
            if (entity != null) {
                Log.e("ENTITY", entity.toString());
                accessToken = entity.getAccess_token();
                openID = entity.getOpenid();
                App.getNetManager().loadWecahtUserInfoRequest(mWechatUserinfoIAsyncFresher, getUserInfoRequest(accessToken, openID));

            }
        }

        @Override
        public void Failed(String errorMsg) {
            ZToast.r(WXEntryActivity.this, errorMsg);
        }
    };

    private INetCallback<ResponseWechatUserInfoEntity> mWechatUserinfoIAsyncFresher = new INetCallback<ResponseWechatUserInfoEntity>() {

        @Override
        public void Success(ResponseWechatUserInfoEntity entity) {
            if (entity != null) {
//                Toast.makeText(WXEntryActivity.this, entity.toString(), Toast.LENGTH_SHORT).show();
//                Log.e("WechatUserInfoEntity", "WechatUserInfoEntity:" + entity.toString());
                if (!TextUtils.isEmpty(accessToken)) {


                    App.getNetManager().loginIn(mRegistIAsyncFresher, accessToken, entity.getOpenid(), entity.getUnionid(), entity.getNickname(), entity.getSex() + "", entity.getHeadimgurl(), "0");

                }

            }
        }

        @Override
        public void Failed(String errorMsg) {
            ZToast.r(WXEntryActivity.this, errorMsg);
        }
    };


    private INetCallback<RegisterResponseEntity> mRegistIAsyncFresher = new INetCallback<RegisterResponseEntity>() {

        @Override
        public void Success(RegisterResponseEntity entity) {
            finish();
            ZLog.e ("callback", "login success");
            ImmediatelyLoginActivity.loginSucc();
        }

        @Override
        public void Failed(String errorMsg) {
            ZToast.r(WXEntryActivity.this, errorMsg);
        }
    };

}