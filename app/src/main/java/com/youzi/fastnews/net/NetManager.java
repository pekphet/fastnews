package com.youzi.fastnews.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.youzi.fastnews.App;
import com.youzi.fastnews.Constants;
import com.youzi.fastnews.entity.LoginE;
import com.youzi.fastnews.entity.LoginRespD;
import com.youzi.fastnews.entity.NewsDResp;
import com.youzi.fastnews.entity.NewsDRespD;
import com.youzi.fastnews.entity.NewsListResp;
import com.youzi.fastnews.entity.NewsListRespD;
import com.youzi.fastnews.entity.RegisterResponseEntity;
import com.youzi.fastnews.entity.ResponseWechatLoginEntity;
import com.youzi.fastnews.entity.ResponseWechatUserInfoEntity;
import com.youzi.fastnews.global.WechatConstants;
import com.youzi.fastnews.utils.DeviceUtils;

import cc.fish.fishhttp.net.RequestHelper;

import static com.youzi.fastnews.global.WechatConstants.REQUEST_HEADIMAGE_URL;
import static com.youzi.fastnews.global.WechatConstants.REQUEST_PARAM_REGISTER_NICKNAME;
import static com.youzi.fastnews.global.WechatConstants.REQUEST_PARAM_REGISTER_OPENID;
import static com.youzi.fastnews.global.WechatConstants.REQUEST_PARAM_REGISTER_SEX;
import static com.youzi.fastnews.global.WechatConstants.REQUEST_PARAM_REGISTER_UNIONID;
import static com.youzi.fastnews.global.WechatConstants.REQUEST_PARAM_REGISTER_UNIQUID;
import static com.youzi.fastnews.global.WechatConstants.REQUEST_PARENT_ID;

/**
 * Created by fish on 16-12-23.
 */
public class NetManager implements Constants {
    private static NetManager instance = null;
    private Context mContext = null;
    private Handler mHandler = new Handler(Looper.myLooper());

    public static NetManager getInstance(Context c) {
        if (instance == null) {
            instance = new NetManager();
        }
        instance.mContext = c;
        return instance;
    }

    private NetManager() {
    }

    public void loadNewsList(int parentId, INetCallback<NewsListResp> callback) {
        new RequestHelper<NewsListRespD>().Method(RequestHelper.Method.GET)
                .Url(NEWS_LIST_URL)
                .Result(NewsListRespD.class)
                .UrlParam("parent_id", parentId + "", true)
                .Success(result -> {
                    if (((NewsListRespD) result).getCode() != 0) {
                        callback.Failed(((NewsListRespD) result).getMsg());
                    } else {
                        NewsListResp data = ((NewsListRespD) result).getData();
                        callback.Success(data);
                    }
                })
                .Failed(msg -> callback.Failed((String) msg))
                .get(mContext, mHandler);
    }

    public void loadNewsCategory(int parentId, int cId, int page, INetCallback<NewsDResp> callback) {
        new RequestHelper<NewsDRespD>().Method(RequestHelper.Method.GET)
                .Url(NEWS_CATEGORY_URL)
                .Result(NewsListRespD.class)
                .UrlParam("page", page + "", true)
                .UrlParam("category_id", parentId + "")
                .UrlParam("category_id2", cId + "")
                .Result(NewsDRespD.class)
                .UrlParam("page", page + "", true)
                .UrlParam("category_id", parentId + "")
                .UrlParam("category_id2", cId + "")
                .Success(result -> {
                    if (((NewsDRespD) result).getCode() != 0) {
                        callback.Failed(((NewsDRespD) result).getMsg());
                    } else {
                        NewsDResp data = ((NewsDRespD) result).getData();
                        callback.Success(data);
                    }
                })
                .Failed(msg -> callback.Failed((String) msg))
                .get(mContext, mHandler);
    }


    /**
     * @param callback 微信获取 ACCESS_TOKEN 接口
     */
    public void loadWecahtAccessTokenRequest(INetCallback<ResponseWechatLoginEntity> callback, String GET_REQUEST_ACCESS_TOKEN) {
        new RequestHelper<ResponseWechatLoginEntity>().Method(RequestHelper.Method.GET)
                .Url(GET_REQUEST_ACCESS_TOKEN)
                .Success(result -> {

                    String errcode = ((ResponseWechatLoginEntity) result).getErrcode();
                    String errmsg = ((ResponseWechatLoginEntity) result).getErrmsg();

                    if (!WechatConstants.GET_REQUEST_ACCESS_TOKEN_Errcode.equals(errcode)) {

                        callback.Success(((ResponseWechatLoginEntity) result));

                    } else {
                        callback.Failed(((ResponseWechatLoginEntity) result).getErrmsg());
                    }
                }).Failed(msg -> callback.Failed((String) msg))
                .get(mContext, mHandler);

    }


