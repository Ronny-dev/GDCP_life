package com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.page;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.adapter.jwxscj_adapter;
import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.bean.jwxscj_javabean;
import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.loginActivity.ConnInterface;
import com.example.ronny_xie.gdcp.loginActivity.LoginPage;
import com.example.ronny_xie.gdcp.util.ProgressDialogUtil;
import com.example.ronny_xie.gdcp.view.ListViewForScrollView;

public class jwxscj_page extends Activity {
	private HttpClient httpclient;
	private List<String> value;
	private String[] user;
	private String bfromHTML;
	private String sp1Data;
	private String sp2Data;
	private Handler handler;
	private ArrayList<jwxscj_javabean> data_bean_bixiu;
	private ArrayList<jwxscj_javabean> data_bean_xuanxiu;
	private ListViewForScrollView lv_bixiu;
	private ListViewForScrollView lv_xuanxiu;
	public static jwxscj_adapter myadapter_bixiu;
	public static jwxscj_adapter myadapter_xuanxiu;
	private ScrollView scrollView;
	private TextView tv_line;
	private LinearLayout linearlayout_bixiu;
	private LinearLayout linearlayout_xuanxiu;
	private TextView tv_time;
	private LinearLayout linearlayout_below;
	private Button btn_share;
	private Button btn_error;
	private ImageView btn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.jwxscj_fragment);
		super.onCreate(savedInstanceState);
		handler = new Handler();
		ProgressDialogUtil.showProgress(jwxscj_page.this, "请稍后...");
		jwxscj_page.this.httpclient = jw_main_page.httpclient;
		jwxscj_page.this.user = LoginPage.getUser(jwxscj_page.this);
		jwxscj_page.this.value = jw_main_page.values;
		initView();
		begin();// 开始操作
	}

	private void initView() {
		btn_back = (ImageView) findViewById(R.id.jwxscj_button_back);
		btn_share = (Button) findViewById(R.id.jwxscj_button_share);
		btn_error = (Button) findViewById(R.id.jwxscj_button_error);
		scrollView = (ScrollView) jwxscj_page.this
				.findViewById(R.id.jwxscj_scrollView);
		linearlayout_below = (LinearLayout) findViewById(R.id.jwxscj_linearlayout_below);
		linearlayout_bixiu = (LinearLayout) findViewById(R.id.jwxscj_linearlayout_bixiu);
		linearlayout_xuanxiu = (LinearLayout) findViewById(R.id.jwxscj_linearlayout_xuanxiu);
		tv_line = (TextView) findViewById(R.id.jwxscj_line);// 横线
		tv_time = (TextView) findViewById(R.id.jwxscj_title_time);// title时间
		lv_bixiu = (ListViewForScrollView) jwxscj_page.this
				.findViewById(R.id.jwxscj_listview_bixiu);
		lv_xuanxiu = (ListViewForScrollView) jwxscj_page.this
				.findViewById(R.id.jwxscj_listview_xuanxiu);
	}

	private void begin() {
		setTitleTime(tv_time);// title的时间
		getIntentData();// 拿到jw_main页面携带过来的数据
		String[] spData = dataSpinner();// 处理intent数据，返回含有spinner的字符串
		initDataFromHTML(spData);// 读取数据
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();// 点击返回，退出页面
			}
		});
	}

	// 处理intent携带的spinner数据
	private String[] dataSpinner() {
		String[] spData1 = new String[2];
		spData1[0] = sp1Data;
		spData1[1] = sp2Data;
		return spData1;
	}

	// 拿到intent里面的数据
	private void getIntentData() {
		Intent intent = getIntent();
		sp1Data = intent.getStringExtra("xueqi");
		sp2Data = intent.getStringExtra("xuenian");
	}

	// 设置页面title时间
	private void setTitleTime(TextView tv_time) {
		SimpleDateFormat dataformat = new SimpleDateFormat("yy-MM-dd hh-mm-ss");
		String format = dataformat.format(new Date());
		tv_time.setText("查询时间" + format);
	}

	// 读取HTML的数据
	private void initDataFromHTML(final String[] data) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				bfromHTML = ConnInterface.getXSCJfromHTML(httpclient, value,
						user[0], "谢志杰", data);
				System.out.println(bfromHTML);
				handler.post(new Runnable() {

					@Override
					public void run() {
						data_bean_bixiu = new ArrayList<jwxscj_javabean>();
						data_bean_xuanxiu = new ArrayList<jwxscj_javabean>();
						initPersonData();// 读取title的个人信息
						getScoreData();// 拿到成绩的界面信息
						setVisible();// 设置界面缓冲显示的组件的visible
						ProgressDialogUtil.dismiss();
					}

					private void setVisible() {
						btn_error.setVisibility(View.VISIBLE);
						btn_share.setVisibility(View.VISIBLE);
						tv_line.setVisibility(View.VISIBLE);
						linearlayout_bixiu.setVisibility(View.VISIBLE);
						linearlayout_below.setVisibility(View.VISIBLE);
						linearlayout_xuanxiu.setVisibility(View.VISIBLE);
					}
				});
			}

			// 拿到分数数据
			private void getScoreData() {
				Document doc = Jsoup.parse(bfromHTML);
				Element elementById = doc.getElementById("Datagrid1");
				Elements elementsByTag = elementById.getElementsByTag("tr");
				for (int i = 1; i < elementsByTag.size(); i++) {
					jwxscj_javabean bean = new jwxscj_javabean();
					if (elementsByTag.get(i).getElementsByTag("td").get(4)
							.text().toString().equals("必修课")) {
						getDataFromDifferenceBean(bean, elementsByTag, i);
						data_bean_bixiu.add(bean);// 添加一个必修
					} else {
						getDataFromDifferenceBean(bean, elementsByTag, i);
						data_bean_xuanxiu.add(bean);// 添加一个选修
					}
				}
				System.out.println(data_bean_bixiu.size() + " "
						+ data_bean_xuanxiu.size());
				myadapter_bixiu = new jwxscj_adapter(data_bean_bixiu,
						jwxscj_page.this);
				lv_bixiu.setAdapter(myadapter_bixiu);
				myadapter_xuanxiu = new jwxscj_adapter(data_bean_xuanxiu,
						jwxscj_page.this);
				lv_xuanxiu.setAdapter(myadapter_xuanxiu);
				ProgressDialogUtil.dismiss();
				scrollView.smoothScrollTo(0, 0);
			}

			// 必修或者选修课程的bean处理
			private void getDataFromDifferenceBean(jwxscj_javabean bean,
					Elements elementsByTag, int i) {
				bean.setDaima(elementsByTag.get(i).getElementsByTag("td")
						.get(2).text().toString());
				bean.setMingcheng(elementsByTag.get(i).getElementsByTag("td")
						.get(3).text().toString());
				bean.setXingzhi(elementsByTag.get(i).getElementsByTag("td")
						.get(4).text().toString());
				bean.setXuefen(elementsByTag.get(i).getElementsByTag("td")
						.get(6).text().toString());
				bean.setJidian(elementsByTag.get(i).getElementsByTag("td")
						.get(7).text().toString());
				bean.setChengji(elementsByTag.get(i).getElementsByTag("td")
						.get(8).text().toString());
				bean.setIsshow(false);
			}

			// 初始化textview显示个人信息的数据
			private void initPersonData() {
				Document doc = Jsoup.parse(bfromHTML);
				Element elementByName = doc.getElementById("Label5");
				Element elementByClass = doc.getElementById("Label8");
				Element elementByXueyuan = doc.getElementById("Label6");
				Element elementByZhuanye = doc.getElementById("Label7");
				TextView tv_name = (TextView) findViewById(R.id.jw_xscj_name);
				TextView tv_class = (TextView) findViewById(R.id.jw_xscj_class);
				TextView tv_xueyuan = (TextView) findViewById(R.id.jw_xscj_xueyuan);
				tv_name.setText(elementByName.text().toString());
				tv_class.setText(elementByClass.text().toString());
				tv_xueyuan.setText(elementByXueyuan.text().toString() + "  "
						+ elementByZhuanye.text().toString());
			}
		});
		thread.start();
	}
}
