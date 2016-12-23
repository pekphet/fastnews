package com.youzi.fastnews.activity;

import android.os.Bundle;

import com.youzi.fastnews.App;
import com.youzi.fastnews.entity.NewsListResp;
import com.youzi.fastnews.net.INetCallback;

import cc.fish.coreui.BaseSplashActivity;
import cc.fish.coreui.annotation.Splash;

@Splash(clz = HomeActivity.class, delay = 300000)
public class SplashActivity extends BaseSplashActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getNetManager().loadNewsList(1, new INetCallback<NewsListResp>() {
            @Override
            public void Success(NewsListResp newsListResp) {
                newsListResp.getRows();
            }

            @Override
            public void Failed(String msg) {
                msg.getBytes();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
