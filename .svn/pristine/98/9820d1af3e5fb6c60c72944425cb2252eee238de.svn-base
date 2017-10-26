package com.example.ronny_xie.gdcp.styleInActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ronny_xie.gdcp.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/19.
 */

public class MyGridViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Photos> photos;

    public MyGridViewAdapter(Context context, ArrayList<Photos> photos) {
        this.context = context;
        this.photos = photos;
    }

    @Override
    public int getCount() {
        return photos == null ? 0 : photos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.style_grid_item, null);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.mIvGv =(ImageView)convertView.findViewById(R.id.iv_gv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(photos.get(i).getTitle());
        Glide.with(context).load(photos.get(i).getUrl()).into(holder.mIvGv);
//        Glide.with(context).lo
        return convertView;
    }

    static class ViewHolder {
        TextView tvTitle;
        
        ImageView mIvGv;
    }
}
