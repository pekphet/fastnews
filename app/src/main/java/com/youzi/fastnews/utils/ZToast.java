package com.youzi.fastnews.utils;

import android.content.Context;
import android.widget.Toast;

import com.youzi.fastnews.Constants;

/**
 * Created by fish on 16-8-4.
 */
public class ZToast {
    public static void debug(Context context, String msg) {
        if (Constants.DEBUG) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void release(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void d(Context context, String msg) {
        debug(context, msg);
    }

    public static void r (Context context, String msg) {
        release(context, msg);
    }
}
