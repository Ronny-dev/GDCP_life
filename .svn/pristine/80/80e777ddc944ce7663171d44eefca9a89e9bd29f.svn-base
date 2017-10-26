package com.example.ronny_xie.gdcp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.Map;

public class menu_backgroundUtils {
    public static void setMenuBackground(Context context, String a) {
        @SuppressWarnings("static-access")
        SharedPreferences shared = context.getSharedPreferences(
                "menu_background", context.MODE_PRIVATE);
        SharedPreferences.Editor edit = shared.edit();
        edit.putString("background_uri", a);
        edit.commit();
    }

    public static void getMenuBackground(Context context, final FrameLayout frameLayout) {
        @SuppressWarnings("static-access")
        SharedPreferences shared = context.getSharedPreferences(
                "menu_background", context.MODE_PRIVATE);
        String requestBackgroundUri = shared.getString("background_uri", null);
        if (requestBackgroundUri != null) {
            System.out.println("requestBackGround！=null");
            Glide.with(context).load(requestBackgroundUri).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                    Drawable drawable = new BitmapDrawable(bitmap);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        frameLayout.setBackground(drawable);
                        ;
                    }
                }
            });
        } else {
            System.out.println("null");
        }
    }

    /**
     * 将搜索的内容保存到本地，以便下次获取
     *
     * @param context
     * @param s
     */
    public static void setStringToCache(Context context, String s) {
        StringBuffer sb = null;
        @SuppressWarnings("static-access")
        SharedPreferences sp = context.getSharedPreferences(
                "SEARCH_RECORD", context.MODE_APPEND);

        ArrayList<String> list = getArrayListToCache(context);
        if (list != null) {
            if (!list.contains(s)) {
                sb = getStringToCache(context);
                sb.append(s);
                sb.append("@*&&*@");
                sp.edit().putString("img_title", sb.toString()).commit();
            }
        } else {
            sb = new StringBuffer();
            sb.append(s);
            sb.append("@*&&*@");
            Log.i("你好！", sb.toString());
            sp.edit().putString("img_title", sb.toString()).commit();
        }

    }

    /**
     * 将搜索的内容拿到，并转化为字符串
     *
     * @param context
     * @return
     */
    public static StringBuffer getStringToCache(Context context) {
        @SuppressWarnings("static-access")
        StringBuffer sb = null;
        SharedPreferences sp = context.getSharedPreferences(
                "SEARCH_RECORD", context.MODE_APPEND);
        String img_title = sp.getString("img_title", null);
        if (!TextUtils.isEmpty(img_title)) {
            sb = new StringBuffer(img_title);
            return sb;
        }
        return null;
    }

    /**
     * 将搜索的内容拿到，并转化为字符串数组
     *
     * @param context
     * @return
     */
    public static ArrayList getArrayListToCache(Context context) {
        ArrayList list = null;
        StringBuffer sb = getStringToCache(context);
        if (!TextUtils.isEmpty(sb)) {
            list = new ArrayList();
            String img_title = sb.toString();
            String[] array = img_title.split("@\\*&&\\*@");
            for (String s : array) {
                if (s != null) {
                    list.add(s);
                }
            }
            return list;
        }
        return null;
    }

    public static ArrayList getExcludeStringArray(Context context, String str) {
        ArrayList<String> arrayList = getArrayListToCache(context);
        ArrayList exList = null;
        if (arrayList != null) {
            exList = new ArrayList<>();
            for (String s : arrayList) {
                if (s.contains(str)) {
                    exList.add(s);
                }
            }
            if (exList != null && exList.size() > 0) {
                return exList;
            }
        }
        return null;
    }

    public static void deleteString(Context context, String string) {
        StringBuffer stringBuffer = null;

        ArrayList<String> list = getArrayListToCache(context);
        if (list!=null&&!TextUtils.isEmpty(string)){
             context.getSharedPreferences(
                    "SEARCH_RECORD", context.MODE_APPEND).edit().clear().commit();
            list.remove(string);
            stringBuffer =new StringBuffer();
            SharedPreferences sp = context.getSharedPreferences(
                    "SEARCH_RECORD", context.MODE_APPEND);
            for (String s : list) {
                stringBuffer.append(s);
                stringBuffer.append("@*&&*@");
                sp.edit().putString("img_title", stringBuffer.toString()).commit();

            }
        }



    }
}