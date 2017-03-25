package com.example.ronny_xie.gdcp.AsynkTask;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.R;

import java.util.ArrayList;

/**
 * Created by ronny_xie on 2017/1/28.
 */

public class adapter_weather_weibo extends BaseAdapter {
    private ArrayList<javabean_weather_weibo> data_javabean = new ArrayList<javabean_weather_weibo>();
    private Context context;

    public adapter_weather_weibo(ArrayList<javabean_weather_weibo> data_javabean, Context context) {
        this.data_javabean = data_javabean;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
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
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.weather_weibo, null);
        }
        TextView tv_title = (TextView) convertView.findViewById(R.id.fragment_weather_weibo_title);
        TextView tv_time = (TextView) convertView.findViewById(R.id.fragment_weather_weibo_time);
        TextView tv_from = (TextView) convertView.findViewById(R.id.fragment_weather_weibo_from);
        tv_title.setText("\u3000\u3000"+data_javabean.get(position).getText());
        tv_time.setText(data_javabean.get(position).getDateTime());
        tv_from.setText(data_javabean.get(position).getFrom());
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
