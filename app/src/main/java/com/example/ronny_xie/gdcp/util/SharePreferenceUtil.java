package com.example.ronny_xie.gdcp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


/**
 * Created by ronny_xie on 2017/1/28.
 */

public class SharePreferenceUtil {
    private static final String TAG = "SharePreferenceUtil";

    public static SharedPreferences newSharePreference(Context context, String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public static void saveArrayList(String key, Object obj, SharedPreferences share, Gson gson) {
        SharedPreferences.Editor edit = share.edit();
        String str = gson.toJson(obj);
        edit.putString(key, str);
        edit.commit();
    }

    public static ArrayList<Object> getArrayList(String key, SharedPreferences share, Class<?> bean, Gson gson) {
        try {
            String s = share.getString(key, "");
            if (!s.equals("")) {
                ArrayList<Object> aaa = new ArrayList<Object>();
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    Object fromJson = gson.fromJson(array.get(i).toString(), bean);
                    aaa.add(fromJson);
                }
                return aaa;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveString(String key, String value, SharedPreferences share) {
        SharedPreferences.Editor edit = share.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static String getString(String key, SharedPreferences share) {
        String s = share.getString(key, "");
        return s;
    }
}
