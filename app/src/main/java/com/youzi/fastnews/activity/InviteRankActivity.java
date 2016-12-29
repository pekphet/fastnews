package com.youzi.fastnews.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.youzi.fastnews.App;
import com.youzi.fastnews.R;
import com.youzi.fastnews.entity.InvRankE;
import com.youzi.fastnews.entity.InvRankResp;
import com.youzi.fastnews.net.INetCallback;
import com.youzi.fastnews.utils.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pekphet on 16/12/24.
 */

public class InviteRankActivity extends Activity{

    private ListView mLv;
    private TextView mTvRule;
    private TextView mBtnBtm;
    private List<InvRankE> mData = new ArrayList<>();
    private InvRankAdapter mAdapter = new InvRankAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_inv_r);
        TextView tvHead = (TextView) findViewById(R.id.tv_head);
        tvHead.setText("邀请排行榜");
        mLv = (ListView) findViewById(R.id.lv_inv_r);
        mTvRule = (TextView) findViewById(R.id.tv_inv_r_rl);
        mBtnBtm = (TextView) findViewById(R.id.btn_inv_b);
        mLv.setAdapter(mAdapter);
        mLv.setSelector(new ColorDrawable());
        findViewById(R.id.btn_ret).setOnClickListener(bv->finish());
        mBtnBtm.setOnClickListener(bv->toInvAct());
        initData();
    }

    private void initData() {
        App.getNetManager().asyncInvRank(new INetCallback<InvRankResp>() {
            @Override
            public void Success(InvRankResp rank) {
                mTvRule.setText(rank.getRule());
                mData.clear();
                mData.addAll(rank.getInvite_rank());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void Failed(String msg) {
            }
        });
    }
    private void toInvAct() {
        Intent intent = new Intent(this, InviteFriendsActivity.class);
        startActivity(intent);
        finish();
    }


    class InvRankAdapter extends BaseAdapter {

        private Context mContext;
        private int[] RANK_ICON_SRCS = {R.drawable.rank_1st, R.drawable.rank_2nd, R.drawable.rank_3rd};
        private int[] RANK_ICON_CLRS = {0xfff13128, 0xff79cf0f, 0xffed9d00};

        public InvRankAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.i_inv_r, null);
            TextView tvR = (TextView) v.findViewById(R.id.tv_inv_r_id);
            CircleImageView cimgHd = (CircleImageView) v.findViewById(R.id.img_inv_r_hd);
            TextView tvNm = (TextView) v.findViewById(R.id.tv_inv_r_nm);
            TextView tvCnt = (TextView) v.findViewById(R.id.tv_inv_r_c);
            InvRankE ire = mData.get(position);
            if (ire.getAvatar().equals("")) {
                cimgHd.setImageResource(R.drawable.i_d_head);
            } else {
                ImageLoader.getInstance().displayImage(ire.getAvatar(), cimgHd);
            }
            tvNm.setText(ire.getUser_name());
            tvCnt.setText(ire.getInvite_num() + "");
            if (position <= 2) {
                tvR.setText("");
                tvR.setBackgroundResource(RANK_ICON_SRCS[position]);
                tvCnt.setTextColor(RANK_ICON_CLRS[position]);
            } else {
                tvR.setText(position + 1 + "");
                tvR.setTextColor(0xff989898);
                tvCnt.setTextColor(0xff989898);
            }
            return v;
        }
    }
}
