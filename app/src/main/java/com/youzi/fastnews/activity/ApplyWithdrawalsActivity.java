package com.youzi.fastnews.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.youzi.fastnews.R;

/**
 * Created by ywb on 2016/12/23.
 *
 * 提现界面
 */

public class ApplyWithdrawalsActivity extends Activity {

    private String moneyss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_withdrawals);

        ImageView im = (ImageView) findViewById(R.id.apply_back);
        EditText money = (EditText) findViewById(R.id.money);
        TextView yu_e = (TextView) findViewById(R.id.yu_e);
        Button btn = (Button) findViewById(R.id.btn);

        moneyss = money.getText().toString();

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
