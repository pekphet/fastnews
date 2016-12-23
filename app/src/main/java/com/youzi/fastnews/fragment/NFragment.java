package com.youzi.fastnews.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.youzi.fastnews.R;

import cc.fish.coreui.BaseFragment;


/**
 * Created by fish on 16-12-21.
 * news
 */

public class NFragment extends BaseFragment {

    private HorizontalScrollView mScrollView;
    private LinearLayout mLlBtnGrp;

    @Override
    protected View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.f_n, null);
        mScrollView = (HorizontalScrollView) v.findViewById(R.id.scroll);
        mLlBtnGrp = (LinearLayout) v.findViewById(R.id.ll_btns);
        return v;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void click(View v) {
    }
}
