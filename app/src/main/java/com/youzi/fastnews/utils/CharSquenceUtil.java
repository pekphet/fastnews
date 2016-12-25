package com.youzi.fastnews.utils;

import android.text.Html;

/**
 * Created by fish on 16-8-26.
 */
public class CharSquenceUtil {

    public static CharSequence getColorfulText(String str) {
        String resourceStr = str
                .replace("<red>", "<font color=\"#f26055\">").replace("</red>", "</font>");
        return Html.fromHtml(resourceStr);
    }
}
