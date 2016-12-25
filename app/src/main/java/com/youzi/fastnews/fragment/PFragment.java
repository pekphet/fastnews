package com.youzi.fastnews.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youzi.fastnews.App;
import com.youzi.fastnews.R;
import com.youzi.fastnews.activity.ApplyWithdrawalsActivity;
import com.youzi.fastnews.activity.ApplyWithdrawalsDetailsActivity;
import com.youzi.fastnews.activity.InviteFriendsActivity;
import com.youzi.fastnews.activity.InviteRankActivity;
import com.youzi.fastnews.activity.ShareActivity;
import com.youzi.fastnews.activity.ShareRankActivity;
import com.youzi.fastnews.utils.WechatUtils;

import cc.fish.coreui.BaseFragment;

/**
 * Created by fish on 16-12-21.
 */

public class PFragment extends BaseFragment {

    private LinearLayout mLlInvite;
    private LinearLayout mLlInviteR;
    private LinearLayout mLlShare;
    private LinearLayout mLlShareR;
    private Button btn_get_money;
    private TextView apply_during_tv;
    private TextView per_login_tv;

    @Override
    protected View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.f_p, null);
        mLlInvite = (LinearLayout) v.findViewById(R.id.ll_invite);
        mLlInviteR = (LinearLayout) v.findViewById(R.id.ll_invite_r);
        mLlShare = (LinearLayout) v.findViewById(R.id.ll_sh);
        mLlShareR = (LinearLayout) v.findViewById(R.id.ll_sh_r);
        btn_get_money = (Button) v.findViewById(R.id.btn_get_money);
        apply_during_tv = (TextView) v.findViewById(R.id.apply_during_tv);
        per_login_tv = (TextView) v.findViewById(R.id.per_login_tv);


        mLlInvite.setOnClickListener(this);
        mLlInviteR.setOnClickListener(this);
        mLlShare.setOnClickListener(this);
        mLlShareR.setOnClickListener(this);
        btn_get_money.setOnClickListener(this);
        apply_during_tv.setOnClickListener(this);
        per_login_tv.setOnClickListener(this);

        return v;

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void click(View v) {
        switch (v.getId()) {
            case R.id.ll_invite:
                startActivity(new Intent(getActivity(), InviteFriendsActivity.class));
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
            case R.id.btn_get_money:
                startActivity(new Intent(getActivity(), ApplyWithdrawalsActivity.class));
                break;
            case R.id.apply_during_tv:
                startActivity(new Intent(getActivity(), ApplyWithdrawalsDetailsActivity.class));
                break;
            case R.id.per_login_tv:
                WechatUtils.wechatLogin(App.iWXAPI);
                break;
            default:
                break;
        }
    }
}
