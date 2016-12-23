package com.youzi.fastnews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.youzi.fastnews.R;
import com.youzi.fastnews.entity.NewsDE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fish on 16-12-23.
 */

public class NewsListAdapter extends BaseAdapter {
    private List<NewsDE> list = new ArrayList<>();
    private Context mContext = null;
    public NewsListAdapter(Context c, List<NewsDE> l) {
        list.clear();
        list.addAll(l);
        mContext = c;
    }


    @Override
    public int getCount() {
        return list.size();
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
        View v = LayoutInflater.from(mContext).inflate(R.layout.i_n, null);
        ImageView img = (ImageView) v.findViewById(R.id.img);
        TextView tv = (TextView) v.findViewById(R.id.tv_title);
        ImageLoader.getInstance().displayImage(list.get(position).getImage(), img);
        tv.setText(list.get(position).getDescription());
        v.setOnClickListener(vv-> {
            Toast.makeText(mContext, list.get(position).getLink(), Toast.LENGTH_SHORT).show();
        });
        return v;
    }
}
