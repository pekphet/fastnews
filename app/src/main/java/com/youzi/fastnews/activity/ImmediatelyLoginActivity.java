package com.youzi.fastnews.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.youzi.fastnews.App;
import com.youzi.fastnews.R;
import com.youzi.fastnews.utils.WechatUtils;


/**
 * Created by ywb on 2016/12/25.
 */

public class ImmediatelyLoginActivity extends Activity {


    private Button loginss_layout;
    private static Activity self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immediately);
        self = this;

        loginss_layout = (Button) findViewById(R.id.loginss_layout);

        loginss_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WechatUtils.wechatLogin(App.iWXAPI);
            }
        });

    }

    public static void loginSucc() {
        self.startActivity(new Intent(self, HomeActivity.class));
        self.finish();
    }

    public static void doLogin(Context context) {
        Intent intent = new Intent(context, ImmediatelyLoginActivity.class);
        context.startActivity(intent);
    }
}
