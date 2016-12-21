package ac.fish.utils.adcore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by ywb on 2016/4/22.
 */
public class BaiduWebView extends WebView{

    public BaiduWebView(Context context) {
        super(context);
        initWebview();
    }

    public BaiduWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWebview();
    }

    public BaiduWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWebview();
    }


    public String getLaunchUrl(String originURL) {
        int width = getWidth();
        int height = getHeight();
        StringBuffer sb = new StringBuffer();
        sb.append(originURL).append(GlobalConst.BAIDU_UNION_KEY_WEB_WIDTH).append(width)
                .append(GlobalConst.BAIDU_UNION_KEY_WEB_HEIGHT).append(height);
        return sb.toString();
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
        setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
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
