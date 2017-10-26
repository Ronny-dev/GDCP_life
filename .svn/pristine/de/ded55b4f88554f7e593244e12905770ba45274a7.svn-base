package com.example.ronny_xie.gdcp.Fragment.card;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.Fragment.card.javabean.cardManager_javabean;
import com.example.ronny_xie.gdcp.Fragment.card.javabean.personData_Javabean;
import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.loginActivity.LoginPage;
import com.example.ronny_xie.gdcp.util.ToastUtil;

/**
 * Created by Ronny on 2017/9/22.
 */

public class CardManager extends Activity {

    private String mTitle;
    private String mNo;
    private personData_Javabean bean;
    private Handler handler;
    private final int SHOWTOAST = 1001;
    private boolean isGUASHI = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_card_manager);
        super.onCreate(savedInstanceState);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == SHOWTOAST) {
                    ToastUtil.show(CardManager.this, (String) msg.obj);
                }
                return false;
            }
        });

        mTitle = getIntent().getStringExtra("cardManager_javabean");
        mNo = getIntent().getStringExtra("no");

        if(!mTitle.contains("挂失"))
            isGUASHI = true;
        else
            isGUASHI = false;

        initView();
        initBar();
        initListener();


    }

    private void initListener() {
        Button btn = (Button) findViewById(R.id.activity_card_manager_commit);
        btn.setText(!isGUASHI ? "挂失" : "解挂");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] userInfo = LoginPage.getUser(CardManager.this);
                if(!userInfo[0].equals("") && !userInfo[1].equals("")) {
                    final String psd = userInfo[1].substring(userInfo[1].length() - 6);
                    Log.i("Ronny", psd+"::"+mNo);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            cardManager_javabean cardManager;
                            if (mTitle.contains("挂失"))
                                cardManager = cardClient.lostCard(mNo, psd);
                            else
                                cardManager = cardClient.unLostCard(mNo, psd);
                            String info = cardManager.getRMsg();
                            handler.sendMessage(handler.obtainMessage(SHOWTOAST, info));
                            if(info.contains("成功"))
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 2000);
                        }
                    }).start();
                }
            }
        });
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.activity_card_manager_title);
        tvTitle.setText(mTitle);
        final TextView tvName = (TextView) findViewById(R.id.activity_card_manager_name);
        final TextView tvNo = (TextView) findViewById(R.id.activity_card_manager_no);
        final TextView tvStatue = (TextView) findViewById(R.id.activity_card_manager_statue);
        new Thread(new Runnable() {
            @Override
            public void run() {
                bean = cardClient.check();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvName.setText(bean.getCard().get(0).getName());
                        tvNo.setText(bean.getCard().get(0).getSno());
                        tvStatue.setText(bean.getCard().get(0).getLostflag().equals("0") ? "正常" : "已挂失");
                    }
                });
            }
        }).start();
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
}
