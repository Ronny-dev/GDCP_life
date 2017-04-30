package com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.page;

import java.util.ArrayList;
import com.example.ronny_xie.gdcp.Fragment.jwFragment;
import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.adapter.jwxskb_adapter;
import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.bean.jwxskb_javabean;
import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.loginActivity.ConnInterface;
import com.example.ronny_xie.gdcp.loginActivity.WelcomePage;
import com.example.ronny_xie.gdcp.util.ProgressDialogUtil;
import com.example.ronny_xie.gdcp.view.ListViewForScrollView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class jwxskb_page extends Activity {
	private ListViewForScrollView listview;
	private String xskcBfromHTML;
	private Handler handler;
	private ArrayList<String> temItem;
	private ArrayList<jwxskb_javabean> BeanData;
	private Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.jwxskb_fragment);
		super.onCreate(savedInstanceState);
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 2) {
					jwxskb_adapter adapter = new jwxskb_adapter(BeanData,jwxskb_page.this);
					listview.setAdapter(adapter);
				}
				super.handleMessage(msg);
			}
		};
		begin();
	}

	private void begin() {
		temItem = new ArrayList<String>();
		BeanData = new ArrayList<jwxskb_javabean>();
		initView();
		setButtonListener();
	}

	private void setButtonListener() {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ProgressDialogUtil.showProgress(jwxskb_page.this, "请稍后...");
				Intent intent = getIntent();
				String sp1Data = intent.getStringExtra("xueqi");
				String sp2Data = intent.getStringExtra("xuenian");
				initData(sp1Data, sp2Data);
			}
		});
	}

	private void initData(String sp1Data, String sp2Data) {
		final String[] tem = { sp1Data, sp2Data };
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// 联网拿到课程表的html数据
				xskcBfromHTML = ConnInterface.getXSKCBfromHTML(
						ConnInterface.getHttpclient(), jwFragment.values,
						WelcomePage.getUser(jwxskb_page.this)[0],
						WelcomePage.getUser(jwxskb_page.this)[1], tem);
				// 发送消息，通知主线程读取spinner数据
				handler.sendEmptyMessage(1);
				// 获取课程数据
				setBeanMoreOne(2);
				setBeanLessOne(4);
				setBeanMoreOne(6);
				setBeanLessOne(8);
				// 设置适配器
				handler.sendEmptyMessage(2);
				ProgressDialogUtil.dismiss();
			}

			private void setBeanMoreOne(int col) {
				temItem.removeAll(temItem);
				for (int i = 2; i < 7; i++) {
					String lineItem = ConnInterface.getItemFromHTML(col, i,
							xskcBfromHTML);
					// 过滤重复信息
					String limitBush = limitBush(lineItem);
					temItem.add(limitBush);
				}
				// 插入数据到bean对象
				isertDataToBean();
			}

			private void setBeanLessOne(int col) {
				temItem.removeAll(temItem);
				for (int i = 1; i < 6; i++) {
					String lineItem = ConnInterface.getItemFromHTML(col, i,
							xskcBfromHTML);
					// 过滤重复信息
					String limitBush = limitBush(lineItem);
					temItem.add(limitBush);
				}
				// 插入数据到bean对象
				isertDataToBean();
			}

		});
		thread.start();
	}

	// 过滤重复信息
	private String limitBush(String lineItem) {
		lineItem = lineItem.replace("周一", "");
		lineItem = lineItem.replace("周二", "");
		lineItem = lineItem.replace("周三", "");
		lineItem = lineItem.replace("周四", "");
		lineItem = lineItem.replace("周五", "");
		lineItem = lineItem.replace("第1,2节", "");
		lineItem = lineItem.replace("第3,4节", "");
		lineItem = lineItem.replace("第5,6节", "");
		lineItem = lineItem.replace("第7,8节", "");
		char[] c = lineItem.toCharArray();
		int total = 0;
		for (int a = 0; a < c.length - 1; a++)
			if (c[a] == c[0] && c[a + 1] == c[1] && c[a + 2] == c[2])
				total++;
		if (total > 1) {
			lineItem = lineItem.substring(0,
					lineItem.indexOf(lineItem.substring(0, 4), 6));
		}
		return lineItem;
	}

	// 插入数据到bean对象
	private void isertDataToBean() {
		jwxskb_javabean beanTitle = new jwxskb_javabean();
		beanTitle.setOne(temItem.get(0));
		beanTitle.setTwo(temItem.get(1));
		beanTitle.setThree(temItem.get(2));
		beanTitle.setFour(temItem.get(3));
		beanTitle.setFive(temItem.get(4));
		BeanData.add(beanTitle);
	}

	private void initView() {
		btn = (Button) findViewById(R.id.jw_xskb_btn);
		listview = (ListViewForScrollView) findViewById(R.id.jwxskb_listview);
	}
}
