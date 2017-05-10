package com.example.ronny_xie.gdcp.MoreActivity.ShopActivity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.example.ronny_xie.gdcp.R;

public class shopActivity2 extends Activity {
	private ListView lv;
	private ProgressDialog dialog;
	public static Handler handler;
	private ArrayList<shopBean> shopList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_shop_main);
		super.onCreate(savedInstanceState);
		handler = new Handler() {
			@SuppressWarnings("unchecked")
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					shopList = ((ArrayList<shopBean>) msg.obj);
					lv.setAdapter(new shop_Adapter(shopList,
							getApplicationContext(), shopConnList.bitmap_List));
					dialog.dismiss();
				}
				super.handleMessage(msg);
			};
		};
		Intent intent = getIntent();
		String uri = intent.getStringExtra("data");
		System.out.println(uri);

		lv = (ListView) findViewById(R.id.shop_lv);
		dialog = new ProgressDialog(this);
		dialog.setTitle("信息正在获取中");
		dialog.setMessage("请稍后...");
		dialog.show();
		shop_AsyncTask_baicai shop_async = new shop_AsyncTask_baicai(uri, this);
		shop_async.execute();
	}
	public void back_buy(View v){
		finish();
	}
}
