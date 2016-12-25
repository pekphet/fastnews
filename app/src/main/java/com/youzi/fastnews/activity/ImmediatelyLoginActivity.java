package com.youzi.fastnews.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.youzi.fastnews.App;
import com.youzi.fastnews.R;
import com.youzi.fastnews.utils.WechatUtils;


/**
 * Created by ywb on 2016/12/25.
 */

public class ImmediatelyLoginActivity extends Activity {


    private LinearLayout loginss_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immediately);

        loginss_layout = (LinearLayout) findViewById(R.id.loginss_layout);

        loginss_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WechatUtils.wechatLogin(App.iWXAPI);
            }
        });

    }
}
