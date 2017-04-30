package com.example.ronny_xie.gdcp.loginActivity;

import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.mainActivity.MainActivity;
import com.example.ronny_xie.gdcp.util.ProgressDialogUtil;
import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeDelegate;
import com.gotye.api.GotyeStatusCode;
import com.gotye.api.GotyeUser;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.GestureDetector.OnGestureListener;
import android.widget.Toast;

public class welcome extends Activity implements OnGestureListener {

	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		super.onCreate(savedInstanceState);

		//初始化聊天服务器
		GotyeAPI.getInstance().init(this, MyApplication.APPKEY);
		int state = GotyeAPI.getInstance().isOnline();
		GotyeAPI.getInstance().getLoginUser();

		//获取对象是否登陆
		String user1[] = WelcomePage.getUser(welcome.this);
		String hasUserName = user1[0];
		boolean hasLogin = MyApplication.getHasLogin(this);
		if (hasUserName != null && hasLogin == true) {
			if (state == GotyeUser.NETSTATE_ONLINE|| state == GotyeUser.NETSTATE_OFFLINE) {
				//已经登陆的情况
				Intent i = new Intent(this, MainActivity.class);
				startActivity(i);
				// 启动service保存service长期活动
				Intent toService = new Intent(this, GotyeService.class);
				startService(toService);
				finish();
				return;
			} else if (state == GotyeUser.NETSTATE_BELOWLINE) {
				GotyeAPI.getInstance().login(hasUserName, null);
			}
		}else{
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					//没有登录的情况
					Intent i = new Intent(welcome.this, WelcomePage.class);
					startActivity(i);
					finish();
				}
			}, 3000);
			
		}
		GotyeAPI.getInstance().addListener(mDelegate);
	}


	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
							float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
						   float velocityY) {
		return false;
	}

	private GotyeDelegate mDelegate = new GotyeDelegate() {

		public void onLogin(int code, GotyeUser user) {
			ProgressDialogUtil.dismiss();
			// 判断登陆是否成功
			if (code == GotyeStatusCode.CodeOK // 0
					|| code == GotyeStatusCode.CodeReloginOK // 5
					|| code == GotyeStatusCode.CodeOfflineLoginOK) { // 6

				// 传入已登过的状态
				String user1[] = WelcomePage.getUser(welcome.this);
				String hasUserName = user1[0];
				String hasPassWord = user1[1];
				WelcomePage.saveUser(welcome.this, hasUserName, hasPassWord,
						true);

				Intent i = new Intent(welcome.this, MainActivity.class);
				startActivity(i);

				if (code == GotyeStatusCode.CodeOfflineLoginOK) {
					Toast.makeText(welcome.this, "您当前处于离线状态",
							Toast.LENGTH_SHORT).show();
				} else if (code == GotyeStatusCode.CodeOK) {
					Toast.makeText(welcome.this, "登录成功", Toast.LENGTH_SHORT)
							.show();
				}
				welcome.this.finish();
			} else {
				// 失败,可根据code定位失败原因
				Toast.makeText(welcome.this, "登录失败 code=" + code,
						Toast.LENGTH_SHORT).show();
			}
		}

		public void onLogout(int code) {
		}

		public void onReconnecting(int code, GotyeUser currentLoginUser) {
		}
	};

	protected void onDestroy() {
		// 移除监听
		GotyeAPI.getInstance().removeListener(mDelegate);
		super.onDestroy();
	}
}
