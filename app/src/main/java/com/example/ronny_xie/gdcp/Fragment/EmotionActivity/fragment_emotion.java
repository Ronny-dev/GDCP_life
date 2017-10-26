package com.example.ronny_xie.gdcp.Fragment.EmotionActivity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.ronny_xie.gdcp.R;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;

/**
 * Created by Eddie on 2017/7/14.
 */

public class fragment_emotion extends AppCompatActivity {

    private WebView mWebView;
    private int windowWidth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_emotion);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                ArrayList<View> outView = new ArrayList<View>();
                getWindow().getDecorView().findViewsWithText(outView, "QQ浏览器", View.FIND_VIEWS_WITH_TEXT);
                int size = outView.size();
                if (outView != null && outView.size() > 0) {
                    outView.get(0).setVisibility(View.GONE);
                }
            }
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();
        initTbs();
    }


    private void initTbs() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {

                }
                return true;
            }
        });

        mWebView.loadUrl("http://qiqu.uc.cn/?uc_param_str=frpfvedncpssntnwbipreime#!/index/index");

    }

    private void initView() {
        windowWidth = getWindowManager().getDefaultDisplay().getWidth();
        mWebView = (WebView) findViewById(R.id.tbs_webview);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private int eventX;

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            eventX = ev.getAction();
            onUserInteraction();
        }
        if(ev.getAction() == MotionEvent.ACTION_UP){
            if(ev.getX() - eventX > windowWidth / 3 * 2){
                finish();
            }
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
        }
    }
}
