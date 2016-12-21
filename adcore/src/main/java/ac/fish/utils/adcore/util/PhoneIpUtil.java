package ac.fish.utils.adcore.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import ac.fish.utils.adcore.entity.BlackListEntity;

/**
 * Created by ywb on 2016/4/1.
 */
public class PhoneIpUtil {

    private static final String GET_FAILED = "获取失败";

    /**
     * 获取手机类型
     */
    public static String getPhoneType() {

        return android.os.Build.MODEL;

    }

    public static String getPhoneIp(Context mContext) {

        //获取wifi服务
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        int ipAddress = wifiInfo.getIpAddress();

        String ip = intToIp(ipAddress);

        return ip;
    }

    public static String intToIp(int i) {

        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

    /**
     * 获取手机是否有sim卡
     */
    public static boolean isSim() {

        int absent = TelephonyManager.SIM_STATE_ABSENT;
        if (1 == absent) {
            return true;
        } else {
            return false;
        }

    }

    public static String getIMEI(Context context) {
        String strIMEI = GET_FAILED;
        try {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            strIMEI = tm.getDeviceId();
        } catch (Exception e) {
        }
        return strIMEI;
    }

    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }


    public static List<BlackListEntity> ReadSettings(Context context) {
        try {
            InputStream is = context.getAssets().open("black_list.json");
            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            String result = new String(buffer, "UTF-8");

            String data = JsonUtil.getKeyFindJsonStr(result, "list");

            List<BlackListEntity> dataList = JsonUtil.toList(data, new TypeToken<List<BlackListEntity>>() {});

            return dataList;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
