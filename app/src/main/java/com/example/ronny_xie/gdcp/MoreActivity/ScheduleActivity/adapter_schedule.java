package com.example.ronny_xie.gdcp.MoreActivity.ScheduleActivity;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.MoreActivity.ScheduleActivity.db.DBService;

import java.util.ArrayList;


/**
 * Created by ronny_xie on 2017/2/5.
 */

public class adapter_schedule extends BaseAdapter {
    Context context;
    ArrayList<String> data = null;

    adapter_schedule(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        Cursor cursor = new DBService(context).query();
        if (data == null) {
            data = new ArrayList<String>();
        } else {
            data.clear();
        }
        while (cursor.moveToNext()) {
            data.add(cursor.getString(1));
        }
        return data.size();
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
        TextView tv = new TextView(context);
        tv.setText(data.get(position).toString());
        tv.setTextSize(20);
        tv.setPadding(50, 0, 0, 0);
        convertView = tv;
        return convertView;
    }
}
