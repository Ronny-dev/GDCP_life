package com.example.ronny_xie.gdcp.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.Fragment.card.CardActivity;
import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.util.ProgressDialogUtil;
import com.example.ronny_xie.gdcp.util.ToastUtil;
import com.example.ronny_xie.gdcp.view.popwindox_card_psd;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by ronny_xie on 2017/3/31.
 */

public class cardFragment extends Activity implements popwindox_card_psd.OnItemClickListener {
    private static final String TAG = "cardFragment";
    public static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_card);
        initView();
    }

    private void initView() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 1) {
                    ProgressDialogUtil.dismiss();
                    pop.dismiss();
                    ToastUtil.show(getApplication(), "登录成功");
                    Intent intent = new Intent(getApplication(), CardActivity.class);
                    startActivity(intent);
                } else if (msg.what == -1) {
                    ProgressDialogUtil.dismiss();
                    pop.dismiss();
                    ToastUtil.show(getApplication(), "密码错误");
                } else {
                    ProgressDialogUtil.dismiss();
                    pop.dismiss();
                    ToastUtil.show(getApplication(), "未知错误，请重试");
                }
                return false;
            }
        });
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
        final HttpClient httpClient = new DefaultHttpClient();
        final Context context = this;
        backgroundAlpha(0.6f);
        pop = new popwindox_card_psd(context, httpClient);
        final ImageView image = pop.getImageView();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpGet getMainUrl = new HttpGet("http://ngrok.xiaojie.ngrok.cc/test/Card");
                    HttpResponse response = null;
                    response = httpClient.execute(getMainUrl);
                    InputStream Stream = response.getEntity().getContent();
                    Bitmap bitmap = BitmapFactory.decodeStream(Stream);
                    final Drawable drawable = new BitmapDrawable(bitmap);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            image.setBackground(drawable);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
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
        switch (v.getId()) {
            case R.id.one:
                ToastUtil.show(cardFragment.this, "1");
                break;
            case R.id.two:
                ToastUtil.show(cardFragment.this, "2");
                break;
            case R.id.three:
                ToastUtil.show(cardFragment.this, "3");
                break;
            case R.id.four:
                ToastUtil.show(cardFragment.this, "4");
                break;
            case R.id.five:
                ToastUtil.show(cardFragment.this, "5");
                break;
            case R.id.six:
                ToastUtil.show(cardFragment.this, "6");
                break;
            case R.id.seven:
                ToastUtil.show(cardFragment.this, "7");
                break;
            case R.id.eight:
                ToastUtil.show(cardFragment.this, "8");
                break;
            case R.id.nine:
                ToastUtil.show(cardFragment.this, "9");
                break;
            case R.id.zero:
                ToastUtil.show(cardFragment.this, "0");
                break;
            case R.id.clean:
                ToastUtil.show(this, "a");
                break;
            case R.id.close:
                ToastUtil.show(this, "b");
                break;
        }
    }

    // 背景透明度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        this.getWindow().setAttributes(lp);
    }
}
