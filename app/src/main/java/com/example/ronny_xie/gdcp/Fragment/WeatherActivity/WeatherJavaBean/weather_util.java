package com.example.ronny_xie.gdcp.Fragment.WeatherActivity.WeatherJavaBean;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ronny_xie on 2017/1/26.
 */
public class weather_util {
    private static final String TAG="weather_util";
    //将传入的inputstring 转换成String
    public static String InputStringToString(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            while (-1 != (len = is.read(buffer))) {
                baos.write(buffer, 0, len);
                baos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String s = baos.toString();
        return s;
    }

    //将传入的String格式处理成Json
    public static String ToJson(String s) {
        String a[] = s.split("=");
        a[1] = a[1].substring(0, a[1].lastIndexOf(";"));
        return a[1];
    }

    public static String getDataBackgroundFromBing() {
        String resultDataTitleBackground = null;
        String requestBingTitleImage = "http://guolin.tech/api/bing_pic";
        try {
            URL Uri = new URL(requestBingTitleImage);
            HttpURLConnection conn = (HttpURLConnection) Uri.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                resultDataTitleBackground = weather_util.InputStringToString(is);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultDataTitleBackground;
    }

    public static ArrayList<javabean_weather_main_pad> mainPadDataFormat(Document doc) {
        ArrayList<javabean_weather_main_pad> data = new ArrayList<javabean_weather_main_pad>();
        Elements day = doc.getElementsByClass("day");
        Elements elementsByClass_daybuttom = day.get(0).getElementsByClass("day_bottom");
        Elements div = elementsByClass_daybuttom.get(0).getElementsByTag("div");
        Elements elementsByTag_li = day.get(0).getElementsByTag("li");
        for (int i = 0; i < elementsByTag_li.size(); i++) {
            javabean_weather_main_pad mainData = new javabean_weather_main_pad();
            mainData.setDay(elementsByTag_li.get(i).text().toString());
            mainData.setImageUrl(div.get(i).getElementsByTag("img").get(0).attr("src"));
            String p = elementsByClass_daybuttom.get(0).getElementsByTag("p").get(i).text().toString();
            String[] split = p.split("/");
            mainData.setMaxTem(split[0]);
            mainData.setMinTem(split[1]);
            data.add(mainData);
        }
        return data;
    }

    public static String[] getDataMainPad(Document doc) {
        String tempp = doc.getElementById("tempp").text();
        String attr_image_now = doc.getElementById("liveImgUrl").attr("src");
        String livehf = doc.getElementById("livehf").text();
        String attr_image_rain = doc.getElementsByClass("icon1").get(0).getElementsByTag("img").get(0).attr("src");
        String[] data = {tempp, attr_image_now, livehf, attr_image_rain};
        return data;
    }

    public static String[] getReportText(Gson gson) {
        try {
            URL url = new URL("http://www.tqyb.com.cn/data/shorttime/gz_shorttime.js?datacache=0.5967189670200765");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                String s = weather_util.InputStringToString(is);
                String toJson = weather_util.ToJson(s);
                javabean_weather_shortReport aa = gson.fromJson(toJson, javabean_weather_shortReport.class);
                String[] data = {aa.getForecast(), aa.getRtime()};
                return data;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getAirReport(Gson gson) {
        try {
            URL url = new URL("http://www.tqyb.com.cn/data/gzWeather/gz_aqi.js?datacache=0.7089350696118857");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                String s = weather_util.InputStringToString(is);
                String toJson = weather_util.ToJson(s);
                javabean_weather_AirReport aa = gson.fromJson(toJson, javabean_weather_AirReport.class);
                String[] data = {aa.getAqi(), aa.getPm25(), aa.getAqi_level()};
                return data;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static javabean_weather_lifeNotic getLifeReport(Gson gson) {
        try {
            URL url = new URL("http://www.tqyb.com.cn/data/gzWeather/livingIndex.js?datacache=0.8153520217894259");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                String s = weather_util.InputStringToString(is);
                String toJson = weather_util.ToJson(s);
                javabean_weather_lifeNotic aa = gson.fromJson(toJson, javabean_weather_lifeNotic.class);
                return aa;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<javabean_weather_weibo> getWeiboData() {
        try {
            URL url = new URL("http://www.tqyb.com.cn/data/weibo/weibolist.js?datacache=0.5001717678783011");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                String s = weather_util.InputStringToString(is);
                String toJson = weather_util.ToJson(s);
                JSONArray array = new JSONArray(toJson);
                int a = array.length();
                final ArrayList<javabean_weather_weibo> data_javabean = new ArrayList<javabean_weather_weibo>();
                for (int i = 0; i < array.length(); i++) {
                    javabean_weather_weibo bean = new javabean_weather_weibo();
                    JSONObject object = new JSONObject(array.get(i).toString());
                    bean.setText(object.getString("text"));
                    bean.setDateTime(object.getString("dateTime"));
                    bean.setFrom(object.getString("from"));
                    data_javabean.add(bean);
                }
                return data_javabean;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static javabean_weather_popwindow1 getPopwindow1(Gson gson){
        try {
            URL url = new URL("http://www.tqyb.com.cn/data/latestWeather/gz_latestWeather.js?random=0.5482740157486112");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if(conn.getResponseCode() == 200){
                InputStream is = conn.getInputStream();
                String s = weather_util.InputStringToString(is);
                String toJson = weather_util.ToJson(s);
                javabean_weather_popwindow1 javabean = gson.fromJson(toJson,javabean_weather_popwindow1.class);
                return javabean;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

