package com.youzi.fastnews.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.youzi.fastnews.R;

/**
 * Created by Pekphet on 16/12/24.
 */

public class InviteRankActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_inv_r);
        TextView tvHead = (TextView) findViewById(R.id.tv_head);
        tvHead.setText("邀请好友排行榜");
        findViewById(R.id.btn_ret).setOnClickListener(bv->finish());
    }
}
