package com.youzi.fastnews.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.youzi.fastnews.App;
import com.youzi.fastnews.R;
import com.youzi.fastnews.global.WechatConstants;



/**
 * 分享界面展示工具
 * Created by YangWenBin on 2015/12/31.
 */
public class PopWindowDisplayUtil {

    /**
     * 显示分享的弹出框
     *
     * @param context            上下文
     * @param content            分享弹出框的标题
     * @param shareUrl                分享的链接
     * @param shareTitle         分享出去的文案标题
     * @param shareContent       分享出去的文案内容
     * @param v                  分享弹出框弹出的相对位置的view
     */

    public static void showSharePopWindow(final Context context,final String content,final String shareUrl,final String shareTitle, final  String shareContent, View v) {

        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_share_layout, null, false);
        TextView title = (TextView) view.findViewById(R.id.share_reminder_text);
        title.setText(content);
        final PopupWindow popupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenHeight(context) / 3);
        ImageView shareWechat = (ImageView) view.findViewById(R.id.share_wechat_logo);
        ImageView shareWechatMoments = (ImageView) view.findViewById(R.id.share_wechat_monents_logo);
        shareWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WechatUtils.wechatShare(context, App.iWXAPI, WechatConstants.WXSceneSession,shareUrl,shareTitle,shareContent);

                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        shareWechatMoments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WechatUtils.wechatShare(context, App.iWXAPI, WechatConstants.WXSceneTimeline,shareUrl,shareTitle,shareContent);

                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
                params.alpha = 1f;
                ((Activity) context).getWindow().setAttributes(params);
            }
        });
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
        params.alpha = 0.5f;
        ((Activity) context).getWindow().setAttributes(params);
    }
}
