package com.example.ronny_xie.gdcp.Fragment.WeatherActivity.WeatherJavaBean;

import java.util.List;

/**
 * Created by ronny_xie on 2017/3/22.
 */

public class javabean_weather_popwindow1 {

    /**
     * latest24HF : {"publisher":"胡婷","yubao":"广州市今天中午到傍晚，阴天，有中雨，局部伴有雷电和短时大风，气温20到23℃，相对湿度70到98%，吹轻微至和缓的东南风;今天晚上到明天白天，中雨转阴天，气温19到25℃，相对湿度65到98%，吹轻微至和缓的偏东风。","ftime":"03月22日 11:00","ptime":1490151600000}
     * newTyphoon : false
     * isgaowen : false
     * ishanleng : false
     * typhoonjb : {"title":"天气分析","admingao":"摘要：昨天夜间到今天白天我市阴天为主，部分地区出现了零星小雨。预计，22-23日受高空槽和低层强盛偏南气流影响，我市将有一次大雨局部暴雨的降雨过程，并伴有短时强降水、8级左右雷雨大风、雷电等强对流天气。23日白天起降水逐渐减弱。我市已进入雨季和强对流高发期，需注意防御雷击、短时大风、强降水及其引发次生灾害；雨雾天气能见度差，需注意水陆交通安全。","ddatetime":"2017年03月21日 16时52分"}
     * weatherAnalysis : {"description":"摘要：昨天夜间到今天白天我市阴天为主，部分地区出现了零星小雨。预计，22-23日受高空槽和低层强盛偏南气流影响，我市将有一次大雨局部暴雨的降雨过程，并伴有短时强降水、8级左右雷雨大风、雷电等强对流天气。23日白天起降水逐渐减弱。我市已进入雨季和强对流高发期，需注意防御雷击、短时大风、强降水及其引发次生灾害；雨雾天气能见度差，需注意水陆交通安全。","weatherlive":"昨天夜间到今天白天我市阴天为主，部分地区出现了零星小雨，今晨各区最低气温19～21℃，白天各区最高气温23～29℃。\n20日20时至21日16时，五山观测站气温介于20.9～25.7℃之间，相对湿度69～97%，能见度1～20公里。","weatherforecast":"预计，22-23日受高空槽和低层强盛偏南气流影响，我市将有一次大雨局部暴雨的降雨过程，并伴有短时强降水、8级左右雷雨大风、雷电等强对流天气。23日白天起降水逐渐减弱。\n广州市区具体预报如下:\n3月22日：阴天，有大雨，并伴有雷电和短时大风；\n3月23日：中到大雨转阵雨；\n3月24日：阴天间多云，有零星小雨和轻雾。\n","suggestion":"我市已进入雨季和强对流高发期，需注意防御强降水和强对流及其引发的次生灾害。\n1. 请注意防御局地雷击灾害和短时大风导致的厂房工棚、临时构筑物、户外广告牌、树木倒塌等灾害。\n2. 持续强降水易引发城乡积涝、局地山洪及山体滑坡等次生灾害，特别是北部山区，请密切注意做好监测和防御工作。\n3. 未来几天雨雾天气持续，能见度低，路面湿滑，请注意水陆交通安全。\n","pdatetime":"2017年03月21日 16时52分","pdatetimes":1490086334000,"title":"22-23日我市将有强对流天气过程","deputytitle":"注意防御短时强降水雷雨大风等灾害"}
     * baseObtInfo : {"obtid":"G1099","wd2dd":"0","wd2ds":2.1,"maxwd3smaxdd":0,"temp":19.8,"maxtemp":-999.9,"mintemp":-999.9,"rh":89,"hourrf":0.1,"hourr":-999.9,"maxhourrf":-999.9}
     * gzObtInfo : {"obtid":"G1099","wd2dd":"0","wd2ds":2.1,"maxwd3smaxdd":0,"temp":19.8,"maxtemp":-999.9,"mintemp":-999.9,"rh":89,"hourrf":0.1,"hourr":-999.9,"maxhourrf":-999.9}
     * baseObtDate : 1490161800000
     * gzObtDate : 1490161800000
     * gridDataTime : 1490151600047
     * latestNews : []
     * chineseCal : 丁酉(鸡)年二月廿五
     */

