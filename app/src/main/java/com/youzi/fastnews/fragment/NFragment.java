package com.youzi.fastnews.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youzi.fastnews.App;
import com.youzi.fastnews.R;
import com.youzi.fastnews.entity.NewsDResp;
import com.youzi.fastnews.entity.NewsListResp;
import com.youzi.fastnews.net.INetCallback;

import cc.fish.coreui.BaseFragment;


/**
 * Created by fish on 16-12-21.
 * news
 */

public class NFragment extends BaseFragment {

    private static final int PIECES = 5;
    private HorizontalScrollView mScrollView;
    private LinearLayout mLlBtnGrp;
    private NewsListResp mNList = null;
    private boolean isScrolling = false;
    public LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getItemWidth(PIECES), LinearLayout.LayoutParams.MATCH_PARENT);

    private int cCid = 0;
    private int cCid2 = 0;
    private int page = 1;


    @Override
    protected View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.f_n, null);
        mScrollView = (HorizontalScrollView) v.findViewById(R.id.scroll);
        mLlBtnGrp = (LinearLayout) v.findViewById(R.id.ll_btns);
        return v;
    }

    @Override
    protected void initData() {
        App.getNetManager().loadNewsList(1, new INetCallback<NewsListResp>() {
            @Override
            public void Success(NewsListResp newsListResp) {
                mNList = newsListResp;
                flushUI();
            }

            @Override
            public void Failed(String msg) {

            }
        });
    }

    private void flushUI() {
        initList();
    }

    private void initList() {
        if (mNList == null) {
            return;
        }
        for (int i = 0; i < mNList.getRows().size(); i++) {
            TextView tv = new TextView(getActivity());
            tv.setText(mNList.getRows().get(i).getCategory_name());
            tv.setLayoutParams(params);
            int finalI = i;
            tv.setOnClickListener(v -> {
                onItemClick(tv, finalI);
            });
        }
    }


    @Override
    protected void click(View v) {
    }

    private void onItemClick(View item, int index) {
        int itemWidth = item.getWidth();
        if (!isScrolling) {
            if (index > 5) {
                index = 5;
            } else if (index < 2) {
                index = 2;
            }
            mScrollView.smoothScrollTo((index - 2) * itemWidth, 40);
        }
        cCid = mNList.getRows().get(index).getParent_id();
        cCid2 = mNList.getRows().get(index).getId();
        App.getNetManager().loadNewsCategory(cCid, cCid2, page, new INetCallback<NewsDResp>() {
            @Override
            public void Success(NewsDResp newsDResp) {
                freshList(newsDResp);
            }
            @Override
            public void Failed(String msg) {

            }
        });
    }

    private void freshList(NewsDResp newsDResp) {

    }


    private int getItemWidth(int pieces) {
        int w = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        return w / pieces;
    }
}
