package com.example.ronny_xie.gdcp.Fragment.card;

import org.jsoup.nodes.Document;

import com.bumptech.glide.Glide;
import com.example.ronny_xie.gdcp.Fragment.card.javabean.personData_Javabean;
import com.example.ronny_xie.gdcp.R;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CardActivity extends Activity implements View.OnClickListener{
    private String html_data;
    private Document doc;
    private personData_Javabean personData_javabean;
    private Handler handler;
    private int SET_PERSON_DATA = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.card_activity);
        super.onCreate(savedInstanceState);
        initBar();
        initHandler();//设置回调
        initData();//获取内容，布局内容
    }

    private void initHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == SET_PERSON_DATA) {
                    initViewData();
                }
                return true;
            }
        });
    }

    private void initData() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String data = cardClient.getPersonData(cardClient.getHttpClient());
                Gson gson = new Gson();
                personData_javabean = gson.fromJson(data, personData_Javabean.class);
                handler.sendEmptyMessage(SET_PERSON_DATA);
            }
        });
        thread.start();
    }

    private void LoadImageToTitle() {
        ImageView image_Title = (ImageView) findViewById(R.id.card_activity_image);
        Glide.with(CardActivity.this)
                .load("http://img.hb.aicdn.com/4ae6629f4be1fd5ba818eb09e3d52afecc7fc79b42b72-Tp58cK_fw658")
                .into(image_Title);
    }

    private void initViewData() {
        TextView tv_name = (TextView) findViewById(R.id.fragment_card_name);
        TextView tv_num = (TextView) findViewById(R.id.fragment_card_num);
        TextView tv_belond = (TextView) findViewById(R.id.fragment_card_belone);
        TextView yue = (TextView) findViewById(R.id.fragment_card_money);
        TextView kazhuangtai = (TextView) findViewById(R.id.kazhuangtai);
        TextView dongjiezhuangtai = (TextView) findViewById(R.id.dongjiezhuangtai);
        tv_name.setText(personData_javabean.getName());
        tv_num.setText("卡号："+personData_javabean.getNumber());
        tv_belond.setText(personData_javabean.getDepartment());
        yue.setText(personData_javabean.getBalance().replace("元",""));
        kazhuangtai.setText(personData_javabean.getCardStatus());
        dongjiezhuangtai.setText(personData_javabean.getFreezeStatus());
        LoadImageToTitle();// 获取title的图片加载入iamge

        RelativeLayout re1 = (RelativeLayout) findViewById(R.id.card_activity_today);
        RelativeLayout re2 = (RelativeLayout) findViewById(R.id.card_activity_history);
        RelativeLayout re3 = (RelativeLayout) findViewById(R.id.card_activity_lost);
        RelativeLayout re4 = (RelativeLayout) findViewById(R.id.card_activity_lostclick);
        RelativeLayout re5 = (RelativeLayout) findViewById(R.id.card_activity_searchlost);
        RelativeLayout re6 = (RelativeLayout) findViewById(R.id.card_activity_exit);

        re1.setOnClickListener(this);
        re2.setOnClickListener(this);
        re3.setOnClickListener(this);
        re4.setOnClickListener(this);
        re5.setOnClickListener(this);
        re6.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_activity_today:
                Intent intent = new Intent(this,TodayActivity.class);
                startActivity(intent);
                break;
            case R.id.card_activity_history:
                break;
            case R.id.card_activity_lost:
                break;
            case R.id.card_activity_lostclick:
                break;
            case R.id.card_activity_searchlost:
                break;
            case R.id.card_activity_exit:
                finish();
                break;
        }
    }
}
