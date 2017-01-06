package com.youzi.fastnews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.youzi.fastnews.activity.ShareWebView2;
import com.youzi.fastnews.fragment.N2Fragment;

import cc.fish.fishhttp.util.ZLog;

/**
 * Created by fish on 16-12-27.
 */

public class HomeWebView extends WebView{
    public HomeWebView(Context context) {
        super(context);
        initWebview();
    }

    public HomeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWebview();
    }

    public HomeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWebview();
    }

    @Override
    public void loadUrl(String url) {
        super.loadUrl(url);
        ZLog.e("HOME WEBVIEW ", url);
    }

    private void initWebview(){
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setBlockNetworkImage(false);
        settings.setDomStorageEnabled(true);
        setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                ShareWebView2.startWeb2Activity(getContext(), url, N2Fragment.gCategory1, N2Fragment.gCategory2);
//                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && canGoBack()) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
