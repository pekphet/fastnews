package com.youzi.fastnews.update;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by ywb on 2016/7/18.
 */
public class WifiUtil {

    public static boolean isHaveWifi(Context mContext) {

        ConnectivityManager connManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            return true;
        } else {
            return false;
        }


    }

    public static String getWifiInfo(Context context, WifiInfoType type) {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info =  wm.getConnectionInfo();
        if(info == null) {
            return "no wifi infos";
        }
        switch (type) {
            case SSID:
                return info.getSSID();
            case IP:
                int ip = info.getIpAddress();
                return String.format("%d.%d.%d.%d", ip&0xff, ip>>8&0xff, ip>>16&0xff, ip>>24&0xff);
            case MAC:
                return info.getMacAddress();
            default:
                return "error arguments";
        }
    }
    public enum WifiInfoType {
        SSID,
        IP,
        MAC,
    }
}
