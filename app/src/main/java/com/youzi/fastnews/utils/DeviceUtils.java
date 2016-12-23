package com.youzi.fastnews.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by fish on 16-12-23.
 */

public class DeviceUtils {
    public static String getIMEI(Context context) {
        String strIMEI = "获取失败";
        try {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            strIMEI = tm.getDeviceId();
        } catch (Exception e) {
        }
        return strIMEI;
    }
}
