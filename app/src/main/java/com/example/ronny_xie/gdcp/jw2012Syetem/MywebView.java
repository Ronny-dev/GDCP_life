package com.example.ronny_xie.gdcp.jw2012Syetem;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.ronny_xie.gdcp.R;

public class MywebView extends Activity {
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.webview);
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		String url = intent.getStringExtra("url");
		WebView webView = (WebView) findViewById(R.id.webView);
		webView.loadUrl(url);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);  
		webSettings.setUseWideViewPort(true);//关键点  
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webSettings.setDisplayZoomControls(false);  
		webSettings.setAllowFileAccess(true); // 允许访问文件  
		webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮  
		webSettings.setSupportZoom(true); // 支持缩放  
		webSettings.setLoadWithOverviewMode(true);  
		  
		DisplayMetrics metrics = new DisplayMetrics();
		  getWindowManager().getDefaultDisplay().getMetrics(metrics);  
		  int mDensity = metrics.densityDpi;  
		  if (mDensity == 240) {   
		   webSettings.setDefaultZoom(ZoomDensity.FAR);
		  } else if (mDensity == 160) {  
		     webSettings.setDefaultZoom(ZoomDensity.MEDIUM);
		  } else if(mDensity == 120) {  
		   webSettings.setDefaultZoom(ZoomDensity.CLOSE);
		  }else if(mDensity == DisplayMetrics.DENSITY_XHIGH){
		   webSettings.setDefaultZoom(ZoomDensity.FAR);
		  }else if (mDensity == DisplayMetrics.DENSITY_TV){
		   webSettings.setDefaultZoom(ZoomDensity.FAR);
		  }else{  
		      webSettings.setDefaultZoom(ZoomDensity.MEDIUM);
		  }  
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
	}
}
