package com.example.ronny_xie.gdcp.loginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Toast;

import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.mainActivity.MainActivity;
import com.example.ronny_xie.gdcp.util.ProgressDialogUtil;
import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeDelegate;
import com.gotye.api.GotyeStatusCode;
import com.gotye.api.GotyeUser;

public class WelcomePage extends FragmentActivity implements OnGestureListener {
	private Fragment loginPage;
	private GestureDetector mGesture = null;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//初始化
		int code = GotyeAPI.getInstance().init(this, MyApplication.APPKEY);
		//判断当前登陆状态
		int state = GotyeAPI.getInstance().isOnline();
		GotyeUser us = GotyeAPI.getInstance().getLoginUser();
		//没有登陆需要显示登陆界面
		setContentView(R.layout.layout_welcome);
		//注意添加LoginListener
		GotyeAPI.getInstance().addListener(mDelegate);
		loginPage = new LoginPage();
		//显示login Fragment
		showLogin();
		mGesture = new GestureDetector(this, this);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
	public void showLogin() {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.setCustomAnimations(R.anim.back_left_in,R.anim.back_right_out); 
		ft.replace(R.id.fragment_container, loginPage, "login");
		ft.addToBackStack(null);
		ft.commit();
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGesture.onTouchEvent(event);
	}

	@Override
	protected void onDestroy() {
		// 移除监听
		GotyeAPI.getInstance().removeListener(mDelegate);
		super.onDestroy();
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
						   float velocityY) {
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	// 单击
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
							float distanceY) {
		return false;
	}

	@Override
	// 长按
	public void onLongPress(MotionEvent e) {

	}
	
	private GotyeDelegate mDelegate = new GotyeDelegate(){
		
		public void onLogin(int code, GotyeUser user) {
			ProgressDialogUtil.dismiss();
			// 判断登陆是否成功
			if (code == GotyeStatusCode.CodeOK //0
					|| code == GotyeStatusCode.CodeReloginOK //5
					|| code == GotyeStatusCode.CodeOfflineLoginOK) {  //6
				
				// 传入已登过的状态
				String user1[] = LoginPage.getUser(WelcomePage.this);
				String hasUserName = user1[0];
				String hasPassWord = user1[1];
				LoginPage.saveUser(WelcomePage.this, hasUserName, hasPassWord, true);
				
				Intent i = new Intent(WelcomePage.this, MainActivity.class);
				startActivity(i);
				
				if (code == GotyeStatusCode.CodeOfflineLoginOK) {
					Toast.makeText(WelcomePage.this, "您当前处于离线状态", Toast.LENGTH_SHORT).show();
				} else if (code == GotyeStatusCode.CodeOK) {
					Toast.makeText(WelcomePage.this, "登录成功", Toast.LENGTH_SHORT).show();
				}
				WelcomePage.this.finish();
			} else {
				// 失败,可根据code定位失败原因
				Toast.makeText(WelcomePage.this, "登录失败 code=" + code, Toast.LENGTH_SHORT)
						.show();
			}
		}

		public void onLogout(int code) {
		}

		public void onReconnecting(int code, GotyeUser currentLoginUser) {
		}
	};
}
