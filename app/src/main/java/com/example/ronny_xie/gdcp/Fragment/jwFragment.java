package com.example.ronny_xie.gdcp.Fragment;

import java.util.List;

import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.page.jw_main_page;
import com.example.ronny_xie.gdcp.loginActivity.ConnInterface;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.loginActivity.WelcomePage;
import com.example.ronny_xie.gdcp.util.ProgressDialogUtil;
import com.example.ronny_xie.gdcp.util.ToastUtil;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class jwFragment extends Fragment {
    private EditText text;
    private ImageView image;
    private Drawable drawable;
    private com.gc.materialdesign.views.ButtonRectangle button;
    public static Handler handler;
    private String[] users;
    public static List<String> values;
    public static HttpClient httpClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.fragment_jw, null);
        return v;
    }

    @Override
    public void onStart() {
        System.out.println("fragment_jw启动");
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    image.setBackground(drawable);
                    ProgressDialogUtil.dismiss();
                } else if (msg.what == 2) {
                    // Todo 添加成功的操作
                    text.setText("");
                    Intent intent = new Intent(getActivity(), jw_main_page.class);
                    startActivity(intent);
                    //Todo
                } else if (msg.what == 3) {
                    text.setText("");
                    String a = (String) msg.obj;
                    if (a == null) {
                        ToastUtil.show(getActivity(), "登录失败LoginPage");
                    } else {
                        ToastUtil.show(getActivity(), (String) msg.obj);
                    }
                    httpClient = null;
                    httpClient = new DefaultHttpClient();
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            values = ConnInterface.Conn(httpClient);
                            drawable = ConnInterface
                                    .GetImageCode(httpClient);
                            handler.sendEmptyMessage(1);
                        }
                    });
                    thread.start();
                }
                super.handleMessage(msg);
            }
        };
        init();
        users = WelcomePage.getUser(getActivity());
        httpClient = new DefaultHttpClient();
        ProgressDialogUtil.showProgress(getActivity(), "正在连接..请稍后");
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                values = ConnInterface.Conn(httpClient);
                drawable = ConnInterface.GetImageCode(httpClient);
                handler.sendEmptyMessage(1);
            }
        });
        thread.start();
        super.onStart();
    }

    private void init() {
        TextView tv_changeImage = (TextView) getActivity().findViewById(
                R.id.fragment_tv_changeimage);
        button = (com.gc.materialdesign.views.ButtonRectangle) getActivity().findViewById(R.id.fragment03_button);
        image = (ImageView) getActivity().findViewById(R.id.fragment03_image);
        text = (EditText) getActivity().findViewById(R.id.fragment03_edittext);
        tv_changeImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ProgressDialogUtil.showProgress(getActivity(), "获取中");
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        drawable = ConnInterface.GetImageCodeAgain(httpClient);
                        handler.sendEmptyMessage(1);
                    }
                });
                thread.start();
            }
        });
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ProgressDialogUtil.showProgress(getActivity(), "正在登录...请稍后");
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        String mUser = users[0];
                        String mCode = text.getText().toString().trim();
                        String mpass = users[1];
                        String[] arr = {mUser, mpass, mCode};
                        int b = ConnInterface.ClickIn(httpClient, arr, values,
                                handler);
                        if (b == 1) {
                            handler.sendEmptyMessage(2);
                            ProgressDialogUtil.dismiss();
                        }
                        if (b == -1) {
                            handler.sendEmptyMessage(3);
                            ProgressDialogUtil.dismiss();
                        }
                    }
                });
                thread.start();
            }
        });
    }
}
