package com.example.ronny_xie.gdcp.styleInActivity;

import java.util.ArrayList;

import com.bumptech.glide.Glide;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewAdapter extends BaseAdapter {

	private ArrayList<String> picURL;
	private Context context;

	public GridViewAdapter(Context context, ArrayList<String> picURL) {
		this.picURL = picURL;
		this.context = context;
	}

	@Override
	public int getCount() {
		System.out.println(picURL.size()+"aaaaaaaaaaaa");
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
		imageview.setLayoutParams(p);
		imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
		}else{
			imageview = (ImageView) convertView;
		}
		Glide.with(context).load(picURL.get(position)).into(imageview);
		return imageview;
	}

}