    private Latest24HFBean latest24HF;
    private boolean newTyphoon;
    private boolean isgaowen;
    private boolean ishanleng;
    private TyphoonjbBean typhoonjb;
    private WeatherAnalysisBean weatherAnalysis;
    private BaseObtInfoBean baseObtInfo;
    private GzObtInfoBean gzObtInfo;
    private long baseObtDate;
    private long gzObtDate;
    private long gridDataTime;
    private String chineseCal;
    private List<?> latestNews;

    public Latest24HFBean getLatest24HF() {
        return latest24HF;
    }

    public void setLatest24HF(Latest24HFBean latest24HF) {
        this.latest24HF = latest24HF;
    }

    public boolean isNewTyphoon() {
        return newTyphoon;
    }

    public void setNewTyphoon(boolean newTyphoon) {
        this.newTyphoon = newTyphoon;
    }

    public boolean isIsgaowen() {
        return isgaowen;
    }

    public void setIsgaowen(boolean isgaowen) {
        this.isgaowen = isgaowen;
    }

    public boolean isIshanleng() {
        return ishanleng;
    }

    public void setIshanleng(boolean ishanleng) {
        this.ishanleng = ishanleng;
    }

    public TyphoonjbBean getTyphoonjb() {
        return typhoonjb;
    }

    public void setTyphoonjb(TyphoonjbBean typhoonjb) {
        this.typhoonjb = typhoonjb;
    }

    public WeatherAnalysisBean getWeatherAnalysis() {
        return weatherAnalysis;
    }

    public void setWeatherAnalysis(WeatherAnalysisBean weatherAnalysis) {
        this.weatherAnalysis = weatherAnalysis;
    }

    public BaseObtInfoBean getBaseObtInfo() {
        return baseObtInfo;
    }

    public void setBaseObtInfo(BaseObtInfoBean baseObtInfo) {
        this.baseObtInfo = baseObtInfo;
    }

    public GzObtInfoBean getGzObtInfo() {
        return gzObtInfo;
    }

    public void setGzObtInfo(GzObtInfoBean gzObtInfo) {
        this.gzObtInfo = gzObtInfo;
    }

    public long getBaseObtDate() {
        return baseObtDate;
    }

    public void setBaseObtDate(long baseObtDate) {
        this.baseObtDate = baseObtDate;
    }

    public long getGzObtDate() {
        return gzObtDate;
    }

    public void setGzObtDate(long gzObtDate) {
        this.gzObtDate = gzObtDate;
    }

    public long getGridDataTime() {
        return gridDataTime;
    }

    public void setGridDataTime(long gridDataTime) {
        this.gridDataTime = gridDataTime;
    }

    public String getChineseCal() {
        return chineseCal;
    }

    public void setChineseCal(String chineseCal) {
        this.chineseCal = chineseCal;
    }

    public List<?> getLatestNews() {
        return latestNews;
    }

    public void setLatestNews(List<?> latestNews) {
        this.latestNews = latestNews;
    }

    public static class Latest24HFBean {
        /**
         * publisher : 胡婷
         * yubao : 广州市今天中午到傍晚，阴天，有中雨，局部伴有雷电和短时大风，气温20到23℃，相对湿度70到98%，吹轻微至和缓的东南风;今天晚上到明天白天，中雨转阴天，气温19到25℃，相对湿度65到98%，吹轻微至和缓的偏东风。
         * ftime : 03月22日 11:00
         * ptime : 1490151600000
         */

        private String publisher;
        private String yubao;
        private String ftime;
        private long ptime;

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getYubao() {
            return yubao;
        }

        public void setYubao(String yubao) {
            this.yubao = yubao;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public long getPtime() {
            return ptime;
        }

        public void setPtime(long ptime) {
            this.ptime = ptime;
        }
    }

