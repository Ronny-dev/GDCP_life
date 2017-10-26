package com.example.ronny_xie.gdcp.loginActivity;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.gotye.api.GotyeUser;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.tencent.smtt.sdk.QbSdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyApplication extends Application {
	//	public static final String DEFAULT_APPKEY = "ccim1001-19f6-4bd8-9b29-e454fdbf4990";
//	public static final String DEFAULT_APPKEY = "9c236035-2bf4-40b0-bfbf-e8b6dec54928";
	public static final String DEFAULT_APPKEY = "fe3c8c1c-5b06-4515-af90-01197c399ca4";
	//	public static final String DEFAULT_APPKEY = "029140c7-3acc-48df-b080-17b50ea8f082";
	public static String APPKEY = DEFAULT_APPKEY;
	//	public static String IP = null;
	public static String IP = "120.132.60.176";
	//	public static int PORT = -1;
	public static int PORT = 8888;
	private static SharedPreferences spf;
	private static final String SHARE_PREFERENCE_NAME = "gotye_config";
	// 是否有新消息提醒
	public static boolean newMsgNotify = true;
	// 不接收群消息
	public static boolean notReceiveGroupMsg = false;
	//群消息状态
	public static Map<Long, Integer> mapList = new HashMap<Long, Integer>();
	public static final String CONFIG = "markGroupTag";
	public static ArrayList<Long> disturbGroupIds = new ArrayList<Long>();

	@Override
	public void onCreate() {
		super.onCreate();
		//OkGo初始化
		OkGo.init(this);
		OkGoinit();
		loadSelectedKey(this);
		CrashApplication.getInstance(this).onCreate();
		spf = getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);

		//搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
		QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
			@Override
			public void onViewInitFinished(boolean arg0) {
				// TODO Auto-generated method stub
				//x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
				Log.d("app", " onViewInitFinished is " + arg0);
			}
			@Override
			public void onCoreInitFinished() {
				// TODO Auto-generated method stub
			}
		};
		//x5内核初始化接口
		QbSdk.initX5Environment(getApplicationContext(), cb);

	}
	private void OkGoinit() {
		//以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
		//好处是全局参数统一,特定请求可以特别定制参数
		//以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
		OkGo.getInstance()
				//如果使用默认的 60秒,以下三行也不需要传
				.setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
				.setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
				.setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间
				//可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
				.setCacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
				//可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
				.setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
				//可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
				.setRetryCount(3)
				//如果不想让框架管理cookie（或者叫session的保持）,以下不需要
				.setCookieStore(new MemoryCookieStore())            //cookie使用内存缓存（app退出后，cookie消失）
				.setCookieStore(new PersistentCookieStore());        //cookie持久化存储，如果cookie不过期，则一直有效
	}
	void onLoginCallBack(int code, GotyeUser currentLoginUser) {
		newMsgNotify = spf.getBoolean(
				"new_msg_notify_" + currentLoginUser.getName(), true);
		notReceiveGroupMsg = spf.getBoolean("not_receive_group_msg_"
				+ currentLoginUser.getName(), false);
	}

	public static void onLogoutCallBack(int code) {
		disturbGroupIds.clear();
	}

	public static boolean isNewMsgNotify() {
		return newMsgNotify;
	}

	/**
	 * 设置指定群组的群消息转态
	 *
	 * @param groupId
	 * @param tag
	 */
	public static void setGroupMarkTag(long groupId, int tag) {
		if (mapList == null) {
			mapList = new HashMap<Long, Integer>();
		}
		mapList.put(groupId, tag);
	}

	public static void setGroupMarkTag1(Context context, String user, String groupId, int tag) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG,
				context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();
		if (groupId != null && user != null) {
			edit.putInt(user + groupId, tag);
			edit.commit();
		}
	}

	public static int getGroupMarkTag1(Context context, String user, String groupId) {
		int tag = -1;
		SharedPreferences sp = context.getSharedPreferences(CONFIG, context.MODE_PRIVATE);
		if (sp.contains(user + groupId)) {
			tag = sp.getInt(user + groupId, 0);
		}
		return tag;
	}

	/**
	 * 获取指定群组的群消息状态
	 *
	 * @param groupId
	 * @return
	 */
	public static int getGroupMarKTag(long groupId) {
		int code = -1;
		if (mapList != null) {
			if (mapList.containsKey(groupId)) {
				code = MyApplication.mapList.get(groupId);
			}
		}
		return code;
	}

	/**
	 * 设置群消息免打扰
	 */
	public static void setGroupDontdisturb(long groupId) {
		if (disturbGroupIds == null) {
			disturbGroupIds = new ArrayList<Long>();
		}
		if (!disturbGroupIds.contains(groupId)) {
			disturbGroupIds.add(groupId);
			String dontdisturbIds = spf.getString("groupDontdisturb", null);
			if (dontdisturbIds == null) {
				spf.edit()
						.putString("groupDontdisturb", String.valueOf(groupId))
						.commit();
			} else {
				dontdisturbIds += "," + String.valueOf(groupId);
				spf.edit().putString("groupDontdisturb", dontdisturbIds)
						.commit();
			}

		}
	}

	/**
	 * 移除群消息免打扰
	 *
	 * @param groupId
	 */
	public static void removeGroupDontdisturb(long groupId) {
		if (disturbGroupIds == null) {
			return;
		} else {
			disturbGroupIds.remove(groupId);
		}
	}

	/**
	 * 判断是否设置群消息免打扰
	 *
	 * @param groupId
	 * @return
	 */
	public static boolean isGroupDontdisturb(long groupId) {
		if (disturbGroupIds == null) {
			String dontdisturbIds = spf.getString("groupDontdisturb", null);
			if (dontdisturbIds == null) {
				return false;
			} else {
				disturbGroupIds = new ArrayList<Long>();
				String ids[] = dontdisturbIds.split(",");
				for (String id : ids) {
					disturbGroupIds.add(Long.parseLong(id));
				}
				return disturbGroupIds.contains(groupId);
			}
		} else {
			return disturbGroupIds.contains(groupId);
		}
	}

	public static void setNewMsgNotify(boolean newMsgNotify_, String name) {
		newMsgNotify = newMsgNotify_;
		spf.edit().putBoolean("new_msg_notify_" + name, newMsgNotify).commit();
	}

	/**
	 * 设置群是否免打扰
	 *
	 * @param groupId
	 * @param disturb
	 */
	public void setDisturb(long groupId, boolean disturb) {
		spf.edit().putBoolean(String.valueOf(groupId), disturb).commit();
	}

	/**
	 * 判断是否接收群消息
	 */
	public static boolean isNotReceiveGroupMsg() {
		return notReceiveGroupMsg;
	}

	public static void setNotReceiveGroupMsg(boolean notReceiveGroupMsg_,
	                                         String loginName) {
		notReceiveGroupMsg = notReceiveGroupMsg_;
		spf.edit()
				.putBoolean("not_receive_group_msg_" + loginName,
						notReceiveGroupMsg).commit();
	}

	public static void loadSelectedKey(Context context) {
		SharedPreferences spf = context.getSharedPreferences("gotye_api",
				Context.MODE_PRIVATE);
		APPKEY = spf.getString("selected_key", DEFAULT_APPKEY);
		String ip_port = spf.getString("selected_ip_port", null);
		if (!TextUtils.isEmpty(ip_port)) {
			String[] ipPort = ip_port.split(":");
			if (ipPort != null && ipPort.length >= 2) {
				try {
					int port = Integer.parseInt(ipPort[1]);
					IP = ipPort[0];
					PORT = port;
				} catch (Exception e) {

				}

			}
		}

	}


	public static void setOfflineStatu(Context context, String user, int code) {
		SharedPreferences sp = context.getSharedPreferences("offlineStatus",
				context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();

		edit.putInt(user, code);
		edit.commit();
	}

	public static int getOfflineStatu(Context context, String user) {
		SharedPreferences sp = context.getSharedPreferences("offlineStatus",
				context.MODE_PRIVATE);
		return sp.getInt(user, 1000);
	}

	public static boolean getHasLogin(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LoginPage.CONFIG,
				Context.MODE_PRIVATE);
		return sp.getBoolean("haslogin", false);
	}

	public static void clearHasLogin(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LoginPage.CONFIG,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();

		edit.remove("haslogin");
		edit.commit();
	}


}
