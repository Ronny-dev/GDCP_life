package com.example.ronny_xie.gdcp.MoreActivity.ScheduleActivity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.R;

import java.util.List;

/**
 * Created by Ronny on 2017/5/4.
 */

public class SpinnerAdapter extends BaseAdapter {
    private List<String> arr = null;
    private Context context;

    SpinnerAdapter(List<String> arr, Context context) {
        this.arr = arr;
        this.context =context;
    }

    @Override
    public int getCount() {
        return arr.size();
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
        if(arr.get(position).equals("新建分组")){
            View v = View.inflate(context, R.layout.adapter_schedule_spinner, null);
            TextView tv = (TextView) v.findViewById(R.id.schedule_spinner_tv);
            tv.setText(arr.get(position).toString());
            return v;
        }else{
            TextView tv = new TextView(context);
            tv.setPadding(20,10,20,10);
            tv.setText(arr.get(position).toString());
            convertView = tv;
            return convertView;
        }
    }
}
