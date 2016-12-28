package com.youzi.fastnews.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.umeng.analytics.MobclickAgent;
import com.youzi.fastnews.R;

public class SplashActivity extends Activity {

    private Handler myHandler = new Handler(Looper.getMainLooper());
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_s);
        myHandler.postDelayed(() -> {
            intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
