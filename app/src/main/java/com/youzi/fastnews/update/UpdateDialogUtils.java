package com.youzi.fastnews.update;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.youzi.fastnews.R;
import com.youzi.fastnews.entity.UpdateResp;

import cc.fish.coreui.util.DisplayUtil;


/**
 * Created by ywb on 2016/7/22.
 */
public class UpdateDialogUtils {

    private static Dialog updateDialog;
    private static ImageView cancelBtn;
    private static Button determineBtn;
    private static ImageView updateBackgroundIamge;
    private static TextView skipCancleTv;
    private static String appName = "火速新闻";
    private static TextView sTvContent;

    /**
     * 显示Dialog
     */
    public static void showDialog(Context mContext, final UpdateResp entity) {
        if (updateDialog != null && updateDialog.isShowing()) {
            try {
                updateDialog.dismiss();
                updateDialog = null;
            } catch (Exception e) {
                e.printStackTrace();
                updateDialog = null;
            }
        }
        View view = View.inflate(mContext, R.layout.d_update, null);
        view.setEnabled(false);
        view.setFocusable(false);
        view.setFocusableInTouchMode(false);
        updateDialog = new Dialog(mContext, R.style.show_dialog);
        DisplayUtil.useTransColorBar(updateDialog);
        try {
            updateDialog.show();
            updateDialog.setContentView(view);
            updateDialog.setCancelable(false);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        cancelBtn = (ImageView) view.findViewById(R.id.update_cancle_btn);
        determineBtn = (Button) view.findViewById(R.id.update_determine_btn);
        updateBackgroundIamge = (ImageView) view.findViewById(R.id.update_background_iamge);
        skipCancleTv = (TextView) view.findViewById(R.id.skip_cancle_tv);
        sTvContent = (TextView) view.findViewById(R.id.tv_update);
        sTvContent.setText(entity.getContent());
        skipCancleTv.setVisibility(entity.getIs_force() == 1 ? View.GONE : View.VISIBLE);
        /*if (force == 1) {
            updateBackgroundIamge.setImageResource(R.drawable.force_updates);
        } else {
            updateBackgroundIamge.setImageResource(R.drawable.automatic_updates);
        }*/

        cancelBtn.setOnClickListener(v-> {
                updateDialog.dismiss();
                if (entity.getIs_force() == 1) {
                    Toast.makeText(mContext, "请更新最新版本", Toast.LENGTH_SHORT).show();
                    System.exit(0);
                }
        });
        determineBtn.setOnClickListener(v-> {
                determineBtn.setText("更新中。。。");
                determineBtn.setEnabled(false);
                DownAppUtils.startDownService(mContext, DownLoadService.class, entity.getUrl(), appName);

        });

        skipCancleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog.dismiss();
            }
        });

    }

    public static void dismissDialog() {

        if (updateDialog != null) {
            updateDialog.dismiss();
        }

    }
}
