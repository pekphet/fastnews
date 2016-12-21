package ac.fish.utils.adcore;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import com.adyouzi.mobads.GatherService;
import com.adyouzi.mobads.InterstitialAd;
import com.adyouzi.mobads.InterstitialAdListener;

import java.util.List;

import ac.fish.utils.adcore.entity.BlackListEntity;
import ac.fish.utils.adcore.request.HttpRequestNet;
import ac.fish.utils.adcore.util.JsonUtil;
import ac.fish.utils.adcore.util.PhoneIpUtil;

import static ac.fish.utils.adcore.request.PhoneIpRequest.syncGetPhoneIpInfo;

/**
 * Created by fish on 16-4-22.
 */
public class ADUtils {
    public static boolean IS_ADVICE_SHOW = true;

    private static boolean mIsInit = false;
    private static Context mContext = null;
    private static MobileInfoManager mMobileInfo;


    public static void init(Context applicationContext) {
        if (applicationContext == null) {
            throw new IllegalArgumentException("please use un-null application context!");
        }
        mContext = applicationContext;
        GatherService.startGather(mContext);
        Config.isOPPOChannel = isOppoChannel(mContext);
        //1. is network conn?
        if (!HttpRequestNet.isNetworkConnected(mContext)) {
            IS_ADVICE_SHOW = false;
            return;
        }
        //2.is ad allowed? by cloud|location config.
        if (!Config.isADAllowed) {
            IS_ADVICE_SHOW = false;
            return;
        }
        //3.is oppo channel?
        if (!Config.isOPPOChannel) {
            IS_ADVICE_SHOW = true;
        }
        checkOppoDeviceEnabled(mContext);
        //4.init baidu union page
        mMobileInfo = MobileInfoManager.getInstance();
        mMobileInfo.initMobileInfo(mContext);
        mIsInit = true;
    }

    public static String getBaiduUnionURL () {
        isInit();
        StringBuffer buffer = new StringBuffer();
        buffer.append(GlobalConst.BAIDU_UNION_URL).append(GlobalConst.BAIDU_UNION_CHANNEL).append(GlobalConst.BAIDU_UNION_KEY)
                .append(GlobalConst.BAIDU_UNION_KEY_SRC).append(mMobileInfo.getMSSPSrc())
                .append(GlobalConst.BAIDU_UNION_KEY_VERSION).append(mMobileInfo.getVersionName())
                .append(GlobalConst.BAIDU_UNION_KEY_IMEI).append(mMobileInfo.getIMEI())
                .append(GlobalConst.BAIDU_UNION_KEY_DEVICE).append(mMobileInfo.getDeviceId())
                .append(GlobalConst.BAIDU_UNION_KEY_OPERATOR).append(mMobileInfo.getProvidersName());

        return buffer.toString();
    }

    public static String getBaiduUnionURL (String channel_key) {
        isInit();
        StringBuffer buffer = new StringBuffer();
        buffer.append(GlobalConst.BAIDU_UNION_URL).append(channel_key)
                .append(GlobalConst.BAIDU_UNION_KEY_SRC).append(mMobileInfo.getMSSPSrc())
                .append(GlobalConst.BAIDU_UNION_KEY_VERSION).append(mMobileInfo.getVersionName())
                .append(GlobalConst.BAIDU_UNION_KEY_IMEI).append(mMobileInfo.getIMEI())
                .append(GlobalConst.BAIDU_UNION_KEY_DEVICE).append(mMobileInfo.getDeviceId())
                .append(GlobalConst.BAIDU_UNION_KEY_OPERATOR).append(mMobileInfo.getProvidersName());

        return buffer.toString();
    }

    public static InterstitialAd makeAd(Activity activity, Handler handler) {
        isInit();
        InterstitialAd ad = new InterstitialAd(activity, GlobalConst.AD_KEY);
        ad.setListener(new InterstitialAdListener() {
            @Override
            public void onReady() {
                if (ad != null && ad.isAdReady()) {
                    ad.show(activity);
                }
            }

            @Override
            public void onShowed() {
                handler.postDelayed(() -> {
                    if (ad != null) {
                        ad.remove();
                    }
                }, 5000);
            }

            @Override
            public void onDismissed() {
            }
        });
        return ad;
    }

    private static void checkOppoDeviceEnabled(Context context) {
        Thread checkBlacklistThread = new Thread(() -> {
            List<BlackListEntity> blackList = PhoneIpUtil.ReadSettings(context);
            String imei = PhoneIpUtil.getIMEI(context);
            if (imei != null) {
            	for (BlackListEntity entity : blackList) {
                    if (imei.equals(entity.imei)) {
                    IS_ADVICE_SHOW = false;
		    }
                }
            }
        });
        Thread checkOppoLocationThread = new Thread(() -> {
            String ipResult = syncGetPhoneIpInfo();
            String data = JsonUtil.getKeyFindJsonStr(ipResult, "data");
            String city = JsonUtil.getKeyFindJsonStr(data, "city");
            for (String block : GlobalConst.BLOCK_LOCATIONS) {
                if (city.contains(block)) {
                    IS_ADVICE_SHOW = false;
                    break;
                }
            }
        });
        checkBlacklistThread.start();
        checkOppoLocationThread.start();
    }

    private static void isInit() {
        if (!mIsInit) {
            throw new IllegalStateException("please call init first!");
        }
    }

    //get channel whether in OPPO CHANNEL
    private static boolean isOppoChannel(Context appContext) {
        Bundle metadata = null;
        try {
            metadata = appContext.getPackageManager().getApplicationInfo(appContext.getPackageName(), PackageManager.GET_META_DATA).metaData;
            if (metadata == null) {
                return false;
            }
            String channel = metadata.getString(GlobalConst.META_DATA_CHANNEL_KEY);
            return GlobalConst.OPPO_CHANNEL.equals(channel);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
