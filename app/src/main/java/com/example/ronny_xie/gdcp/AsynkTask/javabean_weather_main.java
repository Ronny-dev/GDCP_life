package com.example.ronny_xie.gdcp.AsynkTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ronny_xie on 2017/1/26.
 */

public class javabean_weather_main {

    private String ptime;
    private String obtid;
    private int longitude;
    private int latitude;
    private long ptimestemp;
    private long obttimestemp;
    private double hour24_maxt;
    private int hour24_mint;
    private List<String> icons_f;
    private List<String> icons_l;
    private List<String> desc_f;
    private List<String> desc_l;
    private List<String> maxt;
    private List<String> mint;
    private List<String> weekdays;
    private List<String> datedec;
    private List<Boolean> weekend;
    private List<String> hour24_t;
    private List<String> hour24_rain;
    private List<String> hour24_windd;
    private List<String> hour24_winds;

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getObtid() {
        return obtid;
    }

    public void setObtid(String obtid) {
        this.obtid = obtid;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public long getPtimestemp() {
        return ptimestemp;
    }

    public void setPtimestemp(long ptimestemp) {
        this.ptimestemp = ptimestemp;
    }

    public long getObttimestemp() {
        return obttimestemp;
    }

    public void setObttimestemp(long obttimestemp) {
        this.obttimestemp = obttimestemp;
    }

    public double getHour24_maxt() {
        return hour24_maxt;
    }

    public void setHour24_maxt(double hour24_maxt) {
        this.hour24_maxt = hour24_maxt;
    }

    public int getHour24_mint() {
        return hour24_mint;
    }

    public void setHour24_mint(int hour24_mint) {
        this.hour24_mint = hour24_mint;
    }

    public List<String> getIcons_f() {
        return icons_f;
    }

    public void setIcons_f(List<String> icons_f) {
        this.icons_f = icons_f;
    }

    public List<String> getIcons_l() {
        return icons_l;
    }

    public void setIcons_l(List<String> icons_l) {
        this.icons_l = icons_l;
    }

    public List<String> getDesc_f() {
        return desc_f;
    }

    public void setDesc_f(List<String> desc_f) {
        this.desc_f = desc_f;
    }

    public List<String> getDesc_l() {
        return desc_l;
    }

    public void setDesc_l(List<String> desc_l) {
        this.desc_l = desc_l;
    }

    public List<String> getMaxt() {
        return maxt;
    }

    public void setMaxt(ArrayList<String> maxt) {
        this.maxt = maxt;
    }

    public List<String> getMint() {
        return mint;
    }

    public void setMint(ArrayList<String> mint) {
        this.mint = mint;
    }

    public List<String> getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(List<String> weekdays) {
        this.weekdays = weekdays;
    }

    public List<String> getDatedec() {
        return datedec;
    }

    public void setDatedec(List<String> datedec) {
        this.datedec = datedec;
    }

    public List<Boolean> getWeekend() {
        return weekend;
    }

    public void setWeekend(List<Boolean> weekend) {
        this.weekend = weekend;
    }

    public List<String> getHour24_t() {
        return hour24_t;
    }

    public void setHour24_t(List<String> hour24_t) {
        this.hour24_t = hour24_t;
    }

    public List<String> getHour24_rain() {
        return hour24_rain;
    }

    public void setHour24_rain(ArrayList<String> hour24_rain) {
        this.hour24_rain = hour24_rain;
    }

    public List<String> getHour24_windd() {
        return hour24_windd;
    }

    public void setHour24_windd(List<String> hour24_windd) {
        this.hour24_windd = hour24_windd;
    }

    public List<String> getHour24_winds() {
        return hour24_winds;
    }

    public void setHour24_winds(List<String> hour24_winds) {
        this.hour24_winds = hour24_winds;
    }

    public void setHour24_t(ArrayList<String> hour24_t) {
    }
}
