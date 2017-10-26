package com.example.ronny_xie.gdcp.Fragment.card;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ronny_xie.gdcp.Fragment.card.javabean.todayData_javabean;
import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.util.ToastUtil;

import org.feezu.liuli.timeselector.TimeSelector;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.ronny_xie.gdcp.styleInActivity.styleActivity.viewState.init;

/**
 * Created by Ronny on 2017/5/8.
 */

public class HistoryActivity extends Activity{
    private String no;
    private Button btnStart;
    private Button btnEnd;
    private todayData_javabean bean;
    private LinearLayout linearLayout;
    private int pageIndex = 1;
    private String start;
    private String end;
    private LinearLayout linearBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_card_history_main);
        super.onCreate(savedInstanceState);
        no = getIntent().getStringExtra("no");
        initBar();//设置无标题
        initView();
    }

    private void initView() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        btnStart = (Button) findViewById(R.id.activity_card_history_start);
        btnEnd = (Button) findViewById(R.id.activity_card_history_end);
        linearLayout = (LinearLayout) findViewById(R.id.activity_card_history_linear);
        linearBottom = (LinearLayout) findViewById(R.id.activity_card_history_bottom);
        final Button btnSearch = (Button) findViewById(R.id.activity_card_history_search);

        String thisMonth = format.format(new Date(System.currentTimeMillis()));

        final TimeSelector timeSelector1 = new TimeSelector(HistoryActivity.this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                btnStart.setText(time.split(" ")[0]);
            }
        }, "2017-09-01 00:00", thisMonth);
        timeSelector1.setMode(TimeSelector.MODE.YMD);

        final TimeSelector timeSelector2 = new TimeSelector(HistoryActivity.this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                btnEnd.setText(time.split(" ")[0]);
            }
        }, "2017-09-01 00:00", thisMonth);
        timeSelector2.setMode(TimeSelector.MODE.YMD);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSelector1.show();
            }
        });
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSelector2.show();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String currentTime1 = String.valueOf((format.parse(btnStart.getText().toString()).getTime() / 1000));
                    String currentTime2 = String.valueOf((format.parse(btnEnd.getText().toString()).getTime() / 1000));

                    if(Integer.parseInt(currentTime1) > Integer.parseInt(currentTime2)){
                        start = btnEnd.getText().toString();
                        end = btnStart.getText().toString();
                    }else{
                        end = btnEnd.getText().toString();
                        start = btnStart.getText().toString();
                    }
                    initData(start, end);
                    linearLayout.setVisibility(View.VISIBLE);

                } catch (ParseException e) {
                    e.printStackTrace();
                    ToastUtil.show(HistoryActivity.this, "日期格式出错" + e.toString());
                }
            }
        });
    }

    private void initData(final String start, final String end) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(!start.equals("") || !end.equals("")){
                    bean = cardClient.getHistoryTransact(start, end, no, pageIndex+"");
                    initListView(bean);
                }
            }
        });
        thread.start();
    }

    private void initListView(final todayData_javabean moneyData) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView listView = (ListView) findViewById(R.id.card_activity_today_listview);
                listView.setAdapter(new TodayListViewAdapter(HistoryActivity.this, moneyData));
                initButtonView();
                linearBottom.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initButtonView() {
        Button btnShang = (Button) findViewById(R.id.activity_card_history_shang);
        Button btnXia = (Button) findViewById(R.id.activity_card_history_xia);
        btnXia.setVisibility(View.VISIBLE);
        btnShang.setVisibility(View.VISIBLE);

        if(pageIndex == 1)
            btnShang.setVisibility(View.GONE);
        if(pageIndex * 15 > bean.getTotal())
            btnXia.setVisibility(View.GONE);
        TextView tvInfo = (TextView) findViewById(R.id.activity_card_history_tv);
        tvInfo.setText("第" + pageIndex + "页，共" + bean.getTotal() + "条数据");
        btnShang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pageIndex != 1){
                    pageIndex--;
                    initData(start, end);
                }
            }
        });
        btnXia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pageIndex * 15 < bean.getTotal()){
                    pageIndex++;
                    initData(start, end);
                }
            }
        });
    }

    private void initBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    public void back(View view) {
        finish();
    }

}
