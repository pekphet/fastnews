package ac.fish.utils.adcore;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Created by fish on 16-4-22.
 */
public class MobileInfoManager {

    private static MobileInfoManager instance = null;
    private Context mContext = null;

    private MobileInfoManager(){
        super();
    }

    protected static MobileInfoManager getInstance() {
        if (instance == null) {
            instance = new MobileInfoManager();
        }
        return instance;
    }

    protected void initMobileInfo(Context applicationContext) {
        mContext = applicationContext;

    }

    /**获取imei号*/
    protected String getIMEI() {
        String strIMEI = "获取失败";
        try {
            TelephonyManager tm = (TelephonyManager) mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            strIMEI = tm.getDeviceId();
        } catch (Exception e) {
        }
        return strIMEI;
    }

    protected  String GetNetworkType()
    {
        String strNetworkType = "0";

        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
            {
//                strNetworkType = "WIFI";
                strNetworkType = "100";
            }
            else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                String _strSubTypeName = networkInfo.getSubtypeName();

                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
//                        strNetworkType = "2G";
                        strNetworkType = "2";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
//                        strNetworkType = "3G";
                        strNetworkType = "3";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
//                        strNetworkType = "4G";
                        strNetworkType = "4";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000"))
                        {
//                            strNetworkType = "3G";
                            strNetworkType = "3";
                        }
                        else
                        {
                            strNetworkType = _strSubTypeName;
                        }

                        break;
                }

            }
        }


        return strNetworkType;
    }

    /**
     * Role:Telecom service providers获取手机服务商信息 <BR>
     * 需要加入权限<uses-permission
     * android:name="android.permission.READ_PHONE_STATE"/> <BR>
     * Date:2012-3-12 <BR>
     *
     * @author CODYY)peijiangping
     */

    protected  String getProvidersName() {
        String ProvidersName = null;
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        // 返回唯一的用户ID;就是这张卡的编号神马的
        String IMSI = telephonyManager.getSubscriberId();
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
//        System.out.println(IMSI);
        if (IMSI == null) {
            ProvidersName = "99";
        } else if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
//            ProvidersName = "中国移动";
            ProvidersName = "1";

        } else if (IMSI.startsWith("46001")) {
//            ProvidersName = "中国联通";
            ProvidersName = "3";
        } else if (IMSI.startsWith("46003")) {
//            ProvidersName = "中国电信";
            ProvidersName = "2";
        }else{
            ProvidersName = "99";
        }
        return ProvidersName;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param
     * @return 当前应用的版本名称
     */
    protected  String getVersionName() {
        try {
            PackageManager packageManager = mContext.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    mContext.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前设备的唯一标识号
     *
     * @param
     * @return
     */
    protected  String getDeviceId() {
        String deviceId = Settings.System.getString(mContext.getContentResolver(), Settings.System.ANDROID_ID);
        if (TextUtils.isEmpty(deviceId)) {
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getSimSerialNumber();
            return deviceId;
        } else {
            return deviceId;
        }
    }

    protected String getMSSPSrc() {
        return "1";
    }

}
