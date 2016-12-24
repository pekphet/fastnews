package com.youzi.fastnews.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.youzi.fastnews.R;
import com.youzi.fastnews.activity.InviteActivity;
import com.youzi.fastnews.activity.InviteRankActivity;
import com.youzi.fastnews.activity.ShareActivity;
import com.youzi.fastnews.activity.ShareRankActivity;

import cc.fish.coreui.BaseFragment;

/**
 * Created by fish on 16-12-21.
 */

public class PFragment extends BaseFragment {

    private LinearLayout mLlInvite;
    private LinearLayout mLlInviteR;
    private LinearLayout mLlShare;
    private LinearLayout mLlShareR;

    @Override
    protected View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.f_p, null);
        mLlInvite = (LinearLayout) v.findViewById(R.id.ll_invite);
        mLlInviteR = (LinearLayout) v.findViewById(R.id.ll_invite_r);
        mLlShare = (LinearLayout) v.findViewById(R.id.ll_sh);
        mLlShareR = (LinearLayout) v.findViewById(R.id.ll_sh_r);
        mLlInvite.setOnClickListener(this);
        mLlInviteR.setOnClickListener(this);
        mLlShare.setOnClickListener(this);
        mLlShareR.setOnClickListener(this);
        return v;

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void click(View v) {
        switch (v.getId()) {
            case R.id.ll_invite:
                startActivity(new Intent(getActivity(), InviteActivity.class));
                break;
            case R.id.ll_invite_r:
                startActivity(new Intent(getActivity(), InviteRankActivity.class));
                break;
            case R.id.ll_sh:
                startActivity(new Intent(getActivity(), ShareActivity.class));
                break;
            case R.id.ll_sh_r:
                startActivity(new Intent(getActivity(), ShareRankActivity.class));
                break;
            default:
                break;
        }
    }
}
