package com.example.ronny_xie.gdcp.Fragment;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.card.CardActivity;
import com.example.ronny_xie.gdcp.loginActivity.ConnInterface;
import com.example.ronny_xie.gdcp.loginActivity.LoginPage;
import com.example.ronny_xie.gdcp.util.ToastUtil;

public class fragment_card extends Fragment{
	private HttpClient httpClient;
	private Handler handler;
	private RelativeLayout image;
	private Drawable passImage;
	private String password = "";
	private View v;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		v = View.inflate(getActivity(), R.layout.fragment_card, null);
		return v;
	}

	@Override
	public void onStart() {
//		handler = new Handler() {
//			@Override
//			public void handleMessage(Message msg) {
//				if (msg.what == 1) {
//					String message = (String) msg.obj;
//					ToastUtil.show(getActivity(), message);
//					if (message.equals("连接成功")) {
//						// 如果连接成功，调用方法获取密码验证图片
//						connToCardSystem();
//					}
//				} else if (msg.what == 2) {
//					image.setBackground(passImage);
//				}
//				super.handleMessage(msg);
//			}
//
//		};
		// 开启一个线程，检查是否已经连入学校内网
//		Thread thread = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					URL url = new URL("http://card.gdcp.cn/homeLogin.action");
//					HttpURLConnection conn = (HttpURLConnection) url
//							.openConnection();
//					conn.setConnectTimeout(3000);
//					conn.setRequestMethod("GET");
//					if (conn.getResponseCode() == 200) {
//						Message msg = Message.obtain();
//						msg.what = 1;
//						msg.obj = "连接成功";
//						handler.sendMessage(msg);
//					} else {
//						Message msg = Message.obtain();
//						msg.what = 1;
//						msg.obj = "请连接校园服务器";
//						handler.sendMessage(msg);
//					}
//				} catch (MalformedURLException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		thread.start();
		initView();
		super.onStart();
	}

	private void initView() {
		TextView tv_title = (TextView) v.findViewById(R.id.title);
		tv_title.setText("金融一卡通系统");
		TextView tv_login_read = (TextView) v.findViewById(R.id.fragment_card_login_textview);
		tv_login_read.setMovementMethod(ScrollingMovementMethod.getInstance());
	}

//	private void initView() {
//		image = (RelativeLayout) getActivity().findViewById(
//				R.id.fragment_card_image_password);
//		getActivity().findViewById(R.id.one).setOnClickListener(this);
//		getActivity().findViewById(R.id.two).setOnClickListener(this);
//		getActivity().findViewById(R.id.three).setOnClickListener(this);
//		getActivity().findViewById(R.id.four).setOnClickListener(this);
//		getActivity().findViewById(R.id.five).setOnClickListener(this);
//		getActivity().findViewById(R.id.six).setOnClickListener(this);
//		getActivity().findViewById(R.id.seven).setOnClickListener(this);
//		getActivity().findViewById(R.id.eight).setOnClickListener(this);
//		getActivity().findViewById(R.id.nine).setOnClickListener(this);
//		getActivity().findViewById(R.id.zero).setOnClickListener(this);
//		getActivity().findViewById(R.id.fragment_card_button)
//				.setOnClickListener(this);
//		getActivity().findViewById(R.id.click).setOnClickListener(this);
//	}

	// 连接卡务系统，拿到图片对象，通知handler刷新image
//	private void connToCardSystem() {
//		Thread thread = new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				httpClient = new DefaultHttpClient();
//				ConnInterface.ConnToCardSystem(httpClient);
//				passImage = ConnInterface.getPassImage(httpClient);
//				handler.sendEmptyMessage(2);
//			}
//		});
//		thread.start();
//	}

	// 登录系统
//	private void connCardSystem() {
//		Thread thread = new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				ConnInterface.GetCode(httpClient);
//				String clickInCardSystem = ConnInterface.ClickInCardSystem(
//						httpClient, LoginPage.getUser(getActivity())[0],
//						password);
//				String getPersonHtml = ConnInterface
//						.GetPersonAccountHTML(httpClient);
//				if (getPersonHtml == null) {
//					Document doc = Jsoup.parse(clickInCardSystem);
//					Elements error_msg = doc.getElementsByClass("biaotou");
//					String error = error_msg.text().toString();
//					Message msg = Message.obtain();
//					msg.what = 1;
//					msg.obj = error;
//					handler.sendMessage(msg);
//				} else {
//					Intent intent = new Intent(getActivity(),
//							CardActivity.class);
//					intent.putExtra("data", getPersonHtml);
//					startActivity(intent);
//				}
//			}
//		});
//		thread.start();
//	}

//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.one:
//			password = password + "0";
//			break;
//		case R.id.two:
//			password = password + "1";
//			break;
//		case R.id.three:
//			password = password + "2";
//			break;
//		case R.id.four:
//			password = password + "3";
//			break;
//		case R.id.five:
//			password = password + "4";
//			break;
//		case R.id.six:
//			password = password + "5";
//			break;
//		case R.id.seven:
//			password = password + "6";
//			break;
//		case R.id.eight:
//			password = password + "7";
//			break;
//		case R.id.nine:
//			password = password + "8";
//			break;
//		case R.id.zero:
//			password = password + "9";
//			break;
//		case R.id.fragment_card_button:
//			System.out.println(password);
//			connCardSystem();
//			break;
//		default:
//			if (password.length() == 0) {
//				System.out.println("到底了");
//				return;
//			} else {
//				password = password.substring(0, password.length() - 1);
//			}
//			break;
//		}
//	}
}
