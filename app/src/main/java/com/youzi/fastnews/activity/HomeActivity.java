package com.youzi.fastnews.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youzi.fastnews.R;
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
    private final static int[]      BOTTOM_ICON_CHECKED     = {};
    private final static int[]      BOTTOM_ICON_UNCHECKED   = {};
    private final static String[]   BOTTOM_TEXT_ARRAY       = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.a_h);
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
        View v = getBottomLayoutInflater().inflate(R.layout.l_h_b, null);
        LinearLayout ll = (LinearLayout) v.findViewById(R.id.home_page_bottom_layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        ll.setLayoutParams(params);
        ImageView img = (ImageView) v.findViewById(R.id.home_page_bottom_image);
        img.setImageResource(BOTTOM_ICON_UNCHECKED[index]);
        TextView name = (TextView) v.findViewById(R.id.home_page_bottom_btn_name);
        name.setText(BOTTOM_TEXT_ARRAY[index]);
        return v;
    }

    @Override
    protected int getFLid() {
        return R.id.fl_content;
    }

    @Override
    protected LinearLayout getBottomLayout() {
        return (LinearLayout) findViewById(R.id.ll_home_bottom);
    }

    @Override
    protected void checkAllBottomItem(View item, int position, boolean isChecked) {

    }
}
