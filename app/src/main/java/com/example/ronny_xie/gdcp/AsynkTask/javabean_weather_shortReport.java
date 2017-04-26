package com.example.ronny_xie.gdcp.AsynkTask;

/**
 * Created by ronny_xie on 2017/1/26.
 */

public class javabean_weather_shortReport {

    /**
     * publisher : 叶希莹
     * forecast : 预计14-17时，广州市各区晴天，气温20到24℃，吹轻微的偏北风。
     * icon : 01
     * maxt : 0
     * mint : 0
     * rtime : 2017年01月26日 13:50
     * pptime : 1485409500000
     */

    private String publisher;
    private String forecast;
    private String icon;
    private int maxt;
    private int mint;
    private String rtime;
    private long pptime;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getMaxt() {
        return maxt;
    }

    public void setMaxt(int maxt) {
        this.maxt = maxt;
    }

    public int getMint() {
        return mint;
    }

    public void setMint(int mint) {
        this.mint = mint;
    }

    public String getRtime() {
        return rtime;
    }

    public void setRtime(String rtime) {
        this.rtime = rtime;
    }

    public long getPptime() {
        return pptime;
    }

    public void setPptime(long pptime) {
        this.pptime = pptime;
    }
}
