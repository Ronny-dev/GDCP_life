package com.example.ronny_xie.gdcp.Fragment.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ronny_xie.gdcp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by cracker on 2017/7/10.
 * gridView的适配器
 */
public class AbsGridAdapter extends BaseAdapter {
    private Context mContext;

    private String[][] contents;

    private int rowTotal;

    private int columnTotal;

    private int positionTotal;


    private ArrayList colors = new ArrayList();

    private HashMap<String, Integer> courseColors = new HashMap<>();

    private int rand;

    private Random mRandom = new Random();

    public AbsGridAdapter(Context context) {
        this.mContext = context;
        for (int i = 0; i < 10; i++) {
            colors.add(i);
        }
    }


    public int getCount() {
        return positionTotal;
    }

    public long getItemId(int position) {
        return position;
    }

    public Object getItem(int position) {
        //求余得到二维索引
        int column = position % columnTotal;
        //求商得到二维索引
        int row = position / columnTotal;

        return contents[row][column];
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grib_item, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.text);
        //如果有课,那么添加数据
        if (getItem(position).equals("")){
            textView.setBackground(null);
            textView.setText("");
        }
        if (!getItem(position).equals("")) {
            String courseName = (String) getItem(position);
            textView.setText((String) getItem(position));
            textView.setTextColor(Color.WHITE);
            //变换颜色
            if (courseColors.containsKey(courseName)) {
                rand = courseColors.get(courseName);
            } else {
                rand = (mRandom.nextInt(10) + position + 7) % 9;
                if (courseColors.containsValue(rand)) {
                    rand = (int) colors.get(colors.size() - 1);
                    colors.remove(colors.indexOf(rand));
                }
                courseColors.put(courseName, rand);
                if (colors.indexOf(rand) != -1) {
                    int o = (int) colors.get(colors.indexOf(rand));
                    colors.remove(colors.indexOf(rand));
                }
            }
            switch (rand) {
                case 0:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.grid_item_bg));
                    break;
                case 1:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_1));
                    break;
                case 2:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_2));
                    break;
                case 3:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_3));
                    break;
                case 4:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_4));
                    break;
                case 5:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_5));
                    break;
                case 6:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_6));
                    break;
                case 7:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_7));
                    break;
                case 8:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_8));
                    break;
                case 9:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_9));
                    break;
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int row = position / columnTotal;
                    int column = position % columnTotal;
                    String con = "当前选中的是" + contents[row][column] + "课";
                    Toast.makeText(mContext, con, Toast.LENGTH_SHORT).show();
                }
            });
        }
        return convertView;
    }

    /**
     * 设置内容、行数、列数
     */
    public void setContent(String[][] s, int row, int column) {

        this.contents = new String[8][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                contents[j][i]=s[j][i];
            }
        }

        //this.contents = contents;
        this.rowTotal = row;
        this.columnTotal = column;
        positionTotal = rowTotal * columnTotal;
    }

}
