package com.youzi.fastnews.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.youzi.fastnews.App;
import com.youzi.fastnews.R;
import com.youzi.fastnews.entity.DrawResp;
import com.youzi.fastnews.entity.FeedResp;
import com.youzi.fastnews.global.WechatConstants;
import com.youzi.fastnews.net.INetCallback;
import com.youzi.fastnews.utils.WechatUtils;
import com.youzi.fastnews.utils.ZToast;
import com.youzi.fastnews.view.PacDialogUtils;

import cc.fish.fishhttp.util.ZLog;

import static android.view.View.GONE;

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
    private String mUrl;
    private TextView mTvPac;

    private int mPacCount = App.sPacCnt;
    private Handler mHandler = new Handler(Looper.myLooper());
    private boolean isPause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_shw);
        mWb = (WebView) findViewById(R.id.web);
        mBtn = (TextView) findViewById(R.id.btn_share);
        mBtnRt = (ImageButton) findViewById(R.id.btn_retn);
        mBtnFr = (TextView) findViewById(R.id.btn_s2fr);
        mTvPac = (TextView) findViewById(R.id.btn_pac);
        findViewById(R.id.ll_dt_pac).setVisibility(View.VISIBLE);
        initPacBtn();
        initWebView();
        mCat1 = getIntent().getIntExtra("CATEGORY1", -1);
        mCat2 = getIntent().getIntExtra("CATEGORY2", -1);
        if (mCat1 == 2) {
            mCat2 = 0;
        }
        mUrl = getIntent().getStringExtra("URL");
        mWb.loadUrl(mUrl);
        ZLog.e("webview load", mUrl);
        mBtn.setOnClickListener(v->startActivity(new Intent(this, ShareActivity.class)));
        mBtnRt.setOnClickListener(v->finish());
        mBtnFr.setOnClickListener(v->sent2FR());

        loadShareUrl();
    }

    private void initPacBtn() {
        mTvPac.setEnabled(false);
        countPacBtn();
    }

    private void countPacBtn() {
        if (mPacCount > 0) {
            if (mHandler == null) {
                return;
            }
            mTvPac.setText(mPacCount + "s");
            mTvPac.setBackgroundResource(R.drawable.dt_pac_un);
            mHandler.postDelayed(()-> countPacBtn(), 1000);
            mPacCount --;
        } else {
            mTvPac.setEnabled(true);
            mTvPac.setText("");
            mTvPac.setBackgroundResource(R.drawable.dt_pac_en);
            mTvPac.setOnClickListener(v->{
                doDraw();
                findViewById(R.id.ll_dt_pac).setVisibility(GONE);
            });
        }
    }

    private void doDraw() {
        if(App.isLogIn()) {
            App.getNetManager().doDraw(new INetCallback<DrawResp>() {
                @Override
                public void Success(DrawResp drawResp) {
                    PacDialogUtils.showDialog(ShareWebView2.this, drawResp);
                }
                @Override
                public void Failed(String msg) {
                    ZToast.r(ShareWebView2.this, msg);
                }
            });
        } else {
            ImmediatelyLoginActivity.doLogin(ShareWebView2.this);
        }
    }

    private void checkLogin() {
        if (App.isLogIn()) {
            mBtnFr.setOnClickListener(v-> {
                sent2FR();
            });
        } else {
            mBtnFr.setOnClickListener(v-> {
                ImmediatelyLoginActivity.doLogin(this);
            });
        }
    }

    private void loadShareUrl() {
        mBtnFr.setEnabled(false);
        App.getNetManager().transUrl(getIntent().getStringExtra("URL"), mCat1, mCat2, new INetCallback<FeedResp>() {
            @Override
            public void Success(FeedResp f) {
                mBtnFr.setEnabled(true);
                App.sCFID = f.getId();
                sUrl = f.getShare_link().replace("{logged_token}", App.getToken());
                sCon = f.getShare_title();
                sDes = f.getShare_description();
                checkLogin();
            }

            @Override
            public void Failed(String msg) {
                ZToast.r(ShareWebView2.this, msg);
            }
        });
    }

    private void sent2FR() {
        //PopWindowDisplayUtil.showSharePopWindow(this, "分享", sUrl, sCon, sDes, mBtnFr);
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
        settings.setBlockNetworkImage(false);
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

    @Override
    protected void onPause() {
        super.onPause();
        mWb.loadUrl("about:blank");
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPause) {
            mWb.loadUrl(mUrl);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWb != null && android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            mWb.loadUrl("about:blank");
        }
    }
}
