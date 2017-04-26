package com.example.ronny_xie.gdcp.shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.ronny_xie.gdcp.R;

public class fragment_shop extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_main_fragment);

		final String all = "http://www.kiees.com";
		final String huwai = "http://www.kiees.com/fenlei/shangpinleibie/huwai/";
		final String shuma = "http://www.kiees.com/fenlei/shangpinleibie/shoujishuma/";
		final String jiaju = "http://www.kiees.com/fenlei/shangpinleibie/jiajushenghuo/";
		final String xiangbao = "http://www.kiees.com/fenlei/shangpinleibie/zhongbiaoxiangbao/";
		final String dianqi = "http://www.kiees.com/fenlei/shangpinleibie/jiayongdianqi/";
		final String shipin = "http://www.kiees.com/fenlei/shangpinleibie/shipinbaojian/";
		final String shuji = "http://www.kiees.com/fenlei/shangpinleibie/tushuyixiang/";
		final String baicai = "http://www.kiees.com/fenlei/baicai/";

		ListView lv = (ListView) findViewById(R.id.shop_main);
		lv.setAdapter(new shop_main_adapter(getApplicationContext()));
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				if (position==0) {
					Intent intent = new Intent(getApplicationContext(), shopActivity2.class);
					intent.putExtra("data", all);
					startActivity(intent);
				}if(position == 1){
					Intent intent = new Intent(getApplicationContext(), shopActivity2.class);
					intent.putExtra("data", huwai);
					startActivity(intent);
				}
				if(position == 2){
					Intent intent = new Intent(getApplicationContext(), shopActivity2.class);
					intent.putExtra("data", shuma);
					startActivity(intent);
				}
				if(position == 3){
					Intent intent = new Intent(getApplicationContext(), shopActivity2.class);
					intent.putExtra("data", jiaju);
					startActivity(intent);
				}
				if(position == 4){
					Intent intent = new Intent(getApplicationContext(), shopActivity2.class);
					intent.putExtra("data", xiangbao);
					startActivity(intent);
				}
				if(position == 5){
					Intent intent = new Intent(getApplicationContext(), shopActivity2.class);
					intent.putExtra("data", dianqi);
					startActivity(intent);
				}
				if(position == 6){
					Intent intent = new Intent(getApplicationContext(), shopActivity2.class);
					intent.putExtra("data", shipin);
					startActivity(intent);
				}
				if(position == 7){
					Intent intent = new Intent(getApplicationContext(), shopActivity2.class);
					intent.putExtra("data", shuji);
					startActivity(intent);
				}
				if(position == 8){
					Intent intent = new Intent(getApplicationContext(), shopActivity2.class);
					intent.putExtra("data", baicai);
					startActivity(intent);
				}
			}
		});
	}
}
