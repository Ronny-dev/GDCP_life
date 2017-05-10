package com.example.ronny_xie.gdcp.loginActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.mainActivity.MainActivity;
import com.example.ronny_xie.gdcp.util.ProgressDialogUtil;
import com.example.ronny_xie.gdcp.util.SharePreferenceUtil;
import com.example.ronny_xie.gdcp.util.ToastUtil;
import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeDelegate;
import com.gotye.api.GotyeStatusCode;
import com.gotye.api.GotyeUser;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.List;

import developer.shivam.library.DiagonalView;


public class WelcomePage extends FragmentActivity implements OnGestureListener {
    private GestureDetector mGesture = null;
    Button mButLogin;
    EditText mEdtName, mEdtPsd;
    String mUsername;
    String mPassword;
    public static HttpClient httpClient = null;
    private List<String> values;
    private Drawable drawable;
    private ImageView image;
    private Handler handler;
    private EditText code;
    private static final String TAG = "LoginPage";
    public static final int NAMEEXIST = 1001;
    public static final int SCRIPTTAG = 3;
    public static final int SETIMAGECODE = 1;
    private View view;
    private String username;
    private DiagonalView diagonal_top;
    private DiagonalView diagonal_bottom;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //初始化
        int code = GotyeAPI.getInstance().init(this, MyApplication.APPKEY);
        //判断当前登陆状态
        int state = GotyeAPI.getInstance().isOnline();
        GotyeUser us = GotyeAPI.getInstance().getLoginUser();
        //没有登陆需要显示登陆界面
        setContentView(R.layout.activity_login);
        //注意添加LoginListener
        GotyeAPI.getInstance().addListener(mDelegate);
        mGesture = new GestureDetector(this, this);

