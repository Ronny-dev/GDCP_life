package com.example.ronny_xie.gdcp.MoreActivity.ScheduleActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.Fragment.chat.CreateGroupSelectUser;
import com.example.ronny_xie.gdcp.Fragment.chat.page.SearchPage;
import com.example.ronny_xie.gdcp.MoreActivity.WeatherActivity.WeatherJavaBean.weather_util;
import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.MoreActivity.ScheduleActivity.db.DBService;
import com.example.ronny_xie.gdcp.util.ToastUtil;
import com.google.gson.Gson;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

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

public class schedule_fragment extends Activity {
    public static DBService dbService = null;// 数据访问对象
    public AlarmManager am;// 消息管理者
    private static final String TAG = "schedule_fragment";
    public static schedule_adapter schedule_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_schedule);
        initFloatActionButtom();//浮动按钮
        initTitleShowDay();//标题显示
        initListView();//列表显示
    }

    private void initFloatActionButtom() {
        ImageView icon = new ImageView(schedule_fragment.this);
        final FloatingActionButton actionButton = new FloatingActionButton
                .Builder(schedule_fragment.this).setContentView(icon).build();
        SubActionButton.Builder itemBUilder = new SubActionButton.Builder(schedule_fragment.this);
        ImageView itemIcon1 = new ImageView(schedule_fragment.this);
        itemIcon1.setImageDrawable(getResources().getDrawable(R.drawable.tools_add));
        SubActionButton button1 = itemBUilder.setContentView(itemIcon1).build();

        ImageView itemIcon2 = new ImageView(schedule_fragment.this);
        SubActionButton button2 = itemBUilder.setContentView(itemIcon2).build();
        itemIcon2.setImageDrawable(getResources().getDrawable(R.drawable.fab_moresearch));

        ImageView itemIcon3 = new ImageView(schedule_fragment.this);
        SubActionButton button3 = itemBUilder.setContentView(itemIcon3).build();
        itemIcon3.setImageDrawable(getResources().getDrawable(R.drawable.fab_searchfriend));

        ImageView itemIcon4 = new ImageView(schedule_fragment.this);
        SubActionButton button4 = itemBUilder.setContentView(itemIcon4).build();
        itemIcon4.setImageDrawable(getResources().getDrawable(R.drawable.weather_ziwaixian));
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(schedule_fragment.this,editorView.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(schedule_fragment.this,com.example.ronny_xie.gdcp.MoreActivity.WeatherActivity.fragment_weather.class);
                startActivity(intent);
            }
        });

        final FloatingActionMenu actionMenu = new FloatingActionMenu
                .Builder(schedule_fragment.this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .addSubActionView(button4)
                .attachTo(actionButton).build();
        final Thread[] thread = {null};
        final boolean[] isThread_run = {false};
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!actionMenu.isOpen()) {
                    actionMenu.open(true);
                } else {
                    actionMenu.close(true);
                    if (!isThread_run[0] && thread[0] != null) {
                        thread[0] = null;
                        return;
                    }
                }
                isThread_run[0] = true;
                thread[0] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (actionMenu.isOpen()) {
                                        actionMenu.close(true);
                                        isThread_run[0] = false;
                                    }
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread[0].start();
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
        schedule_adapter = new schedule_adapter(this);
        myListView.setAdapter(schedule_adapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(schedule_fragment.this,editorView.class);
                intent.putExtra("item",parent.getCount()-position);
                startActivity(intent);
            }
        });
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