    /**
     * @param callback 微信获取 用户信息 Openid Unionid Nickname Sex Headimgurl接口
     */
    public void loadWecahtUserInfoRequest(INetCallback<ResponseWechatUserInfoEntity> callback, String GET_REQUEST_USERINFO) {
        new RequestHelper<ResponseWechatLoginEntity>().Method(RequestHelper.Method.GET)
                .Url(GET_REQUEST_USERINFO)
                .Success(result -> {

                    String errcode = ((ResponseWechatUserInfoEntity) result).getErrcode();
                    String errmsg = ((ResponseWechatUserInfoEntity) result).getErrmsg();

                    if (!WechatConstants.GET_REQUEST_ACCESS_TOKEN_Errcode.equals(errcode)) {

                        callback.Success(((ResponseWechatUserInfoEntity) result));

                    } else {
                        callback.Failed(((ResponseWechatUserInfoEntity) result).getErrmsg());
                    }
                }).Failed(msg -> callback.Failed((String) msg))
                .get(mContext, mHandler);
    }

    public void loginIn(INetCallback<RegisterResponseEntity> callback, String accessToken, String openId, String unionId, String nickname, String sex, String headimgurl, String parentID) {
        new RequestHelper<ResponseWechatLoginEntity>().Method(RequestHelper.Method.POST)
                .Url("http: //60.205.58.24: 8084/api/user/wechat_login")
                .PostParam(REQUEST_PARAM_REGISTER_OPENID, accessToken)
                .PostParam(REQUEST_PARAM_REGISTER_OPENID, openId)
                .PostParam(REQUEST_PARAM_REGISTER_UNIONID, unionId)
                .PostParam(REQUEST_PARAM_REGISTER_UNIQUID, DeviceUtils.getIMEI(mContext))
                .PostParam(REQUEST_PARAM_REGISTER_NICKNAME, nickname)
                .PostParam(REQUEST_PARAM_REGISTER_SEX, sex)
                .PostParam(REQUEST_HEADIMAGE_URL, headimgurl)
                .PostParam(REQUEST_PARENT_ID, parentID)
                .Success(result -> {
                    if (((RegisterResponseEntity) result).getCode() == 0) {
                        App.setToken(((RegisterResponseEntity) result).getData().getCode() + "");
                        callback.Success((RegisterResponseEntity) result);
                    } else {
                        callback.Failed(((RegisterResponseEntity) result).getMsg());
                    }
                }).Failed(msg -> callback.Failed((String) msg)).post(mContext, mHandler);
    }

    public void loadNewsCategory(INetCallback<NewsDResp> callback) {
        new RequestHelper<NewsDRespD>().Method(RequestHelper.Method.GET)
                .Url(NEWS_CATEGORY_URL)
                .Result(NewsDRespD.class)
                .Success(result -> {
                    if (((NewsDRespD) result).getCode() != 0) {
                        callback.Failed(((NewsDRespD) result).getMsg());
                    } else {
                        NewsDResp data = ((NewsDRespD) result).getData();
                        callback.Success(data);
                    }
                })
                .Failed(msg -> callback.Failed((String) msg))
                .get(mContext, mHandler);
    }

    @Deprecated
    public void login(String accessToken, String openId, String parentId, String nickName, String headimgurl, INetCallback<LoginE> callback) {
        new RequestHelper<LoginRespD>().Method(RequestHelper.Method.POST)
                .Url(LOGIN_URL)
                .Result(LoginRespD.class)
                .PostParam("access_token", accessToken, true)
                .PostParam("openid", openId)
                .PostParam("uniqueid", DeviceUtils.getIMEI(mContext))
                .PostParam("nickname", nickName)
                .PostParam("headimgurl", headimgurl)
                .PostParam("parent_id", parentId)
                .Success(result -> {
                    if (((LoginRespD) result).getCode() != 0) {
                        callback.Failed(((LoginRespD) result).getMsg());
                    }
                    callback.Success(((LoginRespD) result).getData());
                    App.setToken(((LoginRespD) result).getData().getCode());
                })
                .Failed(MSG -> callback.Failed((String) MSG))
                .post(mContext, mHandler);

    }


}
