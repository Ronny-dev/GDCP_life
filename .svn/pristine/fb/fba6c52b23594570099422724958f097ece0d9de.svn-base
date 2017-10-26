package com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.bean.NewsItem;
import com.example.ronny_xie.gdcp.R;

import java.util.ArrayList;

import static com.example.ronny_xie.gdcp.R.id.img;

public class jw_main_adapter extends BaseAdapter {

    private ArrayList<NewsItem> newsItems;
    private Context context;

    public jw_main_adapter(ArrayList<NewsItem> newsItems, Context context) {
        this.newsItems = newsItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return newsItems == null ? 0 : newsItems.size();
    }

    @Override
    public NewsItem getItem(int position) {
        return newsItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_jw_news, parent, false);
            holder.img = (ImageView) convertView.findViewById(img);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tvSource = (TextView) convertView.findViewById(R.id.tv_source);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String url = newsItems.get(position).getImg();
        if (url != null) {
            Glide.with(context).load(url).override(300, 300).into(holder.img);
        }else{
            holder.img.setImageResource(R.drawable.ic_launcher);
        }
        holder.tvTitle.setText(newsItems.get(position).getTitle());
        holder.tvSource.setText(newsItems.get(position).getSource());
        holder.tvDate.setText(newsItems.get(position).getDate());
        return convertView;
    }

    class ViewHolder {
        ImageView img;
        TextView tvTitle;
        TextView tvSource;
        TextView tvDate;
    }

}

