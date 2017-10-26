package com.example.ronny_xie.gdcp.Fragment.card;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.example.ronny_xie.gdcp.Fragment.card.javabean.cardManager_javabean;
import com.example.ronny_xie.gdcp.Fragment.card.javabean.personData;
import com.example.ronny_xie.gdcp.Fragment.card.javabean.personData_Javabean;
import com.example.ronny_xie.gdcp.Fragment.card.javabean.todayData_javabean;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Ronny on 2017/4/29.
 */

public class cardClient {
    private static Response response;

    public static void getCardCode(final ImageView view) {
        OkGo.get("http://card.gdcp.cn/Login/GetValidateCode?time=1506045191430")
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Bitmap bitmap, Call call, Response response) {
                        view.setImageBitmap(bitmap);
                    }
                });
    }

    public static personData checkInCardSystem(String id, String psd, String code, Handler handler) {
        try {
            response = OkGo.post("http://card.gdcp.cn/Login/LoginBySnoQuery")
                    .headers("Accept",
                            "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .headers("Accept-Encoding", "gzip, deflate")
                    .headers("Content-Type",
                            "application/x-www-form-urlencoded")
                    .headers("Referer", "http://card.gdcp.cn/")
                    .params("sno", id)
                    .params("pwd", Base64Encoding(psd))
                    .params("ValiCode", code)
                    .params("remember", 0)
                    .params("uclass", 1)
                    .params("json", true)
                    .execute();

            if (response != null && response.code() == 200) {
                String info = response.body().string();
                //不是第一次登陆
                if (info.contains("正式卡")) {
                    return new personData();
                } else {
                    //第一次登陆
                    //Todo 处理验证码错误，密码错误
                    if (info.contains("验证码错误")) {
                        handler.sendMessage(handler.obtainMessage(1001, "验证码错误"));
                    } else if (info.contains("密码错误")) {
                        handler.sendMessage(handler.obtainMessage(1001, "密码错误"));
                    } else if (info.contains("\"IsSucceed\":true")) {
                        Gson gson = new Gson();
                        personData data;
                        data = gson.fromJson(info, personData.class);
                        return data;
                    } else {
                        handler.sendMessage(handler.obtainMessage(1002));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static personData_Javabean check() {
        try {
            response = OkGo.post("http://card.gdcp.cn/User/GetCardInfoByAccountNoParm")
                    .params("json", true)
                    .execute();
            if (response.code() == 200) {
                String info = response.body().string();
                info = info.replace("\\", "");
                info = "{" + info.substring(info.indexOf("\"card\"") - 1, info.indexOf("}]}") + 3);
                Gson gson = new Gson();
                personData_Javabean data;
                data = gson.fromJson(info, personData_Javabean.class);
                return data;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static todayData_javabean getTodayTransact(String today, String endDay, String no) {
        try {
            response = OkGo.post("http://card.gdcp.cn/Report/GetPersonTrjn")
                    .params("sdate", today)
                    .params("edate", endDay)
                    .params("account", no)
                    .params("page", 1)
                    .params("rows", 15)
                    .execute();
            if (response.code() == 200) {
                String info = response.body().string();
                Log.i("Ronny", info);
                Gson gson = new Gson();
                todayData_javabean data;
                data = gson.fromJson(info, todayData_javabean.class);
                return data;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static todayData_javabean getHistoryTransact(String today, String endDay, String no, String page) {
        try {
            response = OkGo.post("http://card.gdcp.cn/Report/GetPersonTrjn")
                    .params("sdate", today)
                    .params("edate", endDay)
                    .params("account", no)
                    .params("page", page)
                    .params("rows", 15)
                    .execute();
            if (response.code() == 200) {
                String info = response.body().string();
                Log.i("Ronny", info);
                Gson gson = new Gson();
                todayData_javabean data;
                data = gson.fromJson(info, todayData_javabean.class);
                return data;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static cardManager_javabean lostCard(String no, String psd) {
        try {
            response = OkGo.post("http://card.gdcp.cn/User/LostCard")
                    .params("acc", no)
                    .params("pwd", psd)
                    .params("json", true)
                    .execute();
            if (response.code() == 200) {
                String info = response.body().string();
                Log.i("Ronny", info);
                Gson gson = new Gson();
                cardManager_javabean cardManager = gson.fromJson(info, cardManager_javabean.class);
                return cardManager;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static cardManager_javabean unLostCard(String no, String psd) {
        try {
            response = OkGo.post("http://card.gdcp.cn/User/UnLostCard")
                    .params("acc", no)
                    .params("pwd", psd)
                    .params("json", true)
                    .execute();
            if (response.code() == 200) {
                String info = response.body().string();
                Log.i("Ronny", info);
                Gson gson = new Gson();
                cardManager_javabean cardManager = gson.fromJson(info, cardManager_javabean.class);
                return cardManager;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String cardTopUp(String no, String tranamt){
        if(tranamt.contains("."))
            tranamt = tranamt.replace(".","");
        try {
            response = OkGo.post("http://card.gdcp.cn/User/Account_Pay")
                    .params("account", no)
                    .params("acctype", "###")
                    .params("tranamt", Integer.parseInt(tranamt) * 100 + "")
                    .params("paymethod", "2")
                    .params("paytype", "使用绑定的默认账号")
                    .params("client_type", "web")
                    .params("json", true)
                    .execute();

            if(response.code() == 200){
                String info = response.body().string();
                Log.i("Ronny", info);
                info = info.replace("\\","");
                info = info.substring(info.indexOf("errmsg") + 9).substring(0, info.substring(info.indexOf("errmsg") + 9).indexOf(",") - 1);
                return info;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String Base64Encoding(String string) {
        return Base64.encodeToString(string.getBytes(), Base64.DEFAULT);
    }

    private static String Base64Decoing(String string) {
        byte[] m = Base64.decode(string, Base64.DEFAULT);// 解码后
        return new String(m);
    }
}
