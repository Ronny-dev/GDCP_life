package com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.bean.jwxskb_javabean;
import com.example.ronny_xie.gdcp.R;

import java.util.ArrayList;
import java.util.Random;

public class jwxskb_adapter extends BaseAdapter {
    private ArrayList<jwxskb_javabean> beanData;
    private Context context;
    private Random r = new Random();
    private String[][] data = new String[4][5];


    private int rand;

    public jwxskb_adapter(ArrayList<jwxskb_javabean> beanData, Context context) {
        this.beanData = beanData;
        this.context = context;
        for (int i = 0; i < beanData.size(); i++) {
            data[i][0] = beanData.get(i).getOne();
            data[i][1] = beanData.get(i).getTwo();
            data[i][2] = beanData.get(i).getThree();
            data[i][3] = beanData.get(i).getFour();
            data[i][4] = beanData.get(i).getFive();
        }
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public String getItem(int position) {
        //求余得到二维索引
        int column = position % 5;
        //求商得到二维索引
        int row = position / 5;

        return data[row][column];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grib_item2, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.text);
        //如果有课,那么添加数据
        if (getItem(position).length() < 8) {
            textView.setBackground(null);
            textView.setText("");
        } else {
            String courseName = (String) getItem(position);
            textView.setText((String) getItem(position));
            textView.setTextColor(Color.WHITE);
            rand=r.nextInt(10);
            switch (rand) {
                case 0:
                    textView.setBackground(context.getResources().getDrawable(R.drawable.grid_item_bg));
                    break;
                case 1:
                    textView.setBackground(context.getResources().getDrawable(R.drawable.bg_1));
                    break;
                case 2:
                    textView.setBackground(context.getResources().getDrawable(R.drawable.bg_2));
                    break;
                case 3:
                    textView.setBackground(context.getResources().getDrawable(R.drawable.bg_3));
                    break;
                case 4:
                    textView.setBackground(context.getResources().getDrawable(R.drawable.bg_4));
                    break;
                case 5:
                    textView.setBackground(context.getResources().getDrawable(R.drawable.bg_5));
                    break;
                case 6:
                    textView.setBackground(context.getResources().getDrawable(R.drawable.bg_6));
                    break;
                case 7:
                    textView.setBackground(context.getResources().getDrawable(R.drawable.bg_7));
                    break;
                case 8:
                    textView.setBackground(context.getResources().getDrawable(R.drawable.bg_8));
                    break;
                case 9:
                    textView.setBackground(context.getResources().getDrawable(R.drawable.bg_9));
                    break;
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int row = position / 5;
                    int column = position % 5;
                    String con = "当前选中的是" + data[row][column] + "课";
                    Toast.makeText(context, con, Toast.LENGTH_SHORT).show();
                }
            });
        }
        return convertView;

    }


    public static class ViewHolder {
        TextView one;
        TextView two;
        TextView three;
        TextView four;
        TextView five;
    }
}
