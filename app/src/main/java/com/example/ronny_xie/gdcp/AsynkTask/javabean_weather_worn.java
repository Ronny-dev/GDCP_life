package com.example.ronny_xie.gdcp.AsynkTask;

/**
 * Created by ronny_xie on 2017/1/30.
 */

public class javabean_weather_worn {
    /**
     * ddate : 1485734580000
     * issure : 胡东明
     * title : 解除广州低能见度天气提示
     * content : 从30日08时03分起解除广州低能见度天气提示。广州市气象台01月30日08时03分发布。
     */

    private long ddate;
    private String issure;
    private String title;
    private String content;

    public long getDdate() {
        return ddate;
    }

    public void setDdate(long ddate) {
        this.ddate = ddate;
    }

    public String getIssure() {
        return issure;
    }

    public void setIssure(String issure) {
        this.issure = issure;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
