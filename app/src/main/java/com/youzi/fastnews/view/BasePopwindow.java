package com.youzi.fastnews.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.youzi.fastnews.R;

import java.util.List;


/**
 * Created by ywb on 2016/4/25.
 */
public class BasePopwindow extends PopupWindow {

    private Context mContext;
    private OnPopupwindowClickListener mPopupwindowClickListener;
    private int mInflateId;
    private List<String> mDataList;

    public BasePopwindow(Context context, int inflateId) {
        mContext = context;
        mInflateId = inflateId;
    }

    public BasePopwindow(Context context, int inflateId, List<String> dataList) {
        mContext = context;
        mInflateId = inflateId;
        mDataList = dataList;
    }

    public interface OnPopupwindowClickListener {
        public void click();
    }

    public void setOnPopupwindowClickListener(OnPopupwindowClickListener popupwindowClickListener) {
        mPopupwindowClickListener = popupwindowClickListener;
    }

    public void showPopWindow(View v) {
        View view = LayoutInflater.from(mContext).inflate(mInflateId, null, false);
        final PopupWindow popupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView femaleSelected = (TextView) view.findViewById(R.id.complete_iamge);
        TextView cancelSelected = (TextView) view.findViewById(R.id.image_cancel);
        femaleSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupwindowClickListener.click();
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        cancelSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        popupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                WindowManager.LayoutParams params = ((Activity) mContext).getWindow().getAttributes();
                params.alpha = 1f;
                ((Activity) mContext).getWindow().setAttributes(params);
            }
        });
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        WindowManager.LayoutParams params = ((Activity) mContext).getWindow().getAttributes();
        params.alpha = 0.5f;
        ((Activity) mContext).getWindow().setAttributes(params);
    }
}
