package com.youzi.fastnews.update;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by ywb on 2016/5/18.
 */
public class DownAppUtils {

    /**
     * 启动下载友商App服务
     */
    public static void startDownService(Context context, Class<? extends Service> clz, String url, String appName) {
        if (!WifiUtil.isHaveWifi(context)) {
            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(context, "请在WIFI环境下下载,或者在设置里面关闭只在WIFI下载", Toast.LENGTH_SHORT).show());
            return;
        }
        Intent intent = new Intent(context, clz);
        intent.putExtra("downloadUrl", url);
        intent.putExtra("appName", appName);
        context.startService(intent);
    }
}
