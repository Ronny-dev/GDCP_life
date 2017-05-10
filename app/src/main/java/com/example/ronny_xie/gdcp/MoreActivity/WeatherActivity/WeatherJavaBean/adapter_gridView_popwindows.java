package com.example.ronny_xie.gdcp.MoreActivity.WeatherActivity.WeatherJavaBean;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ronny_xie.gdcp.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ronny_xie on 2017/2/1.
 */

public class adapter_gridView_popwindows extends BaseAdapter {
    private Context context;
    private JSONObject main;

    public adapter_gridView_popwindows(Context context, JSONObject main) {
        this.context = context;
        this.main = main;
    }

    @Override
    public int getCount() {
        return 7;
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
            convertView = View.inflate(context, R.layout.popwindow_weather_second_data, null);
        }
        TextView tv_data1 = (TextView) convertView.findViewById(R.id.fragment_popwindow3_text_data1);
        TextView tv_data2 = (TextView) convertView.findViewById(R.id.fragment_popwindow3_text_data2);
        ImageView image_xiawu = (ImageView) convertView.findViewById(R.id.fragment_popwindow3_image_xiawu);
        ImageView image_shagnwu = (ImageView) convertView.findViewById(R.id.fragment_popwindow3_image_zaoshang);
        TextView tv_tem_xiawu = (TextView) convertView.findViewById(R.id.fragment_popwindow3_text_tem_xiawu);
        TextView tv_tem_shagnwu = (TextView) convertView.findViewById(R.id.fragment_popwindow3_text_tem_shangwu);
        TextView tv_descf = (TextView) convertView.findViewById(R.id.fragment_popwindow3_text_descf);
        TextView tv_descl = (TextView) convertView.findViewById(R.id.fragment_popwindow3_text_descl);
        try {
            tv_data1.setText(main.getJSONArray("datedec").get(position).toString());
            tv_data2.setText(main.getJSONArray("weekdays").get(position).toString());
            tv_tem_shagnwu.setText(main.getJSONArray("maxt").get(position).toString() + "°C");
            tv_tem_xiawu.setText(main.getJSONArray("mint").get(position).toString() + "°C");
            Glide.with(context).load("http://www.tqyb.com.cn/designs/images/wicon/" + main.getJSONArray("icons_f").get(position).toString() + "n.png").into(image_shagnwu);
            Glide.with(context).load("http://www.tqyb.com.cn/designs/images/wicon/" + main.getJSONArray("icons_l").get(position).toString() + "n.png").into(image_xiawu);
            tv_descf.setText(main.getJSONArray("desc_f").get(position).toString());
            tv_descl.setText(main.getJSONArray("desc_l").get(position).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
