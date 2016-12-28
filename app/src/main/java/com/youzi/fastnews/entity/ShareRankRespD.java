package com.youzi.fastnews.entity;

import java.util.List;

/**
 * Created by fish on 16-12-28.
 */

public class ShareRankRespD extends BaseResp {
    private List<ShareRankE> data;

    public List<ShareRankE> getData() {
        return data;
    }

    public void setData(List<ShareRankE> data) {
        this.data = data;
    }
}
