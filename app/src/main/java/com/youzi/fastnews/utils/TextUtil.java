package com.youzi.fastnews.utils;

import android.content.ClipboardManager;
import android.content.Context;

/**
 * 文本工具类
 * Created by Ywb on 2015/12/28.
 */
public class TextUtil {
    /**
     * 实现文本复制功能
     * add by wangqianzhou
     *
     * @param content 上下文
     */
    public static void copy(String content, Context context) {
        //得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    /**
     * 实现粘贴功能
     * add by wangqianzhou
     *
     * @param context 上下文
     * @return
     */
    public static String paste(Context context) {
        //得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        if (cmb.getText() != null) {
            return cmb.getText().toString().trim();
        }

        return "";
    }
}

