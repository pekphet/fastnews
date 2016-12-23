package com.youzi.fastnews.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.youzi.fastnews.R;
import com.youzi.fastnews.utils.PopWindowDisplayUtil;

/**
 * Created by fish on 16-12-23.
 */

public class ShareWebView extends Activity {

    private WebView mWb;
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_shw);
        mWb = (WebView) findViewById(R.id.web);
        mBtn = (Button) findViewById(R.id.btn_share);
        initWebView();
        mWb.loadUrl(getIntent().getStringExtra("URL"));
        mBtn.setOnClickListener(v-> PopWindowDisplayUtil.showSharePopWindow(this, "content",mWb.getUrl(), "title", "shareContent", mBtn));
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

    public static void startWebActivity(Context c, String url) {
        Intent i = new Intent(c, ShareWebView.class);
        i.putExtra("URL", url);
        c.startActivity(i);
    }
}
