package com.youzi.fastnews.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fish on 16-12-23.
 */

public class NewsListResp {
    private List<NewsListE> rows = new ArrayList<>();
    private int         total;

    public List<NewsListE> getRows() {
        return rows;
    }

    public void setRows(List<NewsListE> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
