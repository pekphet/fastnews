package com.youzi.fastnews.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.youzi.fastnews.R;
import com.youzi.fastnews.activity.HomeActivity;
import com.youzi.fastnews.entity.DrawResp;

import cc.fish.coreui.util.DisplayUtil;

/**
 * Created by fish on 17-1-4.
 */

public class PacDialogUtils {
    private static Dialog mSucDialog = null;
    private static Dialog mFlDialog = null;
    private static TextView sFlTvCnt;
    private static TextView sSucTvMny;
    private static TextView sSucTvCnt;

    private static void initFlDialog(Context context) {
        View v      = LayoutInflater.from(context).inflate(R.layout.d_pac_fl, null);
        sFlTvCnt    = (TextView) v.findViewById(R.id.tv_pac_ct);

        v.setEnabled(false);
        v.setFocusable(false);
        v.setFocusableInTouchMode(false);

        mFlDialog = new Dialog(context, R.style.show_dialog);
        DisplayUtil.useTransColorBar(mFlDialog);
        mFlDialog.setContentView(v);
        mFlDialog.setCancelable(false);

        v.findViewById(R.id.imb_clz).setOnClickListener(imb->dismiss());
    }

    private static void initSucDialog(Context context) {
        View v      = LayoutInflater.from(context).inflate(R.layout.d_pac_suc, null);
        sSucTvMny   = (TextView) v.findViewById(R.id.tv_d_pac_mny);
        sSucTvCnt   = (TextView) v.findViewById(R.id.tv_pac_ct);

        v.setEnabled(false);
        v.setFocusable(false);
        v.setFocusableInTouchMode(false);

        mSucDialog = new Dialog(context, R.style.show_dialog);
        DisplayUtil.useTransColorBar(mSucDialog);
        mSucDialog.setContentView(v);
        mSucDialog.setCancelable(false);

        v.findViewById(R.id.btn_d_pac_view).setOnClickListener(btn -> {
            dismiss();
            ((Activity)context).finish();
            HomeActivity.sel(2);
        });
        v.findViewById(R.id.btn_d_pac_ok).setOnClickListener(btn -> dismiss());

    }

    public static void showDialog(Context context, final DrawResp entity) {
        dismiss();
        if (entity.getIs_draw() == 0) {
            initFlDialog(context);
            sFlTvCnt.setText(entity.getContent());
            mFlDialog.show();
        } else {
            initSucDialog(context);
            sSucTvMny.setText("+" + entity.getMoney());
            sSucTvCnt.setText(entity.getContent());
            mSucDialog.show();
        }
    }

    public static void dismiss() {
        if (mSucDialog != null && mSucDialog.isShowing()) {
            mSucDialog.dismiss();
        }
        if (mFlDialog != null && mFlDialog.isShowing()) {
            mFlDialog.dismiss();
        }
        mSucDialog  = null;
        mFlDialog   = null;
    }
}
