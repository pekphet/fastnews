package com.youzi.fastnews.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.youzi.fastnews.App;
import com.youzi.fastnews.R;
import com.youzi.fastnews.entity.YUEResp;
import com.youzi.fastnews.net.INetCallback;


/**
 * Created by ywb on 2016/12/23.
 *
 * 提现申请界面
 *
 */

public class ApplyWithdrawalsDetailsActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_withdrawals_details);
        ImageView des_back = (ImageView) findViewById(R.id.des_back);
        TextView apply_status = (TextView) findViewById(R.id.apply_status);
        TextView des = (TextView) findViewById(R.id.des);
        Button bbtn = (Button) findViewById(R.id.bbtn);


        des_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
