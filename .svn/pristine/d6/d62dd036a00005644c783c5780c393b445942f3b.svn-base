package com.example.ronny_xie.gdcp.loginActivity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;

import okhttp3.Call;
import okhttp3.Response;

public class ConnInterface {
    private static HttpClient httpClient;
    private static HttpResponse response;
    private static String realName;

    public static HttpClient getHttpclient(){
        if(httpClient == null){
            httpClient = new DefaultHttpClient();
            return httpClient;
        }
        return httpClient;
    }





    /**
     * 将传入的bytes类型gzip数组解压为正常bytes数组
     *
     * @param bytes 传入被gzip压缩的bytes数组
     * @return 返回gzip解压的bytes数组
     */
    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
        }

        return out.toByteArray();
    }

    /**
     * 传入查询集合，获取需要查询元素的值，使用java反射进行封装，简化操作
     *
     * @param html          传入返回的HTML数据
     * @param queryEntities 传入要查询的QueryEntity
     * @return 返回查询的结果
     */
    public static List<String> getValuesByKeyWords(String html,
                                                   List<QueryEntity> queryEntities) throws Exception {
        List<String> values = new ArrayList<String>();
        Element body = Jsoup.parse(html).select("body").get(0);

        for (QueryEntity entity : queryEntities) {
            Element element = body.select(entity.targetSelector).get(0);
            java.lang.reflect.Method method = null;
            String value = null;
            Class<?> clazz = element.getClass();
            if (entity.methodParms == null) {
                method = clazz.getMethod(entity.methodName);
                value = (String) method.invoke(element, new Object[]{});
            } else {
                method = clazz.getMethod(entity.methodName,
                        new Class[]{String.class});
                value = (String) method.invoke(element,
                        new Object[]{entity.methodParms});
            }
            values.add(value);
        }

        return values;
    }

    public static String getXSKCBSpinner(HttpClient httpClient,
                                         List<String> value, String stuNO, String name) {
        String s = "";
        try {
            final HttpPost httpPost = new HttpPost(
                    "http://jw2012.gdcp.cn/xskbcx.aspx?xh=" + stuNO + "&xm=" + name + "&gnmkdm=N121603");
            httpPost.addHeader("Cookie", value.get(1));
            httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httpPost.addHeader("Accept-Encoding", "gzip, deflate");
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.addHeader("Referer", "http://jw2012.gdcp.cn/xs_main.aspx?xh=" + stuNO);
            response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == 200) {
                InputStream inStream = response.getEntity().getContent();
                Header encoding = response.getEntity().getContentEncoding();
                String string = encoding.toString();
                System.out.println(string);
                ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
                byte[] buff = new byte[100]; // buff用于存放循环读取的临时数据
                int rc = 0;
                while ((rc = inStream.read(buff, 0, 100)) > 0) {
                    swapStream.write(buff, 0, rc);
                }
                byte[] in_b = swapStream.toByteArray();
                byte[] uncompress = uncompress(in_b);
                s = new String(uncompress, "GBK");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }





    /**
     * 将传入的String类型数据gzip解压为正常String数据
     *
     * @param //被gzip压缩的String数据
     * @return 返回gzip解压的String数据
     */
    public static String uncompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(
                str.getBytes("ISO-8859-1"));
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        return new String(out.toByteArray(), "GBK");
    }

    /**
     * 获取学生成绩
     * @param value      传入viewstate
     * @param stuNO      传入学号
     * @param name       传入姓名
     * @return 返回一个htmlData
     */
    public static String getXSCJfromHTML(HttpClient httpClient,
                                         List<String> value, String stuNO, String name, String[] data) {
        String s = "";
        try {
            final HttpPost httpPost = new HttpPost(
                    "http://jw2012.gdcp.cn/xscj_gc.aspx?xh=" + stuNO + "&xm="
                            + "谢" + "&gnmkdm=N121603");
            httpPost.addHeader("Cookie", value.get(1));
            httpPost.addHeader("Accept",
                    "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httpPost.addHeader("Accept-Encoding", "gzip, deflate");
            httpPost.addHeader("Content-Type",
                    "application/x-www-form-urlencoded");
            httpPost.addHeader("Referer",
                    "http://jw2012.gdcp.cn/xs_main.aspx?xh=" + stuNO);
            final List<NameValuePair> requestEntity = new ArrayList<NameValuePair>();
            requestEntity.add(new BasicNameValuePair("__VIEWSTATE", value
                    .get(0)));
            requestEntity.add(new BasicNameValuePair("ddlXN", data[0]));
            requestEntity.add(new BasicNameValuePair("ddlXQ", data[1]));
            requestEntity.add(new BasicNameValuePair("Button1", "按学期查询"));
            httpPost.setEntity(new UrlEncodedFormEntity(requestEntity, "gb2312"));
            response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == 200) {
                InputStream inStream = response.getEntity().getContent();
                Header encoding = response.getEntity().getContentEncoding();
                String string = encoding.toString();
                System.out.println(string);
                ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
                byte[] buff = new byte[100]; // buff用于存放循环读取的临时数据
                int rc = 0;
                while ((rc = inStream.read(buff, 0, 100)) > 0) {
                    swapStream.write(buff, 0, rc);
                }
                byte[] in_b = swapStream.toByteArray();
                byte[] uncompress = uncompress(in_b);
                s = new String(uncompress, "GBK");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static List<String> getClickXSCJfromHTML(HttpClient httpClient,
                                                    List<String> value, String stuNO, String name) {
        String s = "";
        List<String> values = null;
        try {
            final HttpPost httpPost = new HttpPost(
                    "http://jw2012.gdcp.cn/xscj_gc.aspx?xh=" + stuNO + "&xm="
                            + "谢" + "&gnmkdm=N121603");
            httpPost.addHeader("Cookie", value.get(1));
            httpPost.addHeader("Accept",
                    "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httpPost.addHeader("Accept-Encoding", "gzip, deflate");
            httpPost.addHeader("Content-Type",
                    "application/x-www-form-urlencoded");
            httpPost.addHeader("Referer",
                    "http://jw2012.gdcp.cn/xs_main.aspx?xh=" + stuNO);
            final List<NameValuePair> requestEntity = new ArrayList<NameValuePair>();
            httpPost.setEntity(new UrlEncodedFormEntity(requestEntity, "gb2312"));
            response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == 200) {
                InputStream inStream = response.getEntity().getContent();

                Header encoding = response.getEntity().getContentEncoding();
                String string = encoding.toString();
                System.out.println(string);
                ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
                byte[] buff = new byte[100]; // buff用于存放循环读取的临时数据
                int rc = 0;
                while ((rc = inStream.read(buff, 0, 100)) > 0) {
                    swapStream.write(buff, 0, rc);
                }
                byte[] in_b = swapStream.toByteArray();
                byte[] uncompress = uncompress(in_b);
                s = new String(uncompress, "GBK");

                List<QueryEntity> keyWords = new ArrayList<QueryEntity>();
                keyWords.add(new QueryEntity("input[name=__VIEWSTATE]", "val",
                        null));
                // 第0个值是viewstate，第1个值是cookies，第2个值是html
                values = getValuesByKeyWords(s, keyWords);
                values.add(value.get(1));
                values.add(s);
                return values;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    /**
     * 连接饭卡系统
     *
     * @param httpClient 传入一个已创建的httpclient
     */
    public static void ConnToCardSystem(HttpClient httpClient) {
        try {
            HttpGet getMainUrl = new HttpGet("http://card.gdcp.cn/pages/card/homeLogin.action");
            HttpResponse response = httpClient.execute(getMainUrl);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取加密键盘Drawable对象
     *
     * @param httpClient 传入一个已创建的httpclient对象
     * @return 返回一个Drawable对象
     */
    public static Drawable getPassImage(HttpClient httpClient) {
        Drawable drawable = null;
        try {
            HttpGet getMainUrl = new HttpGet("http://card.gdcp.cn/getpasswdPhoto.action");
            HttpResponse response = httpClient.execute(getMainUrl);
            InputStream Stream = response.getEntity().getContent();
            Bitmap bitmap = BitmapFactory.decodeStream(Stream);
            drawable = new BitmapDrawable(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawable;
    }

    /**
     * 必须调用取得验证码，否则连接失败
     *
     * @param httpClient 传入已创建的httpclient对象
     * @return
     */
    public static void GetCode(HttpClient httpClient) {
        try {
            HttpGet getMainUrl = new HttpGet("http://card.gdcp.cn/pages/card/getCheckpic.action?rand=2011.7839089697563");
            HttpResponse execute = httpClient.execute(getMainUrl);
            System.out.println(execute.getStatusLine().getStatusCode() + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 登入饭卡系统
     *
     * @param httpClient 传入一个已经创建好的httpclient对象
     * @param password   传入加密后的密码
     */
    public static String ClickInCardSystem(HttpClient httpClient, String id,
                                           String password) {
        try {
            final HttpPost httpPost = new HttpPost(
                    "http://card.gdcp.cn/pages/card/loginstudent.action");
            httpPost.addHeader(
                    "Accept",
                    "image/jpeg,application/x-ms-application,image/gif,application/xaml+xml,image/pjpeg,application/x-ms-xbap,*/*");
            httpPost.addHeader("Accept-Encoding", "gzip, deflate");
            httpPost.addHeader("Content-Type",
                    "application/x-www-form-urlencoded");
            httpPost.addHeader("Referer",
                    "http://card.gdcp.cn/pages/card/homeLogin.action");
            final List<NameValuePair> requestEntity = new ArrayList<NameValuePair>();
            requestEntity.add(new BasicNameValuePair("name", id));
            requestEntity.add(new BasicNameValuePair("userType", "1"));
            requestEntity.add(new BasicNameValuePair("passwd", password));
            requestEntity.add(new BasicNameValuePair("loginType", "2"));
            requestEntity.add(new BasicNameValuePair("rand", "2011"));
            requestEntity.add(new BasicNameValuePair("imageField.x", "26"));
            requestEntity.add(new BasicNameValuePair("imageField.y", "12"));
            httpPost.setEntity(new UrlEncodedFormEntity(requestEntity, "gb2312"));
            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                InputStream inStream = response.getEntity().getContent();
                byte[] streamToByte = StreamToByte(inStream);
                String a = new String(streamToByte, "GBK");
                return a;
            }
        } catch (Exception e) {
            System.out.println("登录失败");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取对应账号的卡号
     *
     * @param httpClient 传入一个已经创建的httpclient对象
     * @return 返回html数据
     */
    public static String GetPersonDayHtml(HttpClient httpClient) {
        try {
            HttpGet getMainUrl = new HttpGet("http://card.gdcp.cn/accounttodayTrjn.action");
            HttpResponse response = httpClient.execute(getMainUrl);
            if (response.getStatusLine().getStatusCode() == 200) {
                InputStream inStream = response.getEntity().getContent();
                ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
                byte[] buff = new byte[100]; // buff用于存放循环读取的临时数据
                int rc = 0;
                while ((rc = inStream.read(buff, 0, 100)) > 0) {
                    swapStream.write(buff, 0, rc);
                }
                byte[] in_b = swapStream.toByteArray();
                byte[] uncompress = uncompress(in_b);
                String a = new String(uncompress, "GBK");
                System.out.println(a);
                return a;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static byte[] StreamToByte(InputStream stream) {
        byte[] in_b = null;
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100]; // buff用于存放循环读取的临时数据
            int rc = 0;
            while ((rc = stream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
                in_b = swapStream.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in_b;
    }

    /**
     * 获取对应账号的卡号
     *
     * @param httpClient 传入一个已经创建的httpclient对象
     * @return 返回个人页面的html信息
     */
    public static String GetPersonAccountHTML(HttpClient httpClient) {
        try {
            HttpGet getMainUrl = new HttpGet(
                    "http://card.gdcp.cn/accountcardUser.action");
            HttpResponse response = httpClient.execute(getMainUrl);
            if (response.getStatusLine().getStatusCode() == 200) {
                InputStream inStream = response.getEntity().getContent();
                byte[] streamToByte = StreamToByte(inStream);
                String a = new String(streamToByte);
                return a;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
