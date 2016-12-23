package com.youzi.fastnews.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.youzi.fastnews.Constants;
import com.youzi.fastnews.entity.NewsDResp;
import com.youzi.fastnews.entity.NewsDRespD;
import com.youzi.fastnews.entity.NewsListResp;
import com.youzi.fastnews.entity.NewsListRespD;

import cc.fish.fishhttp.net.RequestHelper;

/**
 * Created by fish on 16-12-23.
 */
public class NetManager implements Constants{
    private static NetManager instance = null;
    private Context mContext = null;
    private Handler mHandler = new Handler(Looper.myLooper());

    public static NetManager getInstance(Context c) {
        if (instance == null) {
            instance = new NetManager();
        }
        instance.mContext = c;
        return instance;
    }

    private NetManager() {
    }

    public void loadNewsList(int parentId, INetCallback<NewsListResp> callback) {
        new RequestHelper<NewsListRespD>().Method(RequestHelper.Method.GET)
                .Url(NEWS_LIST_URL)
                .Result(NewsListRespD.class)
                .UrlParam("parent_id", parentId+ "", true)
                .Success(result -> {
                    if (((NewsListRespD)result).getCode() != 0) {
                        callback.Failed(((NewsListRespD)result).getMsg());
                    } else {
                        NewsListResp data = ((NewsListRespD)result).getData();
                        callback.Success(data);
                    }
                })
                .Failed(msg -> callback.Failed((String) msg))
                .get(mContext, mHandler);
    }

    public void loadNewsCategory(int parentId, int cId, int page, INetCallback<NewsDResp> callback) {
        new RequestHelper<NewsDRespD>().Method(RequestHelper.Method.GET)
                .Url(NEWS_CATEGORY_URL)
                .Result(NewsListRespD.class)
                .UrlParam("page", page+ "", true)
                .UrlParam("category_id", parentId+ "")
                .UrlParam("category_id2", cId+ "")
                .Success(result -> {
                    if (((NewsDRespD)result).getCode() != 0) {
                        callback.Failed(((NewsDRespD)result).getMsg());
                    } else {
                        NewsDResp data = ((NewsDRespD)result).getData();
                        callback.Success(data);
                    }
                })
                .Failed(msg -> callback.Failed((String) msg))
                .get(mContext, mHandler);
    }



}
