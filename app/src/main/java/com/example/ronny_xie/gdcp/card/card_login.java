package com.example.ronny_xie.gdcp.card;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.mainActivity.MainActivity;
import com.example.ronny_xie.gdcp.view.popwindox_card_psd;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by ronny_xie on 2017/3/31.
 */

public class card_login extends Activity implements popwindox_card_psd.OnItemClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_card);
        initView();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.title);
        tv_title.setText("金融一卡通系统");
        TextView tv_login_read = (TextView) findViewById(R.id.fragment_card_login_textview);
        tv_login_read.setMovementMethod(ScrollingMovementMethod.getInstance());
        final com.gc.materialdesign.views.CheckBox checkBox = (com.gc.materialdesign.views.CheckBox) findViewById(R.id.fragment_card_login_checkbox);
        final com.gc.materialdesign.views.ButtonRectangle button = (com.gc.materialdesign.views.ButtonRectangle) findViewById(R.id.fragment_card_login_button);
        button.setEnabled(false);
        checkBox.setOncheckListener(new com.gc.materialdesign.views.CheckBox.OnCheckListener() {
            @Override
            public void onCheck(com.gc.materialdesign.views.CheckBox checkBox, boolean b) {
                if (checkBox.isCheck()) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPSD();
            }
        });
    }

    private popwindox_card_psd pop;

    private void initPSD() {
        backgroundAlpha(0.6f);
        pop = new popwindox_card_psd(this);
        ImageView image = pop.getImageView();
             //Todo 未完成
//        Glide.with(this).load("http://ngrok_xiaojie.ngrok.cc/test/Card").into(image);
        pop.showAtLocation(this.findViewById(R.id.fragment_card_login_button), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });
    }

    @Override
    public void setOnItemClick(View v) {

    }

    // 背景透明度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        this.getWindow().setAttributes(lp);
    }
}
