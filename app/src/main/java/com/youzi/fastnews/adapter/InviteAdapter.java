package com.youzi.fastnews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.youzi.fastnews.R;
import com.youzi.fastnews.utils.CharSquenceUtil;

import java.util.List;

/**
 * Created by ywb on 2016/11/15.
 */
public class InviteAdapter extends BaseAdapter {

    private Context mContext;

    private List<String> dataList;

    public InviteAdapter(Context context, List<String> entities) {

        mContext = context;
        dataList = entities;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_invite_rule, parent, false);
            holder = new ViewHolder();
            holder.numTv = (TextView) convertView.findViewById(R.id.invite_rule_num_tv);
            holder.desTv = (TextView) convertView.findViewById(R.id.invite_rule_des_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.numTv.setText((position + 1) + "");

        holder.desTv.setText(CharSquenceUtil.getColorfulText(dataList.get(position)));

        return convertView;
    }

    public class ViewHolder {
        TextView numTv;
        TextView desTv;
    }


}
