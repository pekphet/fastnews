package com.youzi.fastnews.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.youzi.fastnews.fragment.NFragment;
import com.youzi.fastnews.fragment.PFragment;
import com.youzi.fastnews.fragment.VFragment;

import cc.fish.coreui.BaseFragment;
import cc.fish.coreui.BaseFragmentActivity;

/**
 * Created by fish on 16-12-21.
 */

public class HomeActivity extends BaseFragmentActivity {

    private final static Class<BaseFragment>[] INCLUDE_FRAGMENTS = new Class[]{NFragment.class, VFragment.class, PFragment.class};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onItemClick(View item, int index) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected Class<BaseFragment>[] putFragments() {
        return INCLUDE_FRAGMENTS;
    }

    @Override
    protected View getBottomItemView(int index) {
        return null;
    }

    @Override
    protected int getFLid() {
        return 0;
    }

    @Override
    protected LinearLayout getBottomLayout() {
        return null;
    }

    @Override
    protected void checkAllBottomItem(View item, int position, boolean isChecked) {

    }
}
