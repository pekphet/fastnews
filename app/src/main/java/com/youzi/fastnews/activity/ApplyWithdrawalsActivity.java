package com.youzi.fastnews.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.youzi.fastnews.App;
import com.youzi.fastnews.R;
import com.youzi.fastnews.entity.YUEResp;
import com.youzi.fastnews.net.INetCallback;
import com.youzi.fastnews.utils.ZToast;

import static com.youzi.fastnews.R.id.yu_e;

/**
 * Created by ywb on 2016/12/23.
 *
 * 提现界面
 */

public class ApplyWithdrawalsActivity extends Activity {

    private String moneyss;
    private TextView mYu_e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_withdrawals);

        ImageView im = (ImageView) findViewById(R.id.apply_back);
        EditText money = (EditText) findViewById(R.id.money);
        mYu_e = (TextView) findViewById(yu_e);
        Button btn = (Button) findViewById(R.id.btn);

        moneyss = money.getText().toString();

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn.setOnClickListener(vv-> {
            if (money.getText().toString() == "") {
                ZToast.r(this, "请输入金额");
            }
            App.getNetManager().tx(new INetCallback<String>() {
                @Override
                public void Success(String s) {
                    ZToast.r(ApplyWithdrawalsActivity.this, s);
                    flushYUE();
                    finish();
                }

                @Override
                public void Failed(String msg) {
                    ZToast.r(ApplyWithdrawalsActivity.this, msg);
                }
            }, money.getText().toString());
        });

        flushYUE();
    }

    private void flushYUE() {
        App.getNetManager().getYuE(new INetCallback<YUEResp>() {
            @Override
            public void Success(YUEResp yueResp) {
                mYu_e.setText(String.format("余额：%.2f元", yueResp.getMoney()));
            }

            @Override
            public void Failed(String msg) {
                mYu_e.setText("无法获取余额");
                ZToast.r(ApplyWithdrawalsActivity.this, msg);
            }
        });
    }
}
