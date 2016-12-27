package com.youzi.fastnews.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youzi.fastnews.App;
import com.youzi.fastnews.R;
import com.youzi.fastnews.entity.UpdateResp;
import com.youzi.fastnews.fragment.NFragment;
import com.youzi.fastnews.fragment.PFragment;
import com.youzi.fastnews.fragment.VFragment;
import com.youzi.fastnews.net.INetCallback;
import com.youzi.fastnews.update.UpdateDialogUtils;
import com.youzi.fastnews.utils.ZToast;

import cc.fish.coreui.BaseFragment;
import cc.fish.coreui.BaseFragmentActivity;

/**
 * Created by fish on 16-12-21.
 */

public class HomeActivity extends BaseFragmentActivity {

    private final static Class<BaseFragment>[] INCLUDE_FRAGMENTS = new Class[]{NFragment.class, VFragment.class, PFragment.class};
    private final static int[]      BOTTOM_ICON_CHECKED     = {R.drawable.i_h_np, R.drawable.i_h_vp, R.drawable.i_h_pp};
    private final static int[]      BOTTOM_ICON_UNCHECKED   = {R.drawable.i_h_n, R.drawable.i_h_v, R.drawable.i_h_p};
    private final static String[]   BOTTOM_TEXT_ARRAY       = {"新闻", "视频", "个人"};

    private static HomeActivity self = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.a_h);
        self = this;
        checkUpdate();
        super.onCreate(savedInstanceState);
    }

    private void checkUpdate() {
        App.getNetManager().update(new INetCallback<UpdateResp>() {
            @Override
            public void Success(UpdateResp updateResp) {
                UpdateDialogUtils.showDialog(HomeActivity.this, updateResp);
            }

            @Override
            public void Failed(String msg) {
                ZToast.d(HomeActivity.this, msg);
            }
        });
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
        ((ImageView) item.findViewById(R.id.home_page_bottom_image)).setImageResource(isChecked ? BOTTOM_ICON_CHECKED[position] : BOTTOM_ICON_UNCHECKED[position]);
    }

    public static void sel(int index) {
        if (self == null) {
            return;
        }
        self.setTabSel(self.getBottomLayout().getChildAt(index), index);
    }
}
