package com.example.ronny_xie.gdcp.AsynkTask;

/**
 * Created by ronny_xie on 2017/1/26.
 */

public class javabean_weather_main_pad {
    private String day;
    private String imageUrl;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMaxTem() {
        return maxTem;
    }

    public void setMaxTem(String maxTem) {
        this.maxTem = maxTem;
    }

    public String getMinTem() {
        return minTem;
    }

    public void setMinTem(String minTem) {
        this.minTem = minTem;
    }

    private String maxTem;
    private String minTem;
}
