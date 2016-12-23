package com.youzi.fastnews;

import android.app.Application;

import com.youzi.fastnews.net.NetManager;

import ac.fish.utils.adcore.ADUtils;

/**
 * Created by fish on 16-12-21.
 */

public class App extends Application {

    private static NetManager mNetManager;

    @Override
    public void onCreate() {
        super.onCreate();
        initComponents();
    }

    private void initComponents() {
        ADUtils.init(this);
        mNetManager = NetManager.getInstance(this);
    }

    public static NetManager getNetManager() {
        return mNetManager;
    }
}
