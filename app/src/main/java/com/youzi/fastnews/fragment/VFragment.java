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
import com.youzi.fastnews.utils.ZToast;

import cc.fish.coreui.BaseFragment;
import cc.fish.coreui.view.xlistview.XListView;

import static android.view.View.GONE;


/**
 * Created by fish on 16-12-21.
 * news
 */

public class VFragment extends BaseFragment implements XListView.IXListViewListener {

    private static final int PIECES = 5;
    private HorizontalScrollView mScrollView;
    private LinearLayout mLlBtnGrp;
    private NewsListResp mNList = null;
    private boolean isScrolling = false;
    public LinearLayout.LayoutParams params = null;
    public NewsListAdapter mAdapter = null;

    private int cCid = 2;
    private int cCid2 = 0;
    private int page = 1;
    private XListView mXl;

    private int ccl = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initOnce();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initOnce() {
        App.getNetManager().loadNewsList(2, new INetCallback<NewsListResp>() {
            @Override
            public void Success(NewsListResp newsListResp) {
                mNList = newsListResp;
                flushUI();
            }
            @Override
            public void Failed(String msg) {

            }
        });
        params = new LinearLayout.LayoutParams(getItemWidth(PIECES), LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.f_n, null);
        mScrollView = (HorizontalScrollView) v.findViewById(R.id.scroll);
        mLlBtnGrp = (LinearLayout) v.findViewById(R.id.ll_btns);
        mScrollView.setVisibility(GONE);
        mXl = (XListView) v.findViewById(R.id.xlv);
        mXl.setXListViewListener(this);
        mXl.setPullLoadEnable(true);
        return v;
    }

    @Override
    protected void initData() {

    }

    private void flushUI() {
        initList(ccl);
        cCid = mNList.getRows().get(0).getParent_id();
        cCid2 = mNList.getRows().get(0).getId();
        App.getNetManager().loadNewsCategory(cCid, cCid2, page, new INetCallback<NewsDResp>(){
            @Override
            public void Success(NewsDResp newsDResp) {
                mAdapter = new NewsListAdapter(getActivity(), newsDResp.getRows());
                mXl.setAdapter(mAdapter);
            }

            @Override
            public void Failed(String msg) {
            }
        });
    }

    private void initList(int is) {
        if (mNList == null) {
            return;
        }
        mLlBtnGrp.removeAllViews();
        for (int i = 0; i < mNList.getRows().size(); i++) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.i_hd, null);
            TextView tv = (TextView) v.findViewById(R.id.tv);
            tv.setText(mNList.getRows().get(i).getCategory_name());
            v.setLayoutParams(params);
            if (i == is) {
                tv.setBackgroundResource(R.drawable.r_shape);
                tv.setTextColor(0xff545eee);
            }
            int finalI = i;
            v.setOnClickListener(vvvv  -> {
                onItemClick(vvvv, finalI);
            });
            mLlBtnGrp.addView(v);
        }
    }


    @Override
    protected void click(View v) {
    }

    private void onItemClick(View item, int index) {
        if (ccl == index) {
            return;
        }
        int itemWidth = item.getWidth();
        initList(index);
        ccl = index;
        mXl.setAdapter(mAdapter);
        cCid = mNList.getRows().get(index).getParent_id();
        cCid2 = mNList.getRows().get(index).getId();
        App.getNetManager().loadNewsCategory(cCid, cCid2, page, new INetCallback<NewsDResp>() {
            @Override
            public void Success(NewsDResp newsDResp) {
                freshList(newsDResp, false);
            }
            @Override
            public void Failed(String msg) {

            }
        });
        if (!isScrolling) {
            if (index < 2) {
                index = 2;
            }
            if (mNList.getRows().size() >= 5) {
                mScrollView.smoothScrollTo((index - 2) * itemWidth, 40);
            }
        }
    }

    private void freshList(NewsDResp newsDResp, boolean append) {
        mAdapter.changeData(newsDResp.getRows(), append);
    }


    private int getItemWidth(int pieces) {
        int w = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        return w / pieces;
    }

    @Override
    public void onRefresh() {
        page = 1;
        App.getNetManager().loadNewsCategory(cCid, cCid2, page, new INetCallback<NewsDResp>() {
            @Override
            public void Success(NewsDResp newsDResp) {
                freshList(newsDResp, false);
                mXl.stopRefresh();
            }
            @Override
            public void Failed(String msg) {
                mXl.stopRefresh();
            }
        });
    }

    @Override
    public void onLoadMore() {
        page++;
        App.getNetManager().loadNewsCategory(cCid, cCid2, page, new INetCallback<NewsDResp>() {
            @Override
            public void Success(NewsDResp newsDResp) {
                freshList(newsDResp, true);
                mXl.stopLoadMore();
                if (newsDResp.getRows() == null || newsDResp.getRows().isEmpty()) {
                    page --;
                    ZToast.r(getActivity(), "没有更多数据了");
                }
            }
            @Override
            public void Failed(String msg) {
                mXl.stopRefresh();
            }
        });
    }
}
