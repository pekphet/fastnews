package com.youzi.fastnews.wxapi;

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

    /*

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
        <link rel="dns-prefetch" href="//gss1.bdstatic.com">
        <link rel="dns-prefetch" href="//gss2.bdstatic.com">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <link rel="shortcut icon" href="//gss1.bdstatic.com/9aQTbzqaKgQFm2e88IuM_a/static/20170105115730/favicon.ico" type="image/x-icon" />
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
        <meta name="format-detection" content="telephone=no">
        <title>        一首《红颜劫》唱一次哭一次 听得人心酸 用生命演唱的歌者 心中永远的冠军
</title>

        <script src="//gss1.bdstatic.com/9aQTbzqaKgQFm2e88IuM_a/static/20170105115730/js/common/coreMobile.js"></script>
        <!--  -->
        <script>
            var GLOBAL_CONF = { channel: {"baseResponse":{"code":200,"msg":"success"},"channelId":1033,"channelName":"视频","contentStyleType":"video","contentTypes":["video"],"id":"1033","appid":"abbf9c07","contentId":"8446632"}, global: {"debug":false,"passport":{"host":"wappass.baidu.com","tpl":"baidu_cpu"}}, detail: {"showRtnHome":1,"detailAdsExp":1,"showSource":1,"showFeedback":1,"showRecommend":1,"showComment":0,"showOpenboxBanner":1} };
            require.config({
        'waitSeconds': 30,
        'baseUrl': '//gss1.bdstatic.com/9aQTbzqaKgQFm2e88IuM_a/static/20170105115730/js',
        'paths': {
            'weixin': '//res.wx.qq.com/open/js/jweixin-1.0.0',
            'openBox': '//s.bdstatic.com/common/openjs/openBox',
            'videojs': 'common/videojs/dist/video'
        },
        'shim': { 'openBox': { 'exports': 'OpenBox' } }
    });
            if (location.href.indexOf('cpu.baidu.com') !== -1) {
                var _hmt = _hmt || [];
                if ('' === 'true') {
                    _hmt.push(['_setAutoPageview', false]);
                }

                deployBaiduTJ('7e45c71c9fc634f2b7f1555da47b0b3f');
            }

            (function () {
                var fontSize = document.documentElement.clientWidth / 20 - 2;
                document.documentElement.style.fontSize = fontSize > 16 ? '16px' : fontSize + 'px';
            }());

        </script>
<script src="/blank"></script>
<script>
var _sst = [];
    _sst.push(['_setCustomVar', 'page_id', 50002]);
_sst.push(['_setCustomVar', 'app_id', 'abbf9c07']);
_sst.push(['_setCustomVar', 'channel_id', '1033']);
_sst.push(['_setCustomVar', 'video_id', 'C8446632']);
_sst.push(['_setCustomVar', 'content_id', 'C8446632']);

_sst.push(['_setCustomVar', 'content_type', 'video']);
_sst.push(['_setCustomVar', 'block_id', '']);

_sst.push(['_setCustomVar', 'position_id', '0']);
_sst.push(['_setAutoPageview', false]);
</script>

        <script>
        (function() {
            var t = document.createElement('script'),
            s = document.getElementsByTagName('script')[0];
            t.async = true;
            t.id = 'rsst';
            t.setAttribute('data-rsst-site', '1');
            t.src = '//gss1.bdstatic.com/9aQTbzqaKgQFm2e88IuM_a/static/20170105115730/js/stat/st.js';
            s.parentNode.insertBefore(t, s);
        })();
        </script>

<script>
    window.alogObjectConfig = {
        sample: '0.01',

        product: '682',
        page: '682_7',
        monkey_page: '',
        speed_page: '',

        speed: {
            sample: '1'
        },

    };

    void function(a,b,c,d,e,f,g){a.alogObjectName=e,a[e]=a[e]||function(){(a[e].q=a[e].q||[]).push(arguments)},a[e].l=a[e].l||+new Date,d="https:"===a.location.protocol?"https://fex.bdstatic.com"+d:"http://fex.bdstatic.com"+d;var h=!0;if(a.alogObjectConfig&&a.alogObjectConfig.sample){var i=Math.random();a.alogObjectConfig.rand=i,i>a.alogObjectConfig.sample&&(h=!1)}h&&(f=b.createElement(c),f.async=!0,f.src=d+"?v="+~(new Date/864e5)+~(new Date/864e5),g=b.getElementsByTagName(c)[0],g.parentNode.insertBefore(f,g))}(window,document,"script","/hunter/alog/alog.mobile.min.js","alog"),void function(){function a(){}window.PDC={mark:function(a,b){alog("speed.set",a,b||+new Date),alog.fire&&alog.fire("mark")},init:function(a){alog("speed.set","options",a)},view_start:a,tti:a,page_ready:a}}();
    void function(n){var o=!1;n.onerror=function(n,e,t,c){var i=!0;return!e&&/^script error/i.test(n)&&(o?i=!1:o=!0),i&&alog("exception.send","exception",{msg:n,js:e,ln:t,col:c}),!1},alog("exception.on","catch",function(n){alog("exception.send","exception",{msg:n.msg,js:n.path,ln:n.ln,method:n.method,flag:"catch"})})}(window);

    // 处理API过来的详情参数，主要是把设备相关信息存到cookie里并repalceState
    (function () {
        // get search array utility
        function getSearch() {
            var search = window.location.search;
            var params = {};
            if (!search) {
                return params;
            }
            var paramsArr = search.replace('?', '').split('&');
            paramsArr.forEach(function (item) {
                var itemArr = item.split('=');
                params[itemArr[0]] = itemArr[1] ? decodeURIComponent(itemArr[1]) : '';
            });
            return params;
        };

        function genSearch(obj) {
            if (!obj) {
                return '';
            }
            var pairs = [];
            for (var key in obj) {
                if (obj.hasOwnProperty(key)) {
                    pairs.push(encodeURIComponent(key) + '=' + encodeURIComponent(obj[key]));
                }
            }

            if (pairs.length) {
                return '?' + pairs.join('&');
            } else {
                return '';
            };
        };

        // Cookie utility
        function cookie(key, value, options) {
            var expiration, result, time;

            if (arguments.length > 1 && String(value) !== "[object Object]") {
                options = options || {};

                if (value === null || value === undefined) {
                    options.expires = -1;
                }

                if (typeof options.expires === 'number') {
                    expiration = options.expires;
                    time = options.expires = new Date();
                    time.setTime(time.getTime() + expiration * 24 * 60 * 60 * 1000);
                }

                return (document.cookie = [
                    encodeURIComponent(key),
                    '=',
                    encodeURIComponent(value),
                    options.expires ? '; expires=' + options.expires.toUTCString() : '',
                    options.path ? '; path=' + options.path : '',
                    options.domain ? '; domain=' + options.domain : '',
                    options.secure ? '; secure' : ''
                ].join(''));
            }

            result = new RegExp('(?:^|; )' + encodeURIComponent(key) + '=([^;]*)').exec(document.cookie);
            return result ? decodeURIComponent(result[1]) : null;
        };

        function syncToCookie(obj, name, isDelete) {
            if (obj && obj[name]) {
                cookie(name, obj[name], {
                    path: '/',
                    expires: 30
                });

                if (isDelete) {
                    delete obj[name]
                }
            }
        };

        var searchParams = getSearch();

        if (searchParams.cuid || searchParams.ia || searchParams.im || searchParams.aid) {
            syncToCookie(searchParams, 'cuid', true);
            syncToCookie(searchParams, 'ia', true);
            syncToCookie(searchParams, 'im', true);
            syncToCookie(searchParams, 'aid', true);

            var newSearchParam = genSearch(searchParams) + location.hash;
            if (history && history.replaceState) {
                history.replaceState({}, '', location.pathname + newSearchParam);
            }
        }
    })()
</script>
<link rel="stylesheet" href="//gss1.bdstatic.com/9aQTbzqaKgQFm2e88IuM_a/static/20170105115730/css/default/detail.css">
    </head>
    <body ontouchstart>
<script> alog('speed.set', 'ht', +new Date); </script>

<div class="scroll-touch-layout">
    <div class="layout video-layout page-layout">
        <div class="video-container">

            <!-- 视频播放部分 -->
            <div class="video-block">
                    <video id="video" class="video-js vjs-cu-skin vjs-big-play-centered" poster="//gss2.bdstatic.com/5K1IcD3801kUm2zgoZyCKT6zgBYnreHg-_/5500fdf5-c3f2-44c6-9124-df5c43b79e01.jpg@w_1104,h_622" controls data-setup='{}'>
                         <source src="//gss3.bdstatic.com/-b1Caiqa0d9Bmcmop9aC2jh9h2w8e4_h7sED0YQ_t9iCPK/mda-gkjzdmnwtifpm0k3/mda-gkjzdmnwtifpm0k3.mp4" type="video/mp4">
                         <p class="vjs-no-js">Your browser doesn't support HTML5 video tag</p>
                    </video>
                <div class="video-block-desc">
                    <p class="n-title">一首《红颜劫》唱一次哭一次 听得人心酸 用生命演唱的歌者 心中永远的冠军</p>
                    <div class="n-desc">
                        <span class="info">
                            <!-- <span></span> -->
                            <span class="n-ptime">4天前</span>
                        </span>
                    </div>
                </div>
            </div>

        </div>
        <!-- 视频推荐部分 -->
            <div id="recommend-wrap" class="recommend-block"></div>
    </div>
</div>

<div class="btns widget-btns scroll-hide">
    <!-- <a href="javascript:;" class="logo">广告</a> -->
    <a class="scroll-top" href="javascript:void(0);"><i class="icon icon-up"></i></a>
    <a class=" jump-index" href="javascript:void(0);" class="jump-index"><i class="icon icon-home"></i></a>
</div>
<div id="non-wifi">
    <div class="shadow"></div>
    <div id="modal">
        <div class="info">
            <p>您当前正在使用移动网络</p>
            <p>继续播放将消耗流量</p>
        </div>
        <div class="btn-wrapper">
            <a id="stop" href="javascript:;">停止播放</a>
            <a id="keep-on" href="javascript:;">继续播放</a>
        </div>
    <div>
</div>
<script>
$(function () {
    require(['default/detail-entry'], function(newsDetail) {
        newsDetail({
            contentId: 8446632,
            contentParams: {
                ownerId: '607159995' || '',
                ownerType: '1' || ''
            },
            iswap: + '0',
            contentType: 'video',
            connectionType: '0',
            title: '一首《红颜劫》唱一次哭一次 听得人心酸 用生命演唱的歌者 心中永远的冠军'
        });
    });
})
</script>

<script>
    void function(a,b,c,d,e,f){function g(b){a.attachEvent?a.attachEvent("onload",b,!1):a.addEventListener&&a.addEventListener("load",b)}function h(a,c,d){d=d||15;var e=new Date;e.setTime((new Date).getTime()+1e3*d),b.cookie=a+"="+escape(c)+";path=/;expires="+e.toGMTString()}function i(a){var c=b.cookie.match(new RegExp("(^| )"+a+"=([^;]*)(;|$)"));return null!=c?unescape(c[2]):null}function j(){var a=i("PMS_JT");if(a){h("PMS_JT","",-1);try{a=a.match(/{["']s["']:(\d+),["']r["']:["']([\s\S]+)["']}/),a=a&&a[1]&&a[2]?{s:parseInt(a[1]),r:a[2]}:{}}catch(c){a={}}a.r&&b.referrer.replace(/#.*/,"")!=a.r||alog("speed.set","wt",a.s)}}if(a.alogObjectConfig){var k=a.alogObjectConfig.sample,l=a.alogObjectConfig.rand;d="https:"===a.location.protocol?"https://fex.bdstatic.com"+d:"http://fex.bdstatic.com"+d,k&&l&&l>k||(g(function(){alog("speed.set","lt",+new Date),e=b.createElement(c),e.async=!0,e.src=d+"?v="+~(new Date/864e5)+~(new Date/864e5),f=b.getElementsByTagName(c)[0],f.parentNode.insertBefore(e,f)}),j())}}(window,document,"script","/hunter/alog/dp.mobile.min.js");
</script>
<script>
alog('speed.set', 'drt', +new Date);
</script>
</body>
</html>
<!--16135532410318963722010516-->
<script> var _trace_page_logid = 1613553241; </script>

     */

}