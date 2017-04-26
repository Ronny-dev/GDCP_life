package com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.R;

public class jw_main_adapter extends BaseAdapter {
	private ArrayList<String> title;
	private Context context;

	public jw_main_adapter(ArrayList<String> title, Context context) {
		this.title = title;
		this.context = context;
	}

	@Override
	public int getCount() {
		return title.size();
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
			convertView = View.inflate(context, R.layout.jw_listview, null);
		}
		TextView listView_tv = (TextView) convertView
				.findViewById(R.id.jw_listView_textView);
		listView_tv.setText(title.get(position));
		return convertView;
	}

}
