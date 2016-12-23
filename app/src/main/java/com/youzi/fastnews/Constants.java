package com.youzi.fastnews;

/**
 * Created by fish on 16-12-23.
 */

public interface Constants {
    boolean DEBUG               = true;

    String MAIN_URL             = DEBUG ? "http://60.205.58.24:8084" : "";

    String NEWS_LIST_URL        = MAIN_URL + "/api/category/get_list";

    String NEWS_CATEGORY_URL    = MAIN_URL + "/api/feeds/get_list";



}
