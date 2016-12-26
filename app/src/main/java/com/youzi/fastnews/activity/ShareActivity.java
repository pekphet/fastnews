package com.youzi.fastnews.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.youzi.fastnews.App;
import com.youzi.fastnews.R;
import com.youzi.fastnews.entity.ShareRuleResp;
import com.youzi.fastnews.net.INetCallback;

/**
 * Created by Pekphet on 16/12/24.
 */

public class ShareActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_sh);
        TextView tvHead = (TextView) findViewById(R.id.tv_head);
        TextView tvRule = (TextView) findViewById(R.id.tv_rule);
        TextView tvGo = (TextView) findViewById(R.id.tv_go);
        tvGo.setOnClickListener(v-> {
            finish();
            HomeActivity.sel(0);
        });
        tvHead.setText("分享文章");
        findViewById(R.id.btn_ret).setOnClickListener(bv->finish());
        App.getNetManager().getShRule(new INetCallback<ShareRuleResp>() {
            @Override
            public void Success(ShareRuleResp shareRuleResp) {
                tvRule.setText(shareRuleResp.getRule());
            }

            @Override
            public void Failed(String msg) {

            }
        });

    }
}
