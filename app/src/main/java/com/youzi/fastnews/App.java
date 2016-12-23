package com.youzi.fastnews;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.youzi.fastnews.global.WechatConstants;
import com.youzi.fastnews.net.NetManager;

import ac.fish.utils.adcore.ADUtils;

/**
 * Created by fish on 16-12-21.
 */

public class App extends Application {

    private static NetManager mNetManager;
    private static String token = null;

    private static Context mAppContext;

    public static IWXAPI iWXAPI;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppContext();
        initComponents();
        /**初始化*/
        initIWXAPI();
    }

    private void initAppContext() {
        mAppContext = getApplicationContext();
    }

    private void initComponents() {
        ADUtils.init(this);
        mNetManager = NetManager.getInstance(this);
    }

    public static NetManager getNetManager() {
        return mNetManager;
    }

    public static String getToken() {
        if (token == null) {
            SharedPreferences sp = mAppContext.getSharedPreferences("token", MODE_PRIVATE);
            token = sp.getString("token", "");
        }
        return token;
    }

    public static void setToken(String Token) {
        token = Token;
        SharedPreferences sp = mAppContext.getSharedPreferences("token", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString("token", Token);
        e.commit();
    }

    private void initIWXAPI() {

        iWXAPI = WXAPIFactory.createWXAPI(this, WechatConstants.APP_ID);

        if (!iWXAPI.isWXAppInstalled())

        {
            Toast.makeText(this, "请先安装微信应用", Toast.LENGTH_SHORT).show();
        }

        iWXAPI.registerApp(WechatConstants.APP_ID);


    }
}
