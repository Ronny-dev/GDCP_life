package com.example.ronny_xie.gdcp.MoreActivity.WeatherActivity.WeatherJavaBean;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ronny_xie on 2017/2/2.
 */

public class adapter_listview_popwindow3 extends BaseAdapter {
    private static final String TAG = "adapter_listview_popwin";
    private Context context;
    private JSONObject data;
    private int Hour;

    public adapter_listview_popwindow3(Context context, JSONObject data, int hour) {
        this.context = context;
        this.data = data;
        this.Hour = hour;
    }

    @Override
    public int getCount() {
        return 24;
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
            convertView = View.inflate(context, R.layout.popwindows3_listview, null);
        }
        TextView tv1 = (TextView) convertView.findViewById(R.id.fragment_weather_popwindow3_text1);
        TextView tv2 = (TextView) convertView.findViewById(R.id.fragment_weather_popwindow3_text2);
        TextView tv3 = (TextView) convertView.findViewById(R.id.fragment_weather_popwindow3_text3);
        TextView tv4 = (TextView) convertView.findViewById(R.id.fragment_weather_popwindow3_text4);
        TextView tv5 = (TextView) convertView.findViewById(R.id.fragment_weather_popwindow3_text5);
        try {
            tv1.setText(Hour + position + "时");
            tv2.setText(data.getJSONArray("hour24_t").get(position).toString() + " °C");
            tv3.setText(data.getJSONArray("hour24_rain").get(position).toString() + " mm");
            tv4.setText(data.getJSONArray("hour24_windd").get(position).toString() + " k/p");
            tv5.setText(data.getJSONArray("hour24_winds").get(position).toString() + " °");
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
