package com.example.ronny_xie.gdcp.MoreActivity.ScheduleActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.MoreActivity.WeatherActivity.WeatherJavaBean.weather_util;
import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.MoreActivity.ScheduleActivity.db.DBService;
import com.example.ronny_xie.gdcp.util.ToastUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by ronny_xie on 2017/2/3.
 */

public class fragment_schedule extends Activity {
    public static DBService dbService = null;// 数据访问对象
    public AlarmManager am;// 消息管理者
    private static final String TAG = "fragment_schedule";
    private com.example.ronny_xie.gdcp.MoreActivity.ScheduleActivity.adapter_schedule adapter_schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_schedule);
        initTitleShowDay();
        initListView();
        initButton();
    }

    private void initButton() {
        Button myButton = (Button) findViewById(R.id.fragment_schedule_button);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    final PopupWindow popwindow3;
                    final View toolsLayout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.fragment_schedule_popwindows, null);
                    popwindow3 = new PopupWindow(toolsLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                    popwindow3.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    popwindow3.setOutsideTouchable(true);
                    popwindow3.setFocusable(true);
                    popwindow3.setTouchable(true);
                    WindowManager manager = getWindowManager();
                    int width = manager.getDefaultDisplay().getWidth();
                    popwindow3.setWidth(width);
                    popwindow3.showAtLocation(v, Gravity.CENTER, 0, 0);
                    popwindow3.setAnimationStyle(R.style.mypopwindow_anim_style);
                    backgroundAlpha(0.6f);
                    popwindow3.update();
                    Button btnText = (Button) toolsLayout.findViewById(R.id.fragment_schedule_button_commit);
                    btnText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dbService.insertRecord("title", "content", "1996-11-28");
                            backgroundAlpha(1f);
                            popwindow3.dismiss();
                            adapter_schedule.notifyDataSetChanged();
                        }
                    });
                    popwindow3.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            backgroundAlpha(1f);
                        }
                    });
                }
            }
        });
    }

    private void initListView() {
        ListView myListView = (ListView) this.findViewById(R.id.fragment_schedule_listview);
        if (dbService == null) {
            dbService = new DBService(this);
        }
        if (am == null) {
            am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        }
        adapter_schedule = new adapter_schedule(this);
        myListView.setAdapter(adapter_schedule);
    }

    //初始化标题读取数据
    private void initTitleShowDay() {
        final TextView tv_day = (TextView) this.findViewById(R.id.fragment_schedule_day);
        final TextView tv_month = (TextView) this.findViewById(R.id.fragment_schedule_month);
        final TextView tv_weekend = (TextView) this.findViewById(R.id.fragment_schedule_weekend);
        final TextView tv_nongli = (TextView) this.findViewById(R.id.fragment_schedule_nongli);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final Date date = new Date();
                String format1 = new SimpleDateFormat("yyyy-MM-dd").format(date);
                String titleData = connTitleDay(format1);
                Log.i(TAG, "run: " + titleData);
                Gson gson = new Gson();
                final javabean_schedule_day data = gson.fromJson(titleData, javabean_schedule_day.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (data != null) {
                            String[] yangli = data.getResult().getYangli().split("-");
                            tv_day.setText(yangli[2]);
                            tv_month.setText(yangli[1] + "月");
                            tv_nongli.setText(data.getResult().getYinli());
                            tv_weekend.setText(new SimpleDateFormat("EEEE").format(date));
                        } else {
                            ToastUtil.show(getApplicationContext(), "连接服务器失败！");
                        }
                    }
                });
            }
        });
        thread.start();
    }

    //开启网络连接，访问接口
    private String connTitleDay(String format1) {
        String s = "http://v.juhe.cn/laohuangli/d?date=" + format1 + "&key=c546567dcaf7696159d956eed40f960d";
        try {
            URL url = new URL(s);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                return weather_util.InputStringToString(is);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 背景透明度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        this.getWindow().setAttributes(lp);
    }
}
