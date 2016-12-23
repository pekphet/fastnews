package com.youzi.fastnews.activity;

import android.os.Bundle;

import cc.fish.coreui.BaseSplashActivity;
import cc.fish.coreui.annotation.Splash;

@Splash(clz = HomeActivity.class, delay = 3000)
public class SplashActivity extends BaseSplashActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
