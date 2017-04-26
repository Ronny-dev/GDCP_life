package com.example.ronny_xie.gdcp.MoreActivity.WeatherActivity.WeatherJavaBean;

/**
 * Created by ronny_xie on 2017/1/26.
 */

public class javabean_weather_AirReport {
    /**
     * content : 广州市环境监测中心站、广州市气象台2017年1月25日17时发布：预测2017年1月26日我市空气质量指数（AQI）为85-105，空气质量良到轻度污染，首要污染物为NO2，PM2.5平均浓度为65-75微克/立方米。预计2017年1月27日至1月28日，我市空气质量良到轻度污染。
     * aqi_level : 良-轻度污染
     * itime : 2017年01月25日 17时
     * primary_p : 二氧化氮
     * aqi : 85-105
     * ftime : 01月26日 00时-01月27日 00时
     * pm25 : 65-75
     * publisher : 黄俊
     */

    private String content;
    private String aqi_level;
    private String itime;
    private String primary_p;
    private String aqi;
    private String ftime;
    private String pm25;
    private String publisher;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAqi_level() {
        return aqi_level;
    }

    public void setAqi_level(String aqi_level) {
        this.aqi_level = aqi_level;
    }

    public String getItime() {
        return itime;
    }

    public void setItime(String itime) {
        this.itime = itime;
    }

    public String getPrimary_p() {
        return primary_p;
    }

    public void setPrimary_p(String primary_p) {
        this.primary_p = primary_p;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
