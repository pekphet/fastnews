package ac.fish.utils.adcore.request;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


import ac.fish.utils.adcore.util.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * 访问网络类
 * Created by WangCheng on 2015/8/19.
 */
public class HttpRequestNet extends HttpUtils {

    public TreeMap<String, String> mMap;

    private RequestParams mRequestParams;

    public HttpRequestNet() {
        mMap = new TreeMap<>(
                new Comparator<String>() {
                    /*
                     * int compare(Object o1, Object o2) 返回一个基本类型的整型， 返回负数表示：o1
                     * 大于o2， 返回0 表示：o1和o2相等， 返回正数表示：o1小于o2。
                     */
                    public int compare(String o1, String o2) {
                        // 指定排序器按照升序排列
                        return o1.compareTo(o2);
                    }
                });
        mRequestParams = new RequestParams();
    }

    public void put(String key, short value) {
        mMap.put(key, Short.toString(value));
    }

    public void put(String key, int value) {
        mMap.put(key, Integer.toString(value));
    }

    public void put(String key, long value) {
        mMap.put(key, Long.toString(value));
    }

    public void put(String key, float value) {
        mMap.put(key, Float.toString(value));
    }

    public void put(String key, double value) {
        mMap.put(key, Double.toString(value));
    }

    public void put(String key, String value) {
        mMap.put(key, value);
    }

    public void put(String key, File value) {
        mRequestParams.addBodyParameter(key, value);
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * @param url     访问地址
     * @param handler 访问成功后负责将获得数据发回的handler
     */
    public void sendHttpPostRequest(String url, final Handler handler,Context context) {
        sendHttpPostRequest(url, handler, HttpRequestConfig.NO_ARG1,context);
    }

    /**
     * @param url     访问地址
     * @param handler 访问成功后负责将获得数据发回的handler
     * @param arg1    访问标志，主要用于handler处理数据时区分调用接口的方法，以便对数据进行准确的处理，
     *                当arg1为-111是将不会设置进message中
     */
    public void sendHttpPostRequest(String url, final Handler handler, final int arg1,Context mContext) {
        // 检查是否有网络连接
        if (!isNetworkConnected(mContext)) {
            Toast.makeText(mContext, "网络异常，请稍候重试！",
                    Toast.LENGTH_SHORT).show();
            Message msg = Message.obtain();
            msg.what = HttpResponseConfig.NETWORK_ERROR;
            if (arg1 != HttpRequestConfig.NO_ARG1) {
                msg.arg1 = arg1;
            }
            handler.sendMessage(msg);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(url);
        Iterator<String> it = mMap.keySet().iterator();
        while (it.hasNext()) {
            // it.next()得到的是key，tm.get(key)得到obj
            String key = it.next();
            String value = mMap.get(key);
            mRequestParams.addBodyParameter(key, value);
            stringBuffer.append("&").append(key).append("=").append(value);
            // MyLogUtil.getInstance("netdebug").print("------key------" + key);
            // MyLogUtil.getInstance("netdebug").print("------value------" + value);
        }
        //执行请求
        send(HttpRequest.HttpMethod.POST, url, mRequestParams, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                //MyLogUtil.getInstance("netdebug").print("------total------" + total);
                //MyLogUtil.getInstance("netdebug").print("------current------" + current);
                //MyLogUtil.getInstance("netdebug").print("------isUploading------" + isUploading);
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Message msg = Message.obtain();
                String result = responseInfo.result;
                String codeString = JsonUtil.getJsonCode(result);
                if (!TextUtils.isEmpty(codeString)) {
                    if (Integer.parseInt(codeString) == 0) {
                        // 连接成功
                        msg.what = HttpResponseConfig.CONNET_SUCCESS;
                    } else {
                        // 连接失败
                        msg.what = HttpResponseConfig.CONNET_ERROR;
                    }
                } else {
                    // 服务器返回数据异常
                    msg.what = HttpResponseConfig.CONNET_ERROR;
                }
                if (arg1 != HttpRequestConfig.NO_ARG1) {
                    msg.arg1 = arg1;
                }
                msg.obj = result;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                Message msg = Message.obtain();
                msg.what = HttpResponseConfig.SERVER_ERROR;
                if (arg1 != HttpRequestConfig.NO_ARG1) {
                    msg.arg1 = arg1;
                }
                HttpRequestNet.this.getHttpClient().getConnectionManager()
                        .shutdown();// 关闭连接
                handler.sendMessage(msg);
            }
        });
    }

    /**
     * @param url     访问地址
     * @param handler 访问成功后负责将获得数据发回的handler
     */
    public void sendHttpGetRequest(String url, final Handler handler,Context mContext) {
        sendHttpGetRequest(url, handler, HttpRequestConfig.NO_ARG1,mContext);
    }

