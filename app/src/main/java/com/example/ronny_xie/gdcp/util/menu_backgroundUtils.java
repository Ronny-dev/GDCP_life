package com.example.ronny_xie.gdcp.util;

import com.bumptech.glide.Glide;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;

public class menu_backgroundUtils {
	public static void setMenuBackground(Context context, String a) {
		@SuppressWarnings("static-access")
		SharedPreferences shared = context.getSharedPreferences(
				"menu_background", context.MODE_PRIVATE);
		SharedPreferences.Editor edit = shared.edit();
		edit.putString("background_uri",a);
		edit.commit();
	}

	public static void getMenuBackground(Context context, ImageView ll) {
		@SuppressWarnings("static-access")
		SharedPreferences shared = context.getSharedPreferences(
				"menu_background", context.MODE_PRIVATE);
		String requestBackgroundUri = shared.getString("background_uri", null);
		if (requestBackgroundUri != null) {
			System.out.println("requestBackGround！=null");
			Glide.with(context).load(requestBackgroundUri).into(ll);
		} else {
			System.out.println("null");
		}
	}
}
