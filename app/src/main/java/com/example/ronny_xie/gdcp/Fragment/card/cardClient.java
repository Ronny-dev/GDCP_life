package com.example.ronny_xie.gdcp.Fragment.card;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.ronny_xie.gdcp.Fragment.cardFragment;
import com.example.ronny_xie.gdcp.loginActivity.ConnInterface;
import com.example.ronny_xie.gdcp.util.ProgressDialogUtil;
import com.example.ronny_xie.gdcp.util.ToastUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.InputStream;

import static android.R.attr.password;


/**
 * Created by Ronny on 2017/4/29.
 */

public class cardClient {
    private static HttpClient client;

    public static HttpClient getHttpClient() {
        if (client == null) {
            client = new DefaultHttpClient();
            return client;
        }
        return client;
    }

    public static InputStream getPSD(HttpClient client) {
        if (client == null) {
            final HttpParams httpParams = new BasicHttpParams();
            client = new DefaultHttpClient(httpParams);
        }
        try {
            HttpGet getMainUrl = new HttpGet("http://ngrok.xiaojie718.ngrok.cc/test/Card");
            HttpResponse response = null;
            response = client.execute(getMainUrl);
            if (response.getStatusLine().getStatusCode() == 200) {
                InputStream Stream = response.getEntity().getContent();
                return Stream;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void sendPSD(HttpClient client, Context context,String password) {
        if (client == null) {
            final HttpParams httpParams = new BasicHttpParams();
            client = new DefaultHttpClient(httpParams);
        }
        try {
            SharedPreferences sp = context.getSharedPreferences("login_config", Context.MODE_PRIVATE);
            String name = sp.getString("username", null);
            HttpGet get = new HttpGet("http://ngrok.xiaojie718.ngrok.cc/test/get?username=" + name + "&password=" + password);
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                InputStream return_data = response.getEntity().getContent();
                byte[] in_b = ConnInterface.StreamToByte(return_data);
                String data = new String(in_b);
                int message_send = Integer.parseInt(data);
                cardFragment.handler.sendEmptyMessage(message_send);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getPersonData(HttpClient client) {
        if (client == null) {
            return null;
        }
        try {
            HttpGet getMainUrl = new HttpGet("http://ngrok.xiaojie718.ngrok.cc/test/person");
            HttpResponse response = null;
            response = client.execute(getMainUrl);
            if (response.getStatusLine().getStatusCode() == 200) {
                String data = ConnInterface.parseToString(response);
                return data;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getTodayData(HttpClient client){
        if (client == null) {
            return null;
        }
        try {
            HttpGet getMainUrl = new HttpGet("http://ngrok.xiaojie718.ngrok.cc/test/current");
            HttpResponse response = null;
            response = client.execute(getMainUrl);
            if (response.getStatusLine().getStatusCode() == 200) {
                String data = ConnInterface.parseToString(response);
                return data;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
