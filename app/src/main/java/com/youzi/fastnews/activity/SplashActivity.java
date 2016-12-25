package com.youzi.fastnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.youzi.fastnews.App;

import cc.fish.coreui.BaseSplashActivity;

public class SplashActivity extends BaseSplashActivity {

    private Handler myHandler = new Handler(Looper.getMainLooper());
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myHandler.postDelayed(() -> {

            if (!TextUtils.isEmpty(App.getToken())) {

                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);

            } else {
                intent = new Intent(this, ImmediatelyLoginActivity.class);
                startActivity(intent);
            }

        }, 3000);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