        httpClient = new DefaultHttpClient();
        ProgressDialogUtil.showProgress(WelcomePage.this, "正在连接..请稍后");
        initView();
        initBtnListener();
        initHander();

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                values = ConnInterface.Conn(httpClient);
                drawable = ConnInterface.GetImageCode(httpClient);
                handler.sendEmptyMessage(SETIMAGECODE);
            }
        });
        thread.start();
        initAnimation();
    }
    //设计动画事件
    private void initAnimation() {
        {
            diagonal_top = (DiagonalView) findViewById(R.id.login_diagonal_top);
            diagonal_bottom = (DiagonalView) findViewById(R.id.login_diagonal_bottom);
            AnimationSet top_set = new AnimationSet(true);
            AnimationSet buttom_set = new AnimationSet(true);
            Animation alphaAnimation = new AlphaAnimation((float) 0.1, 1);
            Animation translateAnimation_top = new TranslateAnimation(-1000, 0, -300, -300);
            top_set.setFillAfter(true);
            translateAnimation_top.setInterpolator(WelcomePage.this, android.R.anim.accelerate_decelerate_interpolator);
            top_set.addAnimation(alphaAnimation);
            top_set.addAnimation(translateAnimation_top);
            top_set.setDuration(2000);
            diagonal_top.setAnimation(top_set);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    TranslateAnimation tran_top = new TranslateAnimation(0, 0, -300, 0);
                    tran_top.setDuration(1000);
                    diagonal_top.startAnimation(tran_top);
                }
            }, 2000);

            Animation translateAnimation_buttom = new TranslateAnimation(1000, 0, 300, 300);
            translateAnimation_buttom.setInterpolator(WelcomePage.this, android.R.anim.accelerate_decelerate_interpolator);
            buttom_set.setFillAfter(true);
            buttom_set.addAnimation(translateAnimation_buttom);
            buttom_set.addAnimation(alphaAnimation);
            diagonal_bottom.setAnimation(translateAnimation_buttom);
            buttom_set.setDuration(2000);
            diagonal_bottom.setAnimation(buttom_set);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    TranslateAnimation tran_top = new TranslateAnimation(0, 0, 300, 0);
                    tran_top.setDuration(1000);
                    diagonal_bottom.startAnimation(tran_top);
                }
            }, 2000);

            RelativeLayout tv_login_logo = (RelativeLayout) findViewById(R.id.login_logo);
            tv_login_logo.bringToFront();
            AlphaAnimation alphaAnimation_login_tv = new AlphaAnimation(0.1f, 1);
            alphaAnimation_login_tv.setDuration(2000);
            tv_login_logo.setAnimation(alphaAnimation_login_tv);
        }
    }
    //设计响应事件
    private void initHander() {
        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == SETIMAGECODE) {
                    image.setBackground(drawable);
                    ProgressDialogUtil.dismiss();
                } else if (msg.what == 2) {
                    if (checkUser()) {
                        // 登录的时候要传入登录监听，当重复登录时会直接返回登录状态
                        saveUser(WelcomePage.this, mUsername, mEdtPsd.getText().toString().trim(), false);
                        Intent login = new Intent(WelcomePage.this, GotyeService.class);
                        login.setAction(GotyeService.ACTION_LOGIN);
                        login.putExtra("name", mUsername);
                        if (!TextUtils.isEmpty("")) {
                            login.putExtra("pwd", "");
                        }
                        startService(login);
                        ProgressDialogUtil.showProgress(WelcomePage.this, "正在登录...");
                        ToastUtil.show(WelcomePage.this, username + "，欢迎您登录");
                    }
                } else if (msg.what == SCRIPTTAG) {
                    String a = (String) msg.obj;
                    if (a == null) {
                        ToastUtil.show(WelcomePage.this, "登录失败LoginPage");
                    } else {
                        ToastUtil.show(WelcomePage.this, (String) msg.obj);
                    }
                    httpClient = null;
                    httpClient = new DefaultHttpClient();
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            values = ConnInterface.Conn(httpClient);
                            drawable = ConnInterface.GetImageCode(httpClient);
                            handler.sendEmptyMessage(SETIMAGECODE);
                        }
                    });
                    thread.start();
                } else if (msg.what == NAMEEXIST) {
                    username = msg.obj.toString();
                    String userName = msg.obj.toString().replace("同学", "");
                    SharedPreferences userSharePreference = SharePreferenceUtil.newSharePreference(WelcomePage.this, "username");
                    SharePreferenceUtil.saveString("userName", userName, userSharePreference);
                }
            }
        };
    }
    //设计登陆按钮和验证码按钮的点击事件
    private void initBtnListener() {
        mButLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (mEdtName.getText().toString().trim().equals("")) {
                    ToastUtil.show(WelcomePage.this, "请输入用户ID");
                    return;
                } else if (mEdtPsd.getText().toString().trim().equals("")) {
                    ToastUtil.show(WelcomePage.this, "请输入密码");
                    return;
                } else if (code.getText().toString().trim().equals("")) {
                    ToastUtil.show(WelcomePage.this, "请输入验证码");
                    return;
                }
                ProgressDialogUtil.showProgress(WelcomePage.this, "正在登录...请稍后");
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        String mUser = mEdtName.getText().toString().trim();
                        String mCode = code.getText().toString().trim();
                        String mpass = mEdtPsd.getText().toString().trim();
                        String[] arr = {mUser, mpass, mCode};
                        int b = ConnInterface.ClickIn(httpClient, arr, values,
                                handler);
                        if (b == 1) {
                            //登陆成功
                            handler.sendEmptyMessage(2);
                            ProgressDialogUtil.dismiss();
                        }
                        if (b == -1) {
                            //登陆失败
                            handler.sendEmptyMessage(SCRIPTTAG);
                            ProgressDialogUtil.dismiss();
                        }
                    }
                });
                thread.start();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpClient = null;
                httpClient = new DefaultHttpClient();
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        values = ConnInterface.Conn(httpClient);
                        drawable = ConnInterface.GetImageCode(httpClient);
                        handler.sendEmptyMessage(SETIMAGECODE);
                    }
                });
                thread.start();
            }
        });
    }

    private void initView() {
        code = (EditText) findViewById(R.id.code);
        image = (ImageView) findViewById(R.id.image);
        mButLogin = (Button) findViewById(R.id.start);
        mEdtName = (EditText) findViewById(R.id.username);
        mEdtPsd = (EditText) findViewById(R.id.userpsd);
        String user[] = getUser(WelcomePage.this);
        String hasUserName = user[0];
        String hasPassWord = user[1];
        mUsername = hasUserName;
        mPassword = hasPassWord;
        bringViewToFront();
        if (mUsername != null) {
            mEdtName.setText(hasUserName);
            mEdtName.setSelection(mEdtName.getText().length());
        }
    }

    private boolean checkUser() {
        mUsername = mEdtName.getText().toString();
        boolean isValid = true;
        if (mUsername == null || mUsername.length() == 0) {
            Toast.makeText(WelcomePage.this, "请输入用户名", Toast.LENGTH_SHORT)
                    .show();
            isValid = false;
        }
        return isValid;
    }

    private void bringViewToFront() {
        ImageView icon = (ImageView) findViewById(R.id.image_icon);
        icon.bringToFront();
        LinearLayout login_linearlayout = (LinearLayout) findViewById(R.id.login_linearlayout);
        login_linearlayout.bringToFront();
        code.bringToFront();
        mEdtName.bringToFront();
        mEdtPsd.bringToFront();
    }

    public static final String CONFIG = "login_config";

    public static void saveUser(Context context, String name, String password, boolean haslogin) {
        if (TextUtils.isEmpty(name)) {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(CONFIG,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("username", name);
        edit.putString("password", password);
        edit.putBoolean("haslogin", haslogin);
        edit.commit();
    }

    public static String[] getUser(Context context) {
        SharedPreferences sp = context.getSharedPreferences(CONFIG,
                Context.MODE_PRIVATE);
        String name = sp.getString("username", null);
        String password = sp.getString("password", null);
        String[] user = new String[2];
        user[0] = name;
        user[1] = password;
        return user;
    }

    @Override
    public void onBackPressed() {
        GotyeAPI.getInstance().removeListener(mDelegate);
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGesture.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        // 移除监听
        GotyeAPI.getInstance().removeListener(mDelegate);
        super.onDestroy();
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    // 单击
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return false;
    }

    @Override
    // 长按
    public void onLongPress(MotionEvent e) {

    }
    //Gotye登陆函数
    private GotyeDelegate mDelegate = new GotyeDelegate() {

        public void onLogin(int code, GotyeUser user) {
            ProgressDialogUtil.dismiss();
            // 判断登陆是否成功
            if (code == GotyeStatusCode.CodeOK //0
                    || code == GotyeStatusCode.CodeReloginOK //5
                    || code == GotyeStatusCode.CodeOfflineLoginOK) {  //6

                // 传入已登过的状态
                String user1[] = WelcomePage.getUser(WelcomePage.this);
                String hasUserName = user1[0];
                String hasPassWord = user1[1];
                WelcomePage.saveUser(WelcomePage.this, hasUserName, hasPassWord, true);

                Intent i = new Intent(WelcomePage.this, MainActivity.class);
                startActivity(i);

                if (code == GotyeStatusCode.CodeOfflineLoginOK) {
                    Toast.makeText(WelcomePage.this, "您当前处于离线状态", Toast.LENGTH_SHORT).show();
                } else if (code == GotyeStatusCode.CodeOK) {
                    Toast.makeText(WelcomePage.this, "登录成功", Toast.LENGTH_SHORT).show();
                }
                WelcomePage.this.finish();
            } else {
                // 失败,可根据code定位失败原因
                Toast.makeText(WelcomePage.this, "登录失败 code=" + code, Toast.LENGTH_SHORT)
                        .show();
            }
        }

        public void onLogout(int code) {
        }

        public void onReconnecting(int code, GotyeUser currentLoginUser) {
        }
    };

}