    public static class TyphoonjbBean {
        /**
         * title : 天气分析
         * admingao : 摘要：昨天夜间到今天白天我市阴天为主，部分地区出现了零星小雨。预计，22-23日受高空槽和低层强盛偏南气流影响，我市将有一次大雨局部暴雨的降雨过程，并伴有短时强降水、8级左右雷雨大风、雷电等强对流天气。23日白天起降水逐渐减弱。我市已进入雨季和强对流高发期，需注意防御雷击、短时大风、强降水及其引发次生灾害；雨雾天气能见度差，需注意水陆交通安全。
         * ddatetime : 2017年03月21日 16时52分
         */

        private String title;
        private String admingao;
        private String ddatetime;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAdmingao() {
            return admingao;
        }

        public void setAdmingao(String admingao) {
            this.admingao = admingao;
        }

        public String getDdatetime() {
            return ddatetime;
        }

        public void setDdatetime(String ddatetime) {
            this.ddatetime = ddatetime;
        }
    }

    public static class WeatherAnalysisBean {
        /**
         * description : 摘要：昨天夜间到今天白天我市阴天为主，部分地区出现了零星小雨。预计，22-23日受高空槽和低层强盛偏南气流影响，我市将有一次大雨局部暴雨的降雨过程，并伴有短时强降水、8级左右雷雨大风、雷电等强对流天气。23日白天起降水逐渐减弱。我市已进入雨季和强对流高发期，需注意防御雷击、短时大风、强降水及其引发次生灾害；雨雾天气能见度差，需注意水陆交通安全。
         * weatherlive : 昨天夜间到今天白天我市阴天为主，部分地区出现了零星小雨，今晨各区最低气温19～21℃，白天各区最高气温23～29℃。
         20日20时至21日16时，五山观测站气温介于20.9～25.7℃之间，相对湿度69～97%，能见度1～20公里。
         * weatherforecast : 预计，22-23日受高空槽和低层强盛偏南气流影响，我市将有一次大雨局部暴雨的降雨过程，并伴有短时强降水、8级左右雷雨大风、雷电等强对流天气。23日白天起降水逐渐减弱。
         广州市区具体预报如下:
         3月22日：阴天，有大雨，并伴有雷电和短时大风；
         3月23日：中到大雨转阵雨；
         3月24日：阴天间多云，有零星小雨和轻雾。

         * suggestion : 我市已进入雨季和强对流高发期，需注意防御强降水和强对流及其引发的次生灾害。
         1. 请注意防御局地雷击灾害和短时大风导致的厂房工棚、临时构筑物、户外广告牌、树木倒塌等灾害。
         2. 持续强降水易引发城乡积涝、局地山洪及山体滑坡等次生灾害，特别是北部山区，请密切注意做好监测和防御工作。
         3. 未来几天雨雾天气持续，能见度低，路面湿滑，请注意水陆交通安全。

         * pdatetime : 2017年03月21日 16时52分
         * pdatetimes : 1490086334000
         * title : 22-23日我市将有强对流天气过程
         * deputytitle : 注意防御短时强降水雷雨大风等灾害
         */

        private String description;
        private String weatherlive;
        private String weatherforecast;
        private String suggestion;
        private String pdatetime;
        private long pdatetimes;
        private String title;
        private String deputytitle;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getWeatherlive() {
            return weatherlive;
        }

        public void setWeatherlive(String weatherlive) {
            this.weatherlive = weatherlive;
        }

        public String getWeatherforecast() {
            return weatherforecast;
        }

        public void setWeatherforecast(String weatherforecast) {
            this.weatherforecast = weatherforecast;
        }

        public String getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(String suggestion) {
            this.suggestion = suggestion;
        }

        public String getPdatetime() {
            return pdatetime;
        }

        public void setPdatetime(String pdatetime) {
            this.pdatetime = pdatetime;
        }

        public long getPdatetimes() {
            return pdatetimes;
        }

        public void setPdatetimes(long pdatetimes) {
            this.pdatetimes = pdatetimes;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDeputytitle() {
            return deputytitle;
        }

        public void setDeputytitle(String deputytitle) {
            this.deputytitle = deputytitle;
        }
    }

    public static class BaseObtInfoBean {
        /**
         * obtid : G1099
         * wd2dd : 0
         * wd2ds : 2.1
         * maxwd3smaxdd : 0
         * temp : 19.8
         * maxtemp : -999.9
         * mintemp : -999.9
         * rh : 89
         * hourrf : 0.1
         * hourr : -999.9
         * maxhourrf : -999.9
         */

