package com.youzi.fastnews.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.youzi.fastnews.App;
import com.youzi.fastnews.R;
import com.youzi.fastnews.entity.PacRuleResp;
import com.youzi.fastnews.net.INetCallback;

/**
 * Created by fish on 17-1-4.
 */

public class PacActivity extends Activity {

    private TextView mTvCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_pac);
        TextView tvHead = (TextView) findViewById(R.id.tv_head);
        tvHead.setText("阅读寻宝");
        findViewById(R.id.btn_ret).setOnClickListener(bv->finish());
        mTvCon = (TextView) findViewById(R.id.tv_p_pac_ct);
        initContent();
    }

    private void initContent() {
        App.getNetManager().getPacRule(new INetCallback<PacRuleResp>() {
            @Override
            public void Success(PacRuleResp pacRuleResp) {
                mTvCon.setText(pacRuleResp.getRule());
            }
            @Override
            public void Failed(String msg) {
            }
        });
    }
}
