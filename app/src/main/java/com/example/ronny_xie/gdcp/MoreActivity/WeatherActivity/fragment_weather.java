package com.example.ronny_xie.gdcp.MoreActivity.WeatherActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bumptech.glide.Glide;
import com.example.ronny_xie.gdcp.MoreActivity.WeatherActivity.WeatherJavaBean.adapter_gridView_popwindows;
import com.example.ronny_xie.gdcp.MoreActivity.WeatherActivity.WeatherJavaBean.adapter_listview_popwindow3;
import com.example.ronny_xie.gdcp.MoreActivity.WeatherActivity.WeatherJavaBean.adapter_weather_main;
import com.example.ronny_xie.gdcp.MoreActivity.WeatherActivity.WeatherJavaBean.adapter_weather_weibo;
import com.example.ronny_xie.gdcp.MoreActivity.WeatherActivity.WeatherJavaBean.javabean_weather_lifeNotic;
import com.example.ronny_xie.gdcp.MoreActivity.WeatherActivity.WeatherJavaBean.javabean_weather_main;
import com.example.ronny_xie.gdcp.MoreActivity.WeatherActivity.WeatherJavaBean.javabean_weather_main_pad;
import com.example.ronny_xie.gdcp.MoreActivity.WeatherActivity.WeatherJavaBean.javabean_weather_popwindow1;
import com.example.ronny_xie.gdcp.MoreActivity.WeatherActivity.WeatherJavaBean.javabean_weather_weibo;
import com.example.ronny_xie.gdcp.MoreActivity.WeatherActivity.WeatherJavaBean.javabean_weather_worn;
import com.example.ronny_xie.gdcp.MoreActivity.WeatherActivity.WeatherJavaBean.weather_util;
import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.util.AlerterUtil;
import com.example.ronny_xie.gdcp.util.ProgressDialogUtil;
import com.example.ronny_xie.gdcp.util.SharePreferenceUtil;
import com.example.ronny_xie.gdcp.util.ToastUtil;
import com.example.ronny_xie.gdcp.view.ListViewForScrollView;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;


