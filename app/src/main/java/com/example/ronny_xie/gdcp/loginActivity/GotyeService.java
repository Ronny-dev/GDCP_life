package com.example.ronny_xie.gdcp.loginActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.mainActivity.MainActivity;
import com.example.ronny_xie.gdcp.util.AppUtil;
import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeDelegate;
import com.gotye.api.GotyeGroup;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeMessageType;
import com.gotye.api.GotyeNotify;

public class GotyeService extends Service {
	public static final String ACTION_INIT = "gotyeim.init";
	public static final String ACTION_LOGIN = "gotyeim.login";
	private GotyeAPI api;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		api = GotyeAPI.getInstance();
		MyApplication.loadSelectedKey(this);
		//亲加内部调试用，一般用户请忽略
		if(!TextUtils.isEmpty(MyApplication.IP)){
			//api.setNetConfig(MyApplication.IP, MyApplication.PORT);
		}else{
			//使用默认
			//api.setNetConfig("", -1);
		}
//		//初始化
//		int code = api.init(getBaseContext(), MyApplication.APPKEY);
		//语音识别初始化
		api.initIflySpeechRecognition();
		//api.enableLog(false, true, false);
		//添加推送消息监听
		api.addListener(mDelegate);
		//开始接收离线消息
		api.beginReceiveOfflineMessage();
		//api.beginReceiveCSOfflineMessage();
	 
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			if (ACTION_LOGIN.equals(intent.getAction())) {
				String name = intent.getStringExtra("name");
				String pwd = intent.getStringExtra("pwd");
				int code = api.login(name, pwd);
			} else if (ACTION_INIT.equals(intent.getAction())) {
				//亲加内部调试用，一般用户请忽略
				if(!TextUtils.isEmpty(MyApplication.IP)){
					api.setNetConfig(MyApplication.IP, MyApplication.PORT);
				}else{
					//使用默认
					api.setNetConfig("", -1);
				}
				int code = api.init(getBaseContext(), MyApplication.APPKEY);
			}
		} else {
			String[] user = getUser(this);
			Log.d("ServiceCommand", "user = "+user[0]);
			if (!TextUtils.isEmpty(user[0])) {
			}
		}
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		Log.d("gotye_service", "onDestroy");
		GotyeAPI.getInstance().removeListener(mDelegate);
		Intent localIntent = new Intent();
		localIntent.setClass(this, GotyeService.class); // 銷毀時重新啟動Service
		this.startService(localIntent);
		super.onDestroy();
	}

	public static String[] getUser(Context context) {
		SharedPreferences sp = context.getSharedPreferences(WelcomePage.CONFIG,
				Context.MODE_PRIVATE);
		String name = sp.getString("username", null);
		String password = sp.getString("password", null);
		String[] user = new String[2];
		user[0] = name;
		user[1] = password;
		return user;
	}

//	@SuppressWarnings("deprecation")
//	private void notify(String msg) {
//		String currentPackageName = AppUtil.getTopAppPackage(getBaseContext());
//		if (currentPackageName.equals(getPackageName())) {
//			return;
//		}
//		NotificationManager notificationManager = (NotificationManager) this
//				.getSystemService(NOTIFICATION_SERVICE);
//		notificationManager.cancel(0);
//		Notification notification = new Notification(R.drawable.ic_launcher,
//				msg, System.currentTimeMillis());
//		Intent intent = new Intent(this, MainActivity.class);
//		intent.putExtra("notify", 1);
//		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//				intent, PendingIntent.FLAG_UPDATE_CURRENT);
//		notification.setLatestEventInfo(this, getString(R.string.app_name),msg, pendingIntent);
//		notificationManager.notify(0, notification);
//	}
	private void notify(String msg){
		String currentPackageName = AppUtil.getTopAppPackage(getBaseContext());
		if (currentPackageName.equals(getPackageName())) {
			return;
		}
		NotificationManager notificationManager = (NotificationManager) this
				.getSystemService(NOTIFICATION_SERVICE);
		notificationManager.cancel(0);
		Notification.Builder builder1 = new Notification.Builder(this);
		builder1.setSmallIcon(R.drawable.ic_launcher); //设置图标
		builder1.setTicker("显示第二个通知");
		builder1.setContentTitle("通知"); //设置标题
		builder1.setContentText(msg); //消息内容
		builder1.setWhen(System.currentTimeMillis()); //发送时间
		builder1.setDefaults(Notification.DEFAULT_ALL); //设置默认的提示音，振动方式，灯光
		builder1.setAutoCancel(true);//打开程序后图标消失
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("notify", 1);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
		builder1.setContentIntent(pendingIntent);
		Notification notification1 = builder1.build();
		notificationManager.notify(124, notification1); // 通过通知管理器发送通知
	}
	
	private GotyeDelegate mDelegate = new GotyeDelegate(){
		@Override
		public void onReceiveMessage(GotyeMessage message) {
			String msg = null;

			if (message.getType() == GotyeMessageType.GotyeMessageTypeText) {
				msg = message.getSender().getName() + ":"+message.getText();
			} else if (message.getType() == GotyeMessageType.GotyeMessageTypeImage) {
				msg = message.getSender().getName() + "发来了一条图片消息";
			} else if (message.getType() == GotyeMessageType.GotyeMessageTypeAudio) {
				msg = message.getSender().getName() + "发来了一条语音消息";
			} else if (message.getType() == GotyeMessageType.GotyeMessageTypeUserData) {
				msg = message.getSender().getName() + "发来了一条自定义消息";
			} else {
				msg = message.getSender().getName() + "发来了一条群邀请信息";
			}
			if (message.getReceiver() instanceof GotyeGroup) {
				if (!(MyApplication.isGroupDontdisturb(message.getReceiver().getId()))) {
					GotyeService.this.notify(msg);
				}
				return;
			}else{
				GotyeService.this.notify(msg);
			}
		}
		@Override
		public void onReceiveNotify(GotyeNotify notify) {
			String msg = notify.getSender().getName() + "邀请您加入群[";
			if (!TextUtils.isEmpty(notify.getFrom().getName())) {
				msg += notify.getFrom().getName() + "]";
			} else {
				msg += notify.getFrom().getId() + "]";
			}
			GotyeService.this.notify(msg);
		}

		 
	};
}
