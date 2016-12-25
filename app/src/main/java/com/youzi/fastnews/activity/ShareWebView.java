package com.youzi.fastnews.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.youzi.fastnews.App;
import com.youzi.fastnews.R;
import com.youzi.fastnews.global.WechatConstants;
import com.youzi.fastnews.utils.PopWindowDisplayUtil;
import com.youzi.fastnews.utils.WechatUtils;

/**
 * Created by fish on 16-12-23.
 */

public class ShareWebView extends Activity {

    private WebView mWb;
    private Button mBtn;
    private Button mBtnRt;
    private Button mBtnFr;
    private String sUrl;
    private String sCon;
    private String sDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_shw);
        mWb = (WebView) findViewById(R.id.web);
        mBtn = (Button) findViewById(R.id.btn_share);
        mBtnRt = (Button) findViewById(R.id.btn_retn);
        mBtnFr = (Button) findViewById(R.id.btn_s2fr);

        sUrl = getIntent().getStringExtra("S-URL");
        sCon = getIntent().getStringExtra("S-CON");
        sDes = getIntent().getStringExtra("S-DES");

        initWebView();
        mWb.loadUrl(getIntent().getStringExtra("URL"));
        mBtn.setOnClickListener(v->startActivity(new Intent(this, ShareActivity.class)));
        mBtnRt.setOnClickListener(v->finish());
        mBtnFr.setOnClickListener(v->sent2FR());

    }

    private void sent2FR() {
        WechatUtils.wechatShare(this, App.iWXAPI, WechatConstants.WXSceneTimeline, sUrl, sCon, sDes);

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

    public static void startWebActivity(Context c, String url, String shareUrl, String shareContent, String shareDes) {
        Intent i = new Intent(c, ShareWebView.class);
        i.putExtra("URL", url);
        i.putExtra("S-URL", shareUrl);
        i.putExtra("S-CON", shareContent);
        i.putExtra("S-DES", shareDes);

        c.startActivity(i);
    }
}
