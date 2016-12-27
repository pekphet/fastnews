package com.youzi.fastnews.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.youzi.fastnews.App;
import com.youzi.fastnews.R;
import com.youzi.fastnews.activity.ApplyWithdrawalsActivity;
import com.youzi.fastnews.activity.ApplyWithdrawalsDetailsActivity;
import com.youzi.fastnews.activity.InviteFriendsActivity;
import com.youzi.fastnews.activity.InviteRankActivity;
import com.youzi.fastnews.activity.ShareActivity;
import com.youzi.fastnews.activity.ShareRankActivity;
import com.youzi.fastnews.entity.TXListResp;
import com.youzi.fastnews.entity.YUEResp;
import com.youzi.fastnews.net.INetCallback;
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
    private ImageView mImgH;

    private float txShh = 0.0f;
    private TextView mTvMoney;

    @Override
    protected View initView(LayoutInflater inflater) {
        View v      = inflater.inflate(R.layout.f_p, null);
        mLlInvite   = (LinearLayout) v.findViewById(R.id.ll_invite);
        mLlInviteR  = (LinearLayout) v.findViewById(R.id.ll_invite_r);
        mLlShare    = (LinearLayout) v.findViewById(R.id.ll_sh);
        mLlShareR   = (LinearLayout) v.findViewById(R.id.ll_sh_r);
        btn_get_money = (Button) v.findViewById(R.id.btn_get_money);
        apply_during_tv = (TextView) v.findViewById(R.id.apply_during_tv);
        per_login_tv = (TextView) v.findViewById(R.id.per_login_tv);
        mTvMoney = (TextView) v.findViewById(R.id.tv_money);
        mImgH = (ImageView) v.findViewById(R.id.img_head);

        mLlInviteR.setVisibility(View.GONE);
        mLlShareR.setVisibility(View.GONE);

        mLlInvite.setOnClickListener(this);
        mLlInviteR.setOnClickListener(this);
        mLlShare.setOnClickListener(this);
        mLlShareR.setOnClickListener(this);
        btn_get_money.setOnClickListener(this);
        apply_during_tv.setOnClickListener(this);
        //per_login_tv.setOnClickListener(this);
        if (App.getHeadUrl() != null && !App.getHeadUrl().equals("")) {
            ImageLoader.getInstance().displayImage(App.getHeadUrl(), mImgH);
        }
        per_login_tv.setText(App.getNick());
        flushTXL();

        return v;
    }

    private void flushTXL() {
        apply_during_tv.setVisibility(View.VISIBLE);
        if (txShh != 0.0f) {
            if (txShh == -1.0f) {
                apply_during_tv.setText("恭喜您, 您的提现审核已通过");
            } else if (txShh == -2.0f) {
                apply_during_tv.setText("抱歉, 您的提现审核没有通过");
            } else {
                apply_during_tv.setText(String.format("您有一笔%.2f元提现正在处理中", txShh));
            }

        } else {
            apply_during_tv.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        App.getNetManager().txList(new INetCallback<TXListResp>() {
            @Override
            public void Success(TXListResp txListResp) {
                if (txListResp != null && txListResp.getWithdrawal_list()!= null && txListResp.getWithdrawal_list().size() > 0) {
                    if (txListResp.getWithdrawal_list().get(0).getStatus().equals("待审核")) {
                        txShh = txListResp.getWithdrawal_list().get(0).getPoint();
                    } else if (txListResp.getWithdrawal_list().get(0).getStatus().equals("审核通过")) {
                        txShh = -1.0f;
                    } else if (txListResp.getWithdrawal_list().get(0).getStatus().equals("审核不通过")){
                        txShh = -2.0f;
                    } else {

                    }
                    flushTXL();
                }
            }

            @Override
            public void Failed(String msg) {

            }
        });
        App.getNetManager().getYuE(new INetCallback<YUEResp>() {
            @Override
            public void Success(YUEResp yueResp) {
                mTvMoney.setText(yueResp.getMoney() + "");
            }

            @Override
            public void Failed(String msg) {

            }
        });

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

    @Override
    public void onResume() {
        super.onResume();
        mTvMoney.setText(App.sYUE + "");
    }
}
