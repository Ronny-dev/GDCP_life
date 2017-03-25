package com.example.ronny_xie.gdcp.Fragment;
import java.util.ArrayList;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.R;


public class Fragment01_adapter extends BaseAdapter {
	private ArrayList<String> data;
	private ArrayList<String> data_time;
	private ArrayList<String> data_wea;
	private Context context;
	
	public Fragment01_adapter(ArrayList<String> data, ArrayList<String> data_time, ArrayList<String> data_wea, Context context) {
		this.data = data;
		this.data_time = data_time;
		this.data_wea = data_wea;
		this.context = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data_time.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;

		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = View.inflate(context, R.layout.listview, null);
		}
		TextView tv_shijian = (TextView) convertView.findViewById(R.id.shijian);
		TextView tv_wendu = (TextView) convertView.findViewById(R.id.wendu);
		tv_shijian.setText(data_time.get(position));
		tv_wendu.setText(data_wea.get(position));
		return convertView;
	}

}
