package com.example.ronny_xie.gdcp.loginActivity;


import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.mainActivity.MainActivity;
import com.example.ronny_xie.gdcp.util.ProgressDialogUtil;
import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeUser;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends Activity {

	Button mButLogin;
	EditText mEdtName, mEdtPsd;
	String mUsername;
	String mPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_login);
		super.onCreate(savedInstanceState);
		initView();
		initBtnListener();
	}

	private void initBtnListener() {
		mButLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ProgressDialogUtil.showProgress(
						login.this, "正在登录...");
				if (checkUser()) {
					GotyeUser u = GotyeAPI.getInstance().getLoginUser();
					u = GotyeAPI.getInstance().getLoginUser();

					// 登录的时候要传入登录监听，当重复登录时会直接返回登录状态
					saveUser(login.this, mUsername, mEdtPsd
							.getText().toString().trim(), false);
					Intent login = new Intent(login.this, GotyeService.class);
					login.setAction(GotyeService.ACTION_LOGIN);
					login.putExtra("name", mUsername);
					if (TextUtils.isEmpty(mEdtPsd.getText().toString().trim())) {
						// login.putExtra("pwd", null);
					} else {
						login.putExtra("pwd", mEdtPsd.getText().toString()
								.trim());
					}
					startService(login);

					GotyeAPI.getInstance().login(mUsername, null);
					Intent i = new Intent(login.this, MainActivity.class);
					startActivity(i);
					// 启动service保存service长期活动
					Intent toService = new Intent(login.this, GotyeService.class);
					startService(toService);
					finish();
					ProgressDialogUtil.dismiss();
				}
			}
		});
	}

	//获取sp保存的用户名
	public static final String CONFIG = "login_config";
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

	//检验用户名
	private boolean checkUser() {
		mUsername = mEdtName.getText().toString();
		boolean isValid = true;
		if (mUsername == null || mUsername.length() == 0) {
			Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT)
					.show();
			isValid = false;
		}
		return isValid;
	}

	private void initView() {
		mButLogin = (Button) findViewById(R.id.start);
		mEdtName = (EditText) findViewById(R.id.username);
		mEdtPsd = (EditText) findViewById(R.id.userpsd);
		String user[] = getUser(login.this);
		String hasUserName = user[0];
		String hasPassWord = user[1];
		mUsername = hasUserName;

		mPassword = hasPassWord;
		if (mUsername != null) {
			mEdtName.setText(hasUserName);
			mEdtName.setSelection(mEdtName.getText().length());
		}


	}

	public static void saveUser(Context context, String name, String password, boolean haslogin) {
		if (TextUtils.isEmpty(name)) {
			return;
		}
		SharedPreferences sp = context.getSharedPreferences(CONFIG,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();
		edit.putString("username", name);
		if (TextUtils.isEmpty(password)) {
			edit.putString("password", null);
		} else {
			edit.putString("password", password.trim());
		}
		
		edit.putBoolean("haslogin", haslogin);
		edit.commit();
	}
}
