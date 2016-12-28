package com.youzi.fastnews.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.youzi.fastnews.entity.ShareRankE;
import com.youzi.fastnews.net.INetCallback;
import com.youzi.fastnews.utils.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pekphet on 16/12/24.
 */

public class ShareRankActivity extends Activity {

    private TextView mTvBtm;
    private ListView mLv;
    private List<ShareRankE> mData = new ArrayList<>();
    private ShareRankAdapter mAdapter = new ShareRankAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_sh_r);
        TextView tvHead = (TextView) findViewById(R.id.tv_head);
        mTvBtm = (TextView) findViewById(R.id.btn_sh_b);
        mLv = (ListView) findViewById(R.id.lv_sh_r);
        mLv.setAdapter(mAdapter);

        tvHead.setText("分享排行榜");
        findViewById(R.id.btn_ret).setOnClickListener(bv->finish());
        mTvBtm.setOnClickListener(bv->toInvAct());
        initData();
    }

    private void initData() {
        App.getNetManager().asyncShareRank(new INetCallback<List<ShareRankE>>() {
            @Override
            public void Success(List<ShareRankE> shareRankEs) {
                mData.clear();
                mData.addAll(shareRankEs);
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

    class ShareRankAdapter extends BaseAdapter {
        private int[] RANK_ICON_SRCS = {R.drawable.rank_1st, R.drawable.rank_2nd, R.drawable.rank_3rd};
        private int[] RANK_ICON_CLRS = {0xfff13128, 0xff79cf0f, 0xffed9d00};
        private Context mContext;

        public ShareRankAdapter(Context context) {
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
            View v              = LayoutInflater.from(mContext).inflate(R.layout.i_sh_r, null);
            TextView tvR        = (TextView) v.findViewById(R.id.tv_sh_r_id);
            TextView tvNm       = (TextView) v.findViewById(R.id.tv_sh_r_nm);
            TextView tvCnt      = (TextView) v.findViewById(R.id.tv_sh_r_c);
            ShareRankE sre      = mData.get(position);
            CircleImageView cimHd = (CircleImageView) v.findViewById(R.id.img_sh_r_hd);
            if (sre.getAvatar().equals("")) {
                cimHd.setImageResource(R.drawable.i_d_head);
            } else {
                ImageLoader.getInstance().displayImage(sre.getAvatar(), cimHd);
            }
            tvNm.setText(sre.getUser_name());
            tvCnt.setText(sre.getShare_count());
            if (position <= 2) {
                tvR.setText("");
                tvR.setBackgroundResource(RANK_ICON_SRCS[position]);
                tvCnt.setTextColor(RANK_ICON_CLRS[position]);
            } else {
                tvR.setText(position + "");
                tvR.setTextColor(0xff989898);
                tvCnt.setTextColor(0xff989898);
            }
            return v;
        }
    }
}
