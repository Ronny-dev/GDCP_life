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
import android.widget.EditText;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.Fragment.card.javabean.cardManager_javabean;
import com.example.ronny_xie.gdcp.Fragment.card.javabean.personData_Javabean;
import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.loginActivity.LoginPage;
import com.example.ronny_xie.gdcp.util.ToastUtil;

import static com.example.ronny_xie.gdcp.Fragment.card.cardClient.cardTopUp;
import static com.example.ronny_xie.gdcp.R.id.card;
import static com.example.ronny_xie.gdcp.styleInActivity.styleActivity.viewState.init;

/**
 * Created by Ronny on 2017/9/22.
 */

public class CardTopUp extends Activity {

    private static final int SHOWTOAST = 1001;
    private personData_Javabean bean;
    private String mNo;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_card_topup);
        super.onCreate(savedInstanceState);

        mNo = getIntent().getStringExtra("no");

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == SHOWTOAST) {
                    ToastUtil.show(CardTopUp.this, (String) msg.obj);
                }
                return false;
            }
        });

        initBar();
        initView();
        initTopUp();
    }

    private void initTopUp() {
        final EditText edt = (EditText) findViewById(R.id.activity_card_topup_edt);
        Button btnCommit = (Button) findViewById(R.id.activity_card_topup_commit);
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String isSuccess = cardClient.cardTopUp(mNo, edt.getText().toString());
                        handler.sendMessage(handler.obtainMessage(SHOWTOAST, isSuccess));
                        if(isSuccess.contains("成功"))
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            },2000);
                    }
                }).start();
            }
        });
    }

    private void initView() {
        final TextView tvName = (TextView) findViewById(R.id.activity_card_manager_name);
        final TextView tvNo = (TextView) findViewById(R.id.activity_card_manager_no);
        new Thread(new Runnable() {
            @Override
            public void run() {
                bean = cardClient.check();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvName.setText(bean.getCard().get(0).getName());
                        tvNo.setText(bean.getCard().get(0).getSno());
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