public class fragment_weather extends Activity {
    private static final String TAG = "fragment_weather";
    private ImageView imageBackground;
    private TextView tv_temperture;
    private ImageView imageViewNow;
    private TextView tv_rain_random;
    private ImageView imageViewRain;
    private TextView tvShortReport;
    private TextView tvReportTIme;
    private Gson gson;
    private ListView lv_main;
    private TextView tv_aqi;
    private TextView tv_pm25;
    private TextView tv_airTitle;
    private GridView gridView;
    private ListViewForScrollView listview_weibo;
    private SharedPreferences sharePreference;
    private TextView tv_update;
    private ImageView image_refresh;
    private SwipeRefreshLayout swipe;
    private Handler handler;
    private LinearLayout nearWeather;
    private LinearLayout weather_more;
    private SharedPreferences sp_refresh;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_weather);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 3) {
                    setScrollViewToTop();//让scrollview移动到顶部
                    ProgressDialogUtil.dismiss();
                    getRefresh();//定时提示刷新
                    alter("加载成功");
                }
                if (msg.what == 1000) {
                    ToastUtil.show(getBaseContext(), "刷新失败，请重试");
                    alter("刷新失败");
                }
            }
        };
        initView();//绑定控件
        OnClickListenerView();//监听点击popwindows事件
        initData();//数据
        setScrollViewToTop();//移动到顶部
        ProgressDialogUtil.dismiss();
    }

    private void getRefresh() {
        sp_refresh = SharePreferenceUtil.newSharePreference(this, "weather_refresh");
        long date;
        if (SharePreferenceUtil.getString("date", sp_refresh).equals("")) {
            date = System.currentTimeMillis();
            String date_string = String.valueOf(System.currentTimeMillis());
            SharePreferenceUtil.saveString("date", date_string, sp_refresh);
        } else {
            String date_string = SharePreferenceUtil.getString("date", sp_refresh);
            date = Long.parseLong(date_string);
            String date_new_string = String.valueOf(System.currentTimeMillis());
            SharePreferenceUtil.saveString("date", date_new_string, sp_refresh);
        }
        if ((System.currentTimeMillis() - date) / (1000 * 60 * 60) > 3) {
            ToastUtil.show(this, "数据过时，请下拉刷新数据");
        }
    }

    private void OnClickListenerView() {
        //手动刷新
        image_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipe.setRefreshing(true);
                sharePreference.edit().clear().commit();
                initData();
                swipe.setRefreshing(false);
            }
        });
        //下拉刷新
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sharePreference.edit().clear().commit();
                initData();
                swipe.setRefreshing(false);
            }
        });
        nearWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialogUtil.showProgress(fragment_weather.this, "请稍后..");
                backgroundAlpha(0.6f);
                newday_popwindow(v);
                ProgressDialogUtil.dismiss();
                popwindow1.update();
            }
        });
        weather_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundAlpha(0.6f);
                weather_more_popwindow(v);
                popwindow2.update();
            }
        });
    }

    private void initData() {
        //开启一个线程，查询突发事件
        Thread thread_worn = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://www.tqyb.com.cn/data/gzWeather/weatherTips.js?datacache=0.26693270069258257");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    if (conn.getResponseCode() == 200) {
                        String s = weather_util.InputStringToString(conn.getInputStream());
                        final javabean_weather_worn aa = gson.fromJson(weather_util.ToJson(s), javabean_weather_worn.class);
                        if (!aa.getTitle().contains("解除")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.fragment_weather_worn_linearlayout);
                                    TextView tv_form = (TextView) findViewById(R.id.fragment_weather_worn);
                                    TextView tv_time = (TextView) findViewById(R.id.fragment_weather_worn_time);
                                    linearLayout.setVisibility(View.VISIBLE);
                                    tv_form.setText("\u3000\u3000" + aa.getContent());
                                    tv_time.setText(new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(aa.getDdate()));
                                }
                            });
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(1000);
                }
            }
        });
        thread_worn.start();

        //开启四个线程，操作数据发送handler
        ProgressDialogUtil.showProgress(this, "请稍等...");
        final int[] index = {0};
        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                setBackgroundFromBing();//设置背景图片
                setUpdateTime();
                handler.sendEmptyMessage(index[0]++);
            }
        });
        mThread.start();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                setWeatherDataFromPad();//设置标题部分是时间和实时信息
                handler.sendEmptyMessage(index[0]++);
            }
        });
        thread.start();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                setAirQualityData();//设置空气质量数据
                setLifeNoice();//设置生活提示
                handler.sendEmptyMessage(index[0]++);
            }
        });
        thread1.start();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                setweiboData();//设置微博信息
                setReportTextData();//设置main里面的实时天气
                handler.sendEmptyMessage(index[0]++);
            }
        });
        thread2.start();
    }

    private void setUpdateTime() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String date = "";
                if (SharePreferenceUtil.getString("weather_update", sharePreference).equals("")) {
                    SimpleDateFormat format = new SimpleDateFormat("MM月dd日 hh:mm");
                    date = format.format(new Date());
                    SharePreferenceUtil.saveString("weather_update", date, sharePreference);
                } else {
                    date = SharePreferenceUtil.getString("weather_update", sharePreference);
                }
                tv_update.setText(date);
            }
        });
    }

    private void setScrollViewToTop() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ScrollView scrollview = (ScrollView) findViewById(R.id.fragment_weather_scrollview);
                scrollview.smoothScrollTo(0, 0);
            }
        });
    }

    private void setweiboData() {
        ArrayList<javabean_weather_weibo> data = new ArrayList<>();
        if (SharePreferenceUtil.getArrayList("weather_weibo", sharePreference, javabean_weather_weibo.class, gson) == null) {
            data = weather_util.getWeiboData();
            if (data == null) {
                Log.i(TAG, "setweiboData: 这里是查询微博信息");
                handler.sendEmptyMessage(1000);
                return;
            }
            SharePreferenceUtil.saveArrayList("weather_weibo", data, sharePreference, gson);
        } else {
            ArrayList<Object> weather_weibo = SharePreferenceUtil.getArrayList("weather_weibo", sharePreference, javabean_weather_weibo.class, gson);
            if (weather_weibo == null) {
                handler.sendEmptyMessage(1000);
                return;
            }
            for (int i = 0; i < weather_weibo.size(); i++) {
                javabean_weather_weibo javabean_data = (javabean_weather_weibo) weather_weibo.get(i);
                data.add(javabean_data);
            }
        }
        final ArrayList<javabean_weather_weibo> finalData = data;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listview_weibo.setAdapter(new adapter_weather_weibo(finalData, getBaseContext()));
            }
        });
    }

    private void setLifeNoice() {
        javabean_weather_lifeNotic aa;
        if (SharePreferenceUtil.getArrayList("weather_life", sharePreference, javabean_weather_lifeNotic.class, gson) == null) {
            aa = weather_util.getLifeReport(gson);
            if (aa == null) {
                Log.i(TAG, "setLifeNoice: 这里是查询生活指数");
                handler.sendEmptyMessage(1000);
                return;
            }
            SharePreferenceUtil.saveArrayList("weather_life", aa, sharePreference, gson);
        } else {
            ArrayList<Object> weather_life = SharePreferenceUtil.getArrayList("weather_life", sharePreference, javabean_weather_lifeNotic.class, gson);
            if (weather_life == null) {
                handler.sendEmptyMessage(1000);
                return;
            }
            aa = (javabean_weather_lifeNotic) weather_life.get(0);
        }
        String[] lifeStringData = {aa.getDressing().getIndex().substring(0, aa.getDressing().getIndex().indexOf("(")),
                aa.getUltraviolet().getLevel(), aa.getFireDanger().getInfo(),
                aa.getBodyComfortable().getLevel().substring(0, aa.getBodyComfortable().getLevel().indexOf("(")),
                aa.getField().getIndex(), aa.getDustHaze().getInfo(), aa.getMildew().getIndex(),
                aa.getUmbrella().getUmbrella(), aa.getAeroanion().getIndex(), aa.getTraffic().getIndex()};
        int[] icon = {R.drawable.weather_chuanyi, R.drawable.weather_ziwaixian, R.drawable.weather_huoxian,
                R.drawable.weather_shushidu, R.drawable.weather_liangshai, R.drawable.weather_huimai,
                R.drawable.weather_meibian, R.drawable.weather_yusan,
                R.drawable.weather_fulizi, R.drawable.weather_jiaotong};
        final ArrayList<Map<String, Object>> data_list = new ArrayList<>();
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("fragment_weather_image", icon[i]);
            map.put("fragment_weather_text", lifeStringData[i]);
            data_list.add(map);
        }
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String[] from = {"fragment_weather_image", "fragment_weather_text"};
                int[] to = {R.id.fragment_weather_image, R.id.fragment_weather_text};
                SimpleAdapter sim_adapter = new SimpleAdapter(getBaseContext(), data_list, R.layout.weather_item, from, to);
                gridView.setAdapter(sim_adapter);
            }
        });
    }

    private void setAirQualityData() {
        String aqi = "";
        String pm25 = "";
        String title = "";
        if (SharePreferenceUtil.getString("weather_air_aqi", sharePreference).equals("")) {
            final String[] data = weather_util.getAirReport(gson);
            if (data == null) {
                Log.i(TAG, "setAirQualityData: 这里是查询空气质量");
                handler.sendEmptyMessage(1000);
                return;
            }
            aqi = data[0];
            pm25 = data[1];
            title = data[2];
            SharePreferenceUtil.saveString("weather_air_aqi", aqi, sharePreference);
            SharePreferenceUtil.saveString("weather_air_pm25", pm25, sharePreference);
            SharePreferenceUtil.saveString("weather_air_title", title, sharePreference);
        } else {
            aqi = SharePreferenceUtil.getString("weather_air_aqi", sharePreference);
            pm25 = SharePreferenceUtil.getString("weather_air_pm25", sharePreference);
            title = SharePreferenceUtil.getString("weather_air_pm25", sharePreference);
        }
        final String finalAqi = aqi;
        final String finalPm2 = pm25;
        final String finalTitle = title;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_aqi.setText(finalAqi);
                tv_pm25.setText(finalPm2);
                tv_airTitle.setText(finalTitle);
            }
        });
    }

    private void setBackgroundFromBing() {
        final String ResultDataTitleBackground = "http://www.dujin.org/sys/bing/1920.php";
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(ResultDataTitleBackground).openConnection();
            conn.setConnectTimeout(3000);
            if (conn.getResponseCode() != 200 && conn.getResponseCode() != 301) {
                handler.sendEmptyMessage(1000);
                return;
            }
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "run: 运行到设置glide图片");
                    imageBackground = (ImageView) findViewById(R.id.fragment_weather_background);
                    Glide.with(getBaseContext()).load(ResultDataTitleBackground).centerCrop().into(imageBackground);
                    Log.i("AAAAAA", "run: " + ResultDataTitleBackground);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setReportTextData() {
        String title = "";
        String time = "";
        if (SharePreferenceUtil.getString("weather_report", sharePreference).equals("")) {
            final String[] data = weather_util.getReportText(gson);
            if (data == null) {
                Log.i(TAG, "setReportTextData: 这里是短时天气汇报");
                handler.sendEmptyMessage(1000);
                return;
            }
            title = data[0];
            time = data[1];
            SharePreferenceUtil.saveString("weather_report", title, sharePreference);
            SharePreferenceUtil.saveString("weather_time", time, sharePreference);
        } else {
            title = SharePreferenceUtil.getString("weather_report", sharePreference);
            time = SharePreferenceUtil.getString("weather_time", sharePreference);
        }
        final String finalTitle = title;
        final String finalTime = time;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvShortReport.setText(finalTitle);
                tvReportTIme.setText(finalTime);
            }
        });
    }

    private void setWeatherDataFromPad() {
        String temp = "";
        String rain = "";
        String now_image = "";
        String rain_image = "";
        ArrayList<javabean_weather_main_pad> weather_main_pads = null;
        if (SharePreferenceUtil.getString("weather_pad_temp", sharePreference).equals("")) {
            URL url = null;
            try {
                url = new URL("http://tqyb.com.cn/pda/");
                final org.jsoup.nodes.Document doc = Jsoup.parse(url, 5000);
                final String[] data = weather_util.getDataMainPad(doc);
                weather_main_pads = weather_util.mainPadDataFormat(doc);//把数据进行处理，封装成一个arraylist
                temp = data[0];
                rain = data[2];
                now_image = data[1];
                rain_image = data[3];
                SharePreferenceUtil.saveString("weather_pad_temp", data[0], sharePreference);
                SharePreferenceUtil.saveString("weahter_pad_rain", data[2], sharePreference);
                SharePreferenceUtil.saveString("weather_pad_now_image", data[1], sharePreference);
                SharePreferenceUtil.saveString("weather_pad_rain_image", data[3], sharePreference);
                SharePreferenceUtil.saveArrayList("weather_pad_main", weather_main_pads, sharePreference, gson);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(TAG, "setWeatherDataFromPad: 这里是详细天气预报");
                handler.sendEmptyMessage(1000);
            }
        } else {
            weather_main_pads = new ArrayList<>();
            temp = SharePreferenceUtil.getString("weather_pad_temp", sharePreference);
            rain = SharePreferenceUtil.getString("weahter_pad_rain", sharePreference);
            now_image = SharePreferenceUtil.getString("weather_pad_now_image", sharePreference);
            rain_image = SharePreferenceUtil.getString("weather_pad_rain_image", sharePreference);
            Log.i(TAG, "setWeatherDataFromPad: " + rain);
            ArrayList<Object> weather_pad_main = SharePreferenceUtil.getArrayList("weather_pad_main", sharePreference, javabean_weather_main_pad.class, gson);
            if (weather_pad_main != null)
                for (int i = 0; i < weather_pad_main.size(); i++) {
                    javabean_weather_main_pad javabean = new javabean_weather_main_pad();
                    javabean = (javabean_weather_main_pad) weather_pad_main.get(i);
                    weather_main_pads.add(javabean);
                }
        }
        final String finalTemp = temp;
        final String finalRain = rain;
        final String finalNow_image = now_image;
        final String finalRain_image = rain_image;
        final ArrayList<javabean_weather_main_pad> finalWeather_main_pads = weather_main_pads;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_temperture.setText(finalTemp + "°C");
                tv_rain_random.setText(finalRain);
                Glide.with(getBaseContext()).load("http://tqyb.com.cn" + finalNow_image).into(imageViewNow);
                Glide.with(getBaseContext()).load("http://tqyb.com.cn" + finalRain_image).into(imageViewRain);
                lv_main.setAdapter(new adapter_weather_main(finalWeather_main_pads, getBaseContext()));
            }
        });
    }


    private void initView() {
        gson = new Gson();
        weather_more = (LinearLayout) findViewById(R.id.fragment_weather_weathermore_linearlayout);
        nearWeather = (LinearLayout) findViewById(R.id.fragment_weather_nearweather_linearlayout);
        swipe = (SwipeRefreshLayout) findViewById(R.id.fragment_weather_swiperefresh);
        image_refresh = (ImageView) findViewById(R.id.fragment_weather_refresh_image);
        tv_update = (TextView) findViewById(R.id.fragment_weather_timeupdate);
        sharePreference = SharePreferenceUtil.newSharePreference(this, "weather");
        listview_weibo = (ListViewForScrollView) findViewById(R.id.fragment_weibo_listview);
        gridView = (GridView) findViewById(R.id.fragment_weather_gridview);
        tv_airTitle = (TextView) findViewById(R.id.fragment_weather_quality_title);
        tv_aqi = (TextView) findViewById(R.id.fragment_weather_aqi_textview);
        tv_pm25 = (TextView) findViewById(R.id.fragment_weather_pm25_textview);
        lv_main = (ListView) findViewById(R.id.fragment_weather_main);
        tvReportTIme = (TextView) findViewById(R.id.fragment_weather_reportTime);
        tvShortReport = (TextView) findViewById(R.id.fragment_weather_shortReport);
        imageViewRain = (ImageView) findViewById(R.id.fragment_weather_image_rain);
        tv_rain_random = (TextView) findViewById(R.id.fragment_weather_rain_random_textview);
        imageViewNow = (ImageView) findViewById(R.id.fragment_weather_image_now);
        tv_temperture = (TextView) findViewById(R.id.fragment_weather_temperture_textview);
        imageBackground = (ImageView) findViewById(R.id.fragment_weather_background);
    }

    // popwindow内容
    private PopupWindow popwindow1;

    private void newday_popwindow(View v) {
        View toolsLayout = LayoutInflater.from(this).inflate(
                R.layout.weather_newday_popwindows, null);
        initDataToPopWindows1(toolsLayout);
        popwindow1 = new PopupWindow(toolsLayout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popwindow1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popwindow1.setOutsideTouchable(true);
        popwindow1.setFocusable(true);
        popwindow1.setTouchable(true);
        popwindow1.showAsDropDown(v, 0, 0);
        popwindow1.setAnimationStyle(R.style.mypopwindow_anim_style);
        backgroundAlpha(0.4f);
        popwindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    // popwindow内容
    private PopupWindow popwindow2;

    private void weather_more_popwindow(View v) {
        View toolsLayout = LayoutInflater.from(this).inflate(
                R.layout.weather_more_popwindows, null);
        initDataToPopWindows2(toolsLayout, v);
        popwindow2 = new PopupWindow(toolsLayout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popwindow2.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popwindow2.setOutsideTouchable(true);
        popwindow2.setFocusable(true);
        popwindow2.setTouchable(true);
        popwindow2.showAsDropDown(v, 0, 0);
        popwindow2.setAnimationStyle(R.style.mypopwindow_anim_style);
        backgroundAlpha(0.4f);
        popwindow2.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    //展示第二个popwindows
    private void initDataToPopWindows2(View toolsLayout, final View v) {
        spinner = (Spinner) toolsLayout.findViewById(R.id.weather_popwindow2_spinner);
        final boolean[] isSpinnerFirst = {true};
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (!isSpinnerFirst[0]) {
                            PopupWindow popwindow3;
                            Log.i(TAG, "onItemSelected: " + position);
                            final View toolsLayout = LayoutInflater.from(getBaseContext()).inflate(R.layout.weather_popwindows3, null);
                            popwindow2.dismiss();
                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    initDataToPOpWIndows3(toolsLayout);
                                }
                            });
                            thread.start();
                            popwindow3 = new PopupWindow(toolsLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                            popwindow3.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            popwindow3.setOutsideTouchable(true);
                            popwindow3.setFocusable(true);
                            popwindow3.setTouchable(true);
                            WindowManager manager = getWindowManager();
                            int width = manager.getDefaultDisplay().getWidth();
                            popwindow3.setWidth(width);
                            popwindow3.showAtLocation(v, Gravity.NO_GRAVITY, 0, 20);
                            popwindow3.setAnimationStyle(R.style.mypopwindow_anim_style);
                            backgroundAlpha(0.4f);
                            popwindow3.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                @Override
                                public void onDismiss() {
                                    backgroundAlpha(1f);
                                }
                            });
                        }
                        isSpinnerFirst[0] = false;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );
    }

    private void initDataToPOpWIndows3(final View toolsLayout) {
        try {
            URL url = new URL("http://www.tqyb.com.cn/data/weatherForecastByDays/gz_weatherForecastByDays.js?datacache=0.30083525759734875");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                String s = weather_util.InputStringToString(is);
                String toJson = weather_util.ToJson(s);//拿到json数据
                try {
                    JSONObject obj = new JSONObject(toJson);//把String数据转换为JsonObject数据
                    final javabean_weather_main main = new javabean_weather_main();//创建一个javabean用于保存对象
                    String Select_qu = getSelectQu(spinner);
                    final JSONObject data_qu = (JSONObject) obj.get(Select_qu);//拿到第一个值“GDPY”（这里需要根据选择的spinner来操作）
                    main.setPtime(data_qu.getString("ptime"));//设置javabean
                    //Todo 这里应该设置一个adapter用于显示数据
                    this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView tv = (TextView) toolsLayout.findViewById(R.id.fragment_popwindow3_text_main_date);
                            tv.setText("广州市" + spinner.getSelectedItem().toString() + "气象台 " + main.getPtime() + " 发布");
                            GridView gridView = (GridView) toolsLayout.findViewById(R.id.fragment_weather_gridview_popwindwos);
                            gridView.setAdapter(new adapter_gridView_popwindows(getBaseContext(), data_qu));
                            ListView listView = (ListView) toolsLayout.findViewById(R.id.fragment_weather_listview_popwindow3);
                            LinearLayout linear = (LinearLayout) toolsLayout.findViewById(R.id.fragment_weather_popwindow3_linearlayout);
                            listView.setAdapter(new adapter_listview_popwindow3(getBaseContext(), data_qu, new Date().getHours()));
                            linear.setVisibility(View.VISIBLE);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getSelectQu(Spinner spinner) {
        if (spinner.getSelectedItemPosition() == 1) {
            return "GDTH";
        } else if (spinner.getSelectedItemPosition() == 2) {
            return "GDBY";
        } else if (spinner.getSelectedItemPosition() == 3) {
            return "GDYX";
        } else if (spinner.getSelectedItemPosition() == 4) {
            return "GDHZ";
        } else if (spinner.getSelectedItemPosition() == 5) {
            return "GDLW";
        } else if (spinner.getSelectedItemPosition() == 6) {
            return "GDPY";
        } else if (spinner.getSelectedItemPosition() == 7) {
            return "GDLG";
        } else if (spinner.getSelectedItemPosition() == 8) {
            return "GDNS";
        } else if (spinner.getSelectedItemPosition() == 9) {
            return "GDZC";
        } else if (spinner.getSelectedItemPosition() == 10) {
            return "GDHX";
        } else if (spinner.getSelectedItemPosition() == 11) {
            return "GDCO";
        }
        return null;
    }

    private void initDataToPopWindows1(final View toolsLayout) {
        final TextView pop1_title = (TextView) toolsLayout.findViewById(R.id.weather_popwindow1_title);
        final TextView pop1_description = (TextView) toolsLayout.findViewById(R.id.weather_popwindow1_description);
        final TextView pop1_weatherlive = (TextView) toolsLayout.findViewById(R.id.weather_popwindow1_weatherlive);
        final TextView pop1_weatherforecast = (TextView) toolsLayout.findViewById(R.id.weather_popwindow1_weatherforecast);
        final TextView pop1_suggestion = (TextView) toolsLayout.findViewById(R.id.weather_popwindow1_suggestion);
        final TextView pop1_time = (TextView) toolsLayout.findViewById(R.id.weather_popwindow1_time);
        final javabean_weather_popwindow1[] bean = {new javabean_weather_popwindow1()};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                bean[0] = weather_util.getPopwindow1(gson);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pop1_title.setText(bean[0].getWeatherAnalysis().getTitle());
                        pop1_weatherlive.setText("\u3000\u3000" + bean[0].getWeatherAnalysis().getWeatherlive());
                        pop1_description.setText("\u3000\u3000" + bean[0].getWeatherAnalysis().getDescription());
                        pop1_weatherforecast.setText("\u3000\u3000" + bean[0].getWeatherAnalysis().getWeatherforecast());
                        pop1_suggestion.setText(bean[0].getWeatherAnalysis().getSuggestion());
                        pop1_time.setText(bean[0].getWeatherAnalysis().getPdatetime());
                    }
                });

            }
        });
        thread.start();
    }

    // 背景透明度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        this.getWindow().setAttributes(lp);
    }
    public void alter(String str){
        AlerterUtil.noTitleAlertrrr(this, str, R.drawable.alerter_ic_face);
    }
}
