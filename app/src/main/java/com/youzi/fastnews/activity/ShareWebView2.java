package com.youzi.fastnews.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.youzi.fastnews.App;
import com.youzi.fastnews.R;
import com.youzi.fastnews.entity.BaseResp;
import com.youzi.fastnews.global.WechatConstants;
import com.youzi.fastnews.net.INetCallback;
import com.youzi.fastnews.utils.WechatUtils;
import com.youzi.fastnews.utils.ZToast;

/**
 * Created by fish on 16-12-27.
 */

public class ShareWebView2 extends Activity {
    private WebView mWb;
    private TextView mBtn;
    private ImageButton mBtnRt;
    private TextView mBtnFr;
    private String sUrl;
    private String sCon;
    private String sDes;
    private int mCat1;
    private int mCat2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_shw);
        mWb = (WebView) findViewById(R.id.web);
        mBtn = (TextView) findViewById(R.id.btn_share);
        mBtnRt = (ImageButton) findViewById(R.id.btn_retn);
        mBtnFr = (TextView) findViewById(R.id.btn_s2fr);

        initWebView();
        mCat1 = getIntent().getIntExtra("CATEGORY1", -1);
        mCat2 = getIntent().getIntExtra("CATEGORY2", -1);

        mWb.loadUrl(getIntent().getStringExtra("URL"));
        mBtn.setOnClickListener(v->startActivity(new Intent(this, ShareActivity.class)));
        mBtnRt.setOnClickListener(v->finish());
        mBtnFr.setOnClickListener(v->sent2FR());

        loadShareUrl();
    }

    private void loadShareUrl() {
        mBtnFr.setText("正在获取分享连接");
        mBtnFr.setEnabled(false);
        App.getNetManager().transUrl(getIntent().getStringExtra("URL"), mCat1, mCat2, new INetCallback<BaseResp>() {
            @Override
            public void Success(BaseResp baseResp) {
                mBtnFr.setEnabled(true);
                mBtnFr.setText("转发到朋友圈");
            }

            @Override
            public void Failed(String msg) {
                ZToast.r(ShareWebView2.this, msg);
                mBtnFr.setText("获取分享连接失败");
            }
        });
    }

    private void sent2FR() {
        //PopWindowDisplayUtil.showSharePopWindow(this, "分享", sUrl, sCon, sDes, mBtnFr);
        WechatUtils.wechatShare(this, App.iWXAPI, WechatConstants.WXSceneTimeline, sUrl.replace("{logged_token}", App.getToken()), sCon, sDes);
    }

    private void initWebView() {
        WebSettings settings = mWb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    public static void startWeb2Activity(Context c, String url, int c1, int c2) {
        Intent i = new Intent(c, ShareWebView2.class);
        i.putExtra("URL", url);
        i.putExtra("CATEGORY1", c1);
        i.putExtra("CATEGORY2", c2);
        c.startActivity(i);
    }
}
