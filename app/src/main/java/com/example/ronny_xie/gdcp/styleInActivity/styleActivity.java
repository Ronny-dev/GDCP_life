package com.example.ronny_xie.gdcp.styleInActivity;

import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.util.menu_backgroundUtils;

public class styleActivity extends Activity {
	private GridView gridView;
	private ArrayList<String> picUrl;
	private GridViewAdapter adapter;
	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_style);
		initView();
		initBmob();
		searchDataForBmob();
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				menu_backgroundUtils.setMenuBackground(styleActivity.this,picUrl.get(position));
				finish();
			}
		});
	}
	private void initView() {
		picUrl = new ArrayList<String>();
		gridView = (GridView) findViewById(R.id.gridview);
	}

	private void searchDataForBmob() {
		BmobQuery<backgroundURL> query = new BmobQuery<backgroundURL>();
		query.addWhereEqualTo("search", "URL");
		query.findObjects(new FindListener<backgroundURL>() {

			@Override
			public void done(List<backgroundURL> object, BmobException e) {
				if (e == null) {
					for (backgroundURL background : object) {
						String a = background.getBackgroundURL();
						System.out.println(a);
						picUrl.add(a);
					}
				} else {
					System.out.println("error");
				}
				if(picUrl!=null){
					System.out.println(picUrl.size()+"aaaaaaaaaaaaa");
					adapter = new GridViewAdapter(styleActivity.this,picUrl);
					gridView.setAdapter(adapter);
				}
			}
		});
	}

	private void initBmob() {
		Bmob.initialize(this, "cd3366d572012e733dec8de33f65ded5");
	}
}
