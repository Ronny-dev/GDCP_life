package com.example.ronny_xie.gdcp.MoreActivity.ShopActivity;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.R;

public class shop_main_adapter extends BaseAdapter {
	private String[] arrs;
	private Context context;
	private int[] arr_images;
	public shop_main_adapter(Context context) {
		arrs = new String[] {"全部","运动户外","电脑数码","家具生活","箱包鞋帽","家用电器","食品保健","书籍旅行"};
		arr_images = new int[]{R.drawable.aaaall,R.drawable.aaayundong,R.drawable.aaadiannao,R.drawable.aaajiaju,R.drawable.aaaxiangbao,R.drawable.aaajiayong,R.drawable.aaashipin,R.drawable.aaashuji};
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrs.length;
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
			convertView = View.inflate(context, R.layout.shop_main_baseadapter, null);
		}
		LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.shop_main_baseadapter_ll);
		ll.setBackgroundResource(arr_images[position]);
		TextView tv = (TextView) convertView.findViewById(R.id.shop_main_baseadapter_text);
		tv.setText(arrs[position]);
		return convertView;
	}

}
