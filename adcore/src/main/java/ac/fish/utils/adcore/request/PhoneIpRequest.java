package ac.fish.utils.adcore.request;

import android.content.Context;
import android.os.Handler;

import com.lidroid.xutils.http.client.HttpRequest;


/**
 * Created by ywb on 2016/3/18.
 */
public class PhoneIpRequest {

    public static final int GET_PHONE_IP = 9000;

    public static final int GET_PHONE_INFO = 9001;

    public static final int GET_PHONE_BALCK_LIST = 9003;

    /**
     * 获取手机的Ip地址
     *
     * @param handler 返回接口访问获得数据
     */
    public static void getPhoneIpInfo(Handler handler,Context mContext) {
        String url = HttpRequestConfig.PHONE_IP_ADDRESS_URL + "myip";//"myip  113.105.67.99"
        HttpRequestNet httpRequestNet = new HttpRequestNet();
        httpRequestNet.sendHttpGetRequest(url, handler, GET_PHONE_IP,mContext);

    }

    public static String syncGetPhoneIpInfo() {
        String url = HttpRequestConfig.PHONE_IP_ADDRESS_URL + "myip";//"myip  113.105.67.99"
        HttpRequestNet httpRequestNet = new HttpRequestNet();
        return httpRequestNet.syncRequestForJson(HttpRequest.HttpMethod.GET, url, null);
    }

    public static void sendPhoneInfo(Handler handler,String imei,int sim,String phonetype,String phoneinip,String phoneoutip,Context mContext) {
        String url = HttpRequestConfig.MAIN_URL + "app/mi?";
        HttpRequestNet httpRequestNet = new HttpRequestNet();
        httpRequestNet.put("imei",imei);
        httpRequestNet.put("sim",sim);
        httpRequestNet.put("phonetype",phonetype);
        httpRequestNet.put("phoneinip",phoneinip);
        httpRequestNet.put("phoneoutip",phoneoutip);
        httpRequestNet.sendHttpPostRequest(url, handler, GET_PHONE_INFO,mContext);

    }

}