    /**
     * @param url     访问地址
     * @param handler 访问成功后负责将获得数据发回的handler
     * @param arg1    访问标志，主要用于handler处理数据时区分调用接口的方法，以便对数据进行准确的处理，
     *                当arg1为-111是将不会设置进message中
     */
    public void sendHttpGetRequest(String url, final Handler handler, final int arg1,Context mContext) {
        // 检查是否有网络连接
        if (!isNetworkConnected(mContext)) {
            Toast.makeText(mContext, "网络异常，请稍候重试！",
                    Toast.LENGTH_SHORT).show();
            Message msg = Message.obtain();
            msg.what = HttpResponseConfig.NETWORK_ERROR;
            if (arg1 != HttpRequestConfig.NO_ARG1) {
                msg.arg1 = arg1;
            }
            handler.sendMessage(msg);
            return;
        }
        //执行请求
        send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Message msg = Message.obtain();
                String result = responseInfo.result;
                String codeString = JsonUtil.getJsonCode(result);
                if (!TextUtils.isEmpty(codeString)) {
                    if (Integer.parseInt(codeString) == 0) {
                        // 连接成功
                        msg.what = HttpResponseConfig.CONNET_SUCCESS;
                    } else {
                        // 连接失败
                        msg.what = HttpResponseConfig.CONNET_ERROR;
                    }
                } else {
                    // 服务器返回数据异常
                    msg.what = HttpResponseConfig.CONNET_ERROR;
                }
                if (arg1 != HttpRequestConfig.NO_ARG1) {
                    msg.arg1 = arg1;
                }
                msg.obj = result;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                Message msg = Message.obtain();
                msg.what = HttpResponseConfig.SERVER_ERROR;
                if (arg1 != HttpRequestConfig.NO_ARG1) {
                    msg.arg1 = arg1;
                }
                HttpRequestNet.this.getHttpClient().getConnectionManager()
                        .shutdown();// 关闭连接
                handler.sendMessage(msg);
            }
        });
    }

    /**
     * @param url      访问地址
     * @param handler  访问成功后负责将获得数据发回的handler
     * @param arg1     访问标志，主要用于handler处理数据时区分调用接口的方法，以便对数据进行准确的处理，
     *                 当arg1为-111是将不会设置进message中
     * @param position 用来标记位置
     */
    public void sendHttpPostRequest(String url, final Handler handler, final int arg1, final int position,Context mContext) {
        // 检查是否有网络连接
        if (!isNetworkConnected(mContext)) {
            Toast.makeText(mContext, "网络异常，请稍候重试！",
                    Toast.LENGTH_SHORT).show();
            Message msg = Message.obtain();
            msg.what = HttpResponseConfig.NETWORK_ERROR;
            if (arg1 != HttpRequestConfig.NO_ARG1) {
                msg.arg1 = arg1;
                msg.arg2 = position;
            }
            handler.sendMessage(msg);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(url);
        Iterator<String> it = mMap.keySet().iterator();
        while (it.hasNext()) {
            // it.next()得到的是key，tm.get(key)得到obj
            String key = it.next();
            String value = mMap.get(key);
            mRequestParams.addBodyParameter(key, value);
            stringBuffer.append("&").append(key).append("=").append(value);
            // MyLogUtil.getInstance("netdebug").print("------key------" + key);
            // MyLogUtil.getInstance("netdebug").print("------value------" + value);
        }
        //执行请求
        send(HttpRequest.HttpMethod.POST, url, mRequestParams, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                //MyLogUtil.getInstance("netdebug").print("------total------" + total);
                //MyLogUtil.getInstance("netdebug").print("------current------" + current);
                //MyLogUtil.getInstance("netdebug").print("------isUploading------" + isUploading);
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Message msg = Message.obtain();
                String result = responseInfo.result;
                String codeString = JsonUtil.getJsonCode(result);
                if (!TextUtils.isEmpty(codeString)) {
                    if (Integer.parseInt(codeString) == 0) {
                        // 连接成功
                        msg.what = HttpResponseConfig.CONNET_SUCCESS;
                    } else {
                        // 连接失败
                        msg.what = HttpResponseConfig.CONNET_ERROR;
                    }
                } else {
                    // 服务器返回数据异常
                    msg.what = HttpResponseConfig.CONNET_ERROR;
                }
                if (arg1 != HttpRequestConfig.NO_ARG1) {
                    msg.arg1 = arg1;
                }
                msg.arg2 = position;
                msg.obj = result;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                Message msg = Message.obtain();
                msg.what = HttpResponseConfig.SERVER_ERROR;
                if (arg1 != HttpRequestConfig.NO_ARG1) {
                    msg.arg1 = arg1;
                }
                msg.arg2 = position;
                HttpRequestNet.this.getHttpClient().getConnectionManager()
                        .shutdown();// 关闭连接
                handler.sendMessage(msg);
            }
        });
    }

    public String syncRequestForJson(HttpRequest.HttpMethod sendMethod, String url, RequestParams params) {
        ResponseStream resultStream = null;
        try {
            if (params == null) {
                resultStream = sendSync(sendMethod, url);
            } else {
                resultStream = sendSync(sendMethod, url, params);
            }
            return resultStream.readString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            if (resultStream != null) {
                try {
                    resultStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
