package com.youzi.fastnews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youzi.fastnews.App;
import com.youzi.fastnews.R;
import com.youzi.fastnews.adapter.NewsListAdapter;
import com.youzi.fastnews.entity.NewsDResp;
import com.youzi.fastnews.entity.NewsListResp;
import com.youzi.fastnews.net.INetCallback;
import com.youzi.fastnews.view.HomeWebView;

import cc.fish.coreui.BaseFragment;

import static com.youzi.fastnews.fragment.N2Fragment.gCategory2;

/**
 * Created by fish on 16-12-27.
 */

public class V2Fragment extends BaseFragment {
    private static final int PIECES = 5;
    private HorizontalScrollView mScrollView;
    private LinearLayout mLlBtnGrp;
    private NewsListResp mNList = null;
    private boolean isScrolling = false;
    public LinearLayout.LayoutParams params = null;
    public NewsListAdapter mAdapter = null;
    private HomeWebView mWbH;
    private int ccl = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initOnce();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initOnce() {
        params = new LinearLayout.LayoutParams(getItemWidth(PIECES), LinearLayout.LayoutParams.MATCH_PARENT);
        App.getNetManager().loadNewsList(2, new INetCallback<NewsListResp>() {
            @Override
            public void Success(NewsListResp newsListResp) {
                mNList = newsListResp;
                mWbH.loadUrl(mNList.getRows().get(0).getLink());
                gCategory2 = mNList.getRows().get(0).getId();
                flushUI();
            }
            @Override
            public void Failed(String msg) {

            }
        });
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.n2_f, null);
        mScrollView = (HorizontalScrollView) v.findViewById(R.id.scroll);
        mScrollView.setVisibility(View.GONE);
        mLlBtnGrp = (LinearLayout) v.findViewById(R.id.ll_btns);
        mWbH = (HomeWebView) v.findViewById(R.id.wb_home);
        initWebView();
        return v;
    }

    private void initWebView() {

    }

    @Override
    protected void initData() {
        N2Fragment.gCategory1 = 2;
    }

    private void flushUI() {
        initList(ccl);
        App.getNetManager().loadNewsCategory(new INetCallback<NewsDResp>() {
            @Override
            public void Success(NewsDResp newsDResp) {
                mAdapter = new NewsListAdapter(getActivity(), newsDResp.getRows());
            }

            @Override
            public void Failed(String msg) {
            }
        });
    }

    private void initList(int index) {
        if (mNList == null) {
            return;
        }
        mLlBtnGrp.removeAllViews();
        for (int i = 0; i < mNList.getRows().size(); i++) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.i_hd, null);
            TextView tv = (TextView) v.findViewById(R.id.tv);
            tv.setText(mNList.getRows().get(i).getCategory_name());
            v.setLayoutParams(params);
            if (i == index) {
                tv.setBackgroundResource(R.drawable.r_shape);
                tv.setTextColor(0xff545eee);
            }
            int finalI = i;
            v.setOnClickListener(vv  -> {
                onItemClick(v, finalI);
                mWbH.loadUrl(mNList.getRows().get(finalI).getLink());
            });
            mLlBtnGrp.addView(v);
        }
    }

    private void onItemClick(View v, int index) {
        if (ccl == index) {
            return;
        }
        gCategory2 = mNList.getRows().get(index).getId();
        int itemWidth = v.getWidth();
        initList(index);
        ccl = index;
        if (!isScrolling) {
            if (index < 2) {
                index = 2;
            }
            if (mNList.getRows().size() >= 5) {
                mScrollView.smoothScrollTo((index - 2) * itemWidth, 40);
            }
        }
    }


    @Override
    protected void click(View v) {
    }

    private int getItemWidth(int pieces) {
        int w = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        return w / pieces;
    }


}
