package com.example.ronny_xie.gdcp.shop;

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
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.SlidingMenu.OnOpenListener;

public class shopActivity2 extends Activity {
	private ListView lv;
	private ProgressDialog dialog;
	public static Handler handler;
	private ArrayList<shopBean> shopList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shopfragment);
		super.onCreate(savedInstanceState);
		initmenu();
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
	private void initmenu() {
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		// 设置触摸屏幕的模式
		menu.setTouchModeAbove(SlidingMenu.LEFT);
		menu.setBehindOffsetRes(R.dimen.setMenu_MainWidth);
		menu.setBehindWidth(1);//设置SlidingMenu菜单的宽度
		menu.setShadowDrawable(R.drawable.ic_launcher);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 设置渐入渐出效果的值
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.RIGHT);	
		menu.setOnOpenListener(new OnOpenListener() {
			
			@Override
			public void onOpen() {
				finish();
			}
		});
	}
	private SlidingMenu menu;
}