        private String obtid;
        private String wd2dd;
        private double wd2ds;
        private int maxwd3smaxdd;
        private double temp;
        private double maxtemp;
        private double mintemp;
        private int rh;
        private double hourrf;
        private double hourr;
        private double maxhourrf;

        public String getObtid() {
            return obtid;
        }

        public void setObtid(String obtid) {
            this.obtid = obtid;
        }

        public String getWd2dd() {
            return wd2dd;
        }

        public void setWd2dd(String wd2dd) {
            this.wd2dd = wd2dd;
        }

        public double getWd2ds() {
            return wd2ds;
        }

        public void setWd2ds(double wd2ds) {
            this.wd2ds = wd2ds;
        }

        public int getMaxwd3smaxdd() {
            return maxwd3smaxdd;
        }

        public void setMaxwd3smaxdd(int maxwd3smaxdd) {
            this.maxwd3smaxdd = maxwd3smaxdd;
        }

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public double getMaxtemp() {
            return maxtemp;
        }

        public void setMaxtemp(double maxtemp) {
            this.maxtemp = maxtemp;
        }

        public double getMintemp() {
            return mintemp;
        }

        public void setMintemp(double mintemp) {
            this.mintemp = mintemp;
        }

        public int getRh() {
            return rh;
        }

        public void setRh(int rh) {
            this.rh = rh;
        }

        public double getHourrf() {
            return hourrf;
        }

        public void setHourrf(double hourrf) {
            this.hourrf = hourrf;
        }

        public double getHourr() {
            return hourr;
        }

        public void setHourr(double hourr) {
            this.hourr = hourr;
        }

        public double getMaxhourrf() {
            return maxhourrf;
        }

        public void setMaxhourrf(double maxhourrf) {
            this.maxhourrf = maxhourrf;
        }
    }

    public static class GzObtInfoBean {
        /**
         * obtid : G1099
         * wd2dd : 0
         * wd2ds : 2.1
         * maxwd3smaxdd : 0
         * temp : 19.8
         * maxtemp : -999.9
         * mintemp : -999.9
         * rh : 89
         * hourrf : 0.1
         * hourr : -999.9
         * maxhourrf : -999.9
         */

        private String obtid;
        private String wd2dd;
        private double wd2ds;
        private int maxwd3smaxdd;
        private double temp;
        private double maxtemp;
        private double mintemp;
        private int rh;
        private double hourrf;
        private double hourr;
        private double maxhourrf;

        public String getObtid() {
            return obtid;
        }

        public void setObtid(String obtid) {
            this.obtid = obtid;
        }

        public String getWd2dd() {
            return wd2dd;
        }

        public void setWd2dd(String wd2dd) {
            this.wd2dd = wd2dd;
        }

        public double getWd2ds() {
            return wd2ds;
        }

        public void setWd2ds(double wd2ds) {
            this.wd2ds = wd2ds;
        }

        public int getMaxwd3smaxdd() {
            return maxwd3smaxdd;
        }

        public void setMaxwd3smaxdd(int maxwd3smaxdd) {
            this.maxwd3smaxdd = maxwd3smaxdd;
        }

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public double getMaxtemp() {
            return maxtemp;
        }

        public void setMaxtemp(double maxtemp) {
            this.maxtemp = maxtemp;
        }

        public double getMintemp() {
            return mintemp;
        }

        public void setMintemp(double mintemp) {
            this.mintemp = mintemp;
        }

        public int getRh() {
            return rh;
        }

        public void setRh(int rh) {
            this.rh = rh;
        }

        public double getHourrf() {
            return hourrf;
        }

        public void setHourrf(double hourrf) {
            this.hourrf = hourrf;
        }

        public double getHourr() {
            return hourr;
        }

        public void setHourr(double hourr) {
            this.hourr = hourr;
        }

        public double getMaxhourrf() {
            return maxhourrf;
        }

        public void setMaxhourrf(double maxhourrf) {
            this.maxhourrf = maxhourrf;
        }
    }
}
