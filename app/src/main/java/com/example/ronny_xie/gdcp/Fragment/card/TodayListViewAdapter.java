package com.example.ronny_xie.gdcp.Fragment.card;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.Fragment.card.javabean.todayData_javabean;
import com.example.ronny_xie.gdcp.R;

import java.util.ArrayList;

/**
 * Created by Ronny on 2017/5/9.
 */

public class TodayListViewAdapter extends BaseAdapter {
    private Context context;
    private todayData_javabean moneyData;

    TodayListViewAdapter(Context context, todayData_javabean moneyData) {
        this.context = context;
        this.moneyData = moneyData;
    }

    @Override
    public int getCount() {
        return moneyData.getRows().size();
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
        View view = View.inflate(context, R.layout.adapter_card_today_listview, null);
        TextView tv_date = (TextView) view.findViewById(R.id.fragment_card_listview_tv_date);
        TextView tv_type = (TextView) view.findViewById(R.id.fragment_card_listview_tv_type);
        TextView tv_name = (TextView) view.findViewById(R.id.fragment_card_listview_tv_name);
        TextView tv_money = (TextView) view.findViewById(R.id.fragment_card_listview_tv_money);
        String date = moneyData.getRows().get(position).getOCCTIME();
        double money = moneyData.getRows().get(position).getTRANAMT();
        Log.i("Ronny", date);
        date = date.substring(date.indexOf("-") + 1);
        tv_date.setText(date);
        tv_type.setText(moneyData.getRows().get(position).getTRANNAME());
        tv_name.setText(money < 0 ? String.valueOf(money) + "元" : "+" + money + "元");
        tv_money.setText(moneyData.getRows().get(position).getCARDBAL() + "元");
        return view;
    }
}
