package com.youzi.fastnews;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.youzi.fastnews.global.WechatConstants;
import com.youzi.fastnews.net.NetManager;

import ac.fish.utils.adcore.ADUtils;

/**
 * Created by fish on 16-12-21.
 */

public class App extends Application {

    private static NetManager mNetManager;
    private static String token = null;

    private static Context mAppContext;

    public static IWXAPI iWXAPI;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppContext();
        initComponents();

        /**初始化*/
        initIWXAPI();
        initImageLoader();
    }

    private void initAppContext() {
        mAppContext = getApplicationContext();
    }

    private void initComponents() {
        ADUtils.init(this);
        mNetManager = NetManager.getInstance(this);
    }

    public static NetManager getNetManager() {
        return mNetManager;
    }

    public static String getToken() {
        if (token == null) {
            SharedPreferences sp = mAppContext.getSharedPreferences("token", MODE_PRIVATE);
            token = sp.getString("token", "");
        }
        return token;
    }

    public static void setToken(String Token) {
        token = Token;
        SharedPreferences sp = mAppContext.getSharedPreferences("token", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString("token", Token);
        e.commit();
    }

    private void initIWXAPI() {

        iWXAPI = WXAPIFactory.createWXAPI(this, WechatConstants.APP_ID);

        if (!iWXAPI.isWXAppInstalled())

        {
            Toast.makeText(this, "请先安装微信应用", Toast.LENGTH_SHORT).show();
        }

        iWXAPI.registerApp(WechatConstants.APP_ID);


    }

    private void initImageLoader() {
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(4 * 1024 * 1024))
                .memoryCacheSize(4 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(
                        new BaseImageDownloader(getApplicationContext(),
                                5 * 1000, 30 * 1000)).build();
        ImageLoader.getInstance().init(imageLoaderConfiguration);
    }

}
