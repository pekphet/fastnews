package com.youzi.fastnews.update;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;


/**
 * Created by ywb on 2016/5/25.
 * <p>
 * 下载App的服务
 */
public class DownLoadService extends Service {
    private DownloadManager downloadManager;
    private static long enqueueID;//根据队列的id查找下载apk的Uri
    private DownLoadReceiver receiver;
    /**
     * 下载文件存放目录
     */
    private static String downloadDir = "apk/download/";

    private String downloadUrl;

    private String appName;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        }
        downloadUrl = intent.getStringExtra("downloadUrl");
        appName = intent.getStringExtra("appName");
        if (downloadUrl != null && !"".equals(downloadUrl)) {
            startDownload();
        }
        receiver = new DownLoadReceiver();
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        return super.onStartCommand(intent, flags, startId);
    }

    private void startDownload() {
        try {
            downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
            request.setTitle(appName);
            request.setMimeType("application/vnd.android.package-archive");
            if (!Environment.getExternalStoragePublicDirectory(downloadDir).getParentFile().exists()) {
                Environment.getExternalStoragePublicDirectory(downloadDir).getParentFile().mkdirs();
            }
            request.setDestinationInExternalPublicDir(downloadDir, appName);
            enqueueID = downloadManager.enqueue(request);
//            Toast.makeText(getApplicationContext(), "开始下载。。。", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "下载失败。。。", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        stopSelf();
        super.onDestroy();
    }

}