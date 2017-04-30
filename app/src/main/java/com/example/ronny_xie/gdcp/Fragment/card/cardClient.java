package com.example.ronny_xie.gdcp.Fragment.card;

import android.util.Log;

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



/**
 * Created by Ronny on 2017/4/29.
 */

public class cardClient {
    private static HttpClient client;
    public static HttpClient getHttpClient(){
        if(client==null){
            client = new DefaultHttpClient();
            return client;
        }
        return client;
    }

    public static InputStream getPSD(HttpClient client) {
        if(client == null) {
            final HttpParams httpParams = new BasicHttpParams();
            client = new DefaultHttpClient(httpParams);
        }
        try {
            HttpGet getMainUrl = new HttpGet("http://ngrok.xiaojie.ngrok.cc/test/Card");
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

    public static String getPersonData(HttpClient client) {
        if (client == null) {
            return null;
        }
        try {
            HttpGet getMainUrl = new HttpGet("http://ngrok.xiaojie.ngrok.cc/test/person");
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
