package com.example.ronny_xie.gdcp.styleInActivity;

import java.util.ArrayList;

import com.bumptech.glide.Glide;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewAdapter extends BaseAdapter {

    private ArrayList<String> picURL;
    private Context context;
    private int width;
    private int height;

    public GridViewAdapter(Context context, ArrayList<String> picURL) {
        this.picURL = picURL;
        this.context = context;
    }

    public GridViewAdapter(Context context, ArrayList<String> picURL, int width, int height) {
        this.width = width;
        this.height = height;
        this.picURL = picURL;
        this.context = context;
    }

    @Override
    public int getCount() {
        System.out.println(picURL.size() + "aaaaaaaaaaaa");
//        Log.i("你好", picURL.size() + "");
        return picURL.size();
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
        ImageView imageview = null;
        if (convertView == null) {
            imageview = new ImageView(context);
            GridView.LayoutParams p = new GridView.LayoutParams(300, 1000);
            p.width = (width / 4) - 4;
            p.height = 100;
            imageview.setPadding(0, 0, 1, 0);
            imageview.setLayoutParams(p);
            imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            imageview = (ImageView) convertView;
        }
        Glide.with(context).load(picURL.get(position)).into(imageview);
        return imageview;
    }

}
