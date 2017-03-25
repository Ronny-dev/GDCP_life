package com.example.ronny_xie.gdcp.loginActivity;

import java.util.List;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.util.ProgressDialogUtil;
import com.example.ronny_xie.gdcp.util.ToastUtil;
import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeUser;

public class LoginPage extends Fragment {
	Button mButLogin, mButLogout;
	EditText mEdtName, mEdtPsd;
	String mUsername;
	String mPassword;
	public static HttpClient httpClient = null;
	private List<String> values;
	private Drawable drawable;
	private ImageView image;
	private Handler handler;
	private EditText code;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.layout_login, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		httpClient = new DefaultHttpClient();
		ProgressDialogUtil.showProgress(getActivity(), "正在连接..请稍后");
		initView();
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				if (msg.what == 1) {
					image.setBackground(drawable);
					ProgressDialogUtil.dismiss();
				} else if (msg.what == 2) {
					if (checkUser()) {
						GotyeUser u = GotyeAPI.getInstance().getLoginUser();
						u = GotyeAPI.getInstance().getLoginUser();
						Log.d("", u.getName());

						// 登录的时候要传入登录监听，当重复登录时会直接返回登录状态
						saveUser(LoginPage.this.getActivity(), mUsername,
								mEdtPsd.getText().toString().trim(), false);
						Intent login = new Intent(getActivity(),
								GotyeService.class);
						login.setAction(GotyeService.ACTION_LOGIN);
						login.putExtra("name", mUsername);
						if (TextUtils.isEmpty("")) {
							// login.putExtra("pwd", null);
						} else {
							login.putExtra("pwd", "");
						}
						getActivity().startService(login);
						ProgressDialogUtil.showProgress(
								LoginPage.this.getActivity(), "正在登录...");
					}
				} else if (msg.what == 3) {
					String a = (String) msg.obj;
					if (a == null) {
						ToastUtil.show(getActivity(), "登录失败LoginPage");
					} else {
						ToastUtil.show(getActivity(), (String) msg.obj);
					}
					httpClient = null;
					httpClient = new DefaultHttpClient();
					Thread thread = new Thread(new Runnable() {

						@Override
						public void run() {
							values = ConnInterface.Conn(httpClient);
							drawable = ConnInterface.GetImageCode(httpClient);
							handler.sendEmptyMessage(1);
						}
					});
					thread.start();
				} else if (msg.what == 1001) {
					ToastUtil.show(getActivity(), (String) msg.obj + "，欢迎您登录");
				}
			};
		};
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				values = ConnInterface.Conn(httpClient);
				drawable = ConnInterface.GetImageCode(httpClient);
				handler.sendEmptyMessage(1);
			}
		});
		thread.start();
	}

	public void initView() {
		code = (EditText) getView().findViewById(R.id.code);
		image = (ImageView) getView().findViewById(R.id.image);
		mButLogin = (Button) getView().findViewById(R.id.start);
		mEdtName = (EditText) getView().findViewById(R.id.username);
		mEdtPsd = (EditText) getView().findViewById(R.id.userpsd);
		String user[] = getUser(LoginPage.this.getActivity());
		String hasUserName = user[0];
		String hasPassWord = user[1];
		mUsername = hasUserName;

		mPassword = hasPassWord;
		if (mUsername != null) {
			mEdtName.setText(hasUserName);
			mEdtName.setSelection(mEdtName.getText().length());
		}

		mButLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				initButton();
				ProgressDialogUtil.showProgress(getActivity(), "正在登录...请稍后");
				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						String mUser = mEdtName.getText().toString().trim();
						String mCode = code.getText().toString().trim();
						String mpass = mEdtPsd.getText().toString().trim();
						String[] arr = { mUser, mpass, mCode };
						int b = ConnInterface.ClickIn(httpClient, arr, values,
								handler);
						if (b == 1) {
							handler.sendEmptyMessage(2);
							ProgressDialogUtil.dismiss();
						}
						if (b == -1) {
							handler.sendEmptyMessage(3);
							ProgressDialogUtil.dismiss();
						}
					}
				});
				thread.start();
			}

			private void initButton() {
				if (mEdtName.getText().toString().trim().equals("")) {
					ToastUtil.show(getActivity(), "请输入用户ID");
					return;
				} else if (mEdtPsd.getText().toString().trim().equals("")) {
					ToastUtil.show(getActivity(), "请输入密码");
					return;
				} else if (code.getText().toString().trim().equals("")) {
					ToastUtil.show(getActivity(), "请输入验证码");
					return;
				}
			}
		});
	}

	private boolean checkUser() {
		mUsername = mEdtName.getText().toString();
		boolean isValid = true;
		if (mUsername == null || mUsername.length() == 0) {
			Toast.makeText(this.getActivity(), "请输入用户名", Toast.LENGTH_SHORT)
					.show();
			isValid = false;
		}
		return isValid;
	}

	public static final String CONFIG = "login_config";

	public static void saveUser(Context context, String name, String password,
								boolean haslogin) {
		if (TextUtils.isEmpty(name)) {
			return;
		}
		SharedPreferences sp = context.getSharedPreferences(CONFIG,
				Context.MODE_PRIVATE);

		SharedPreferences.Editor edit = sp.edit();
		edit.putString("username", name);
//		if (TextUtils.isEmpty(password)) {
//			edit.putString("password", null);
//		} else {
//			edit.putString("password", password.trim());
//		}
		edit.putString("password", password);

		edit.putBoolean("haslogin", haslogin);

		edit.commit();
	}

	public static String[] getUser(Context context) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG,
				Context.MODE_PRIVATE);
		String name = sp.getString("username", null);
		String password = sp.getString("password", null);
		String[] user = new String[2];
		user[0] = name;
		user[1] = password;
		return user;
	}

}
