package com.example.ronny_xie.gdcp.card;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.bumptech.glide.Glide;
import com.example.ronny_xie.gdcp.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class CardActivity extends Activity {

	private ImageView image_Title;
	private String html_data;
	private Document doc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.card_activity);
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		html_data = intent.getStringExtra("data");
		doc = Jsoup.parse(html_data);
		initView();
		LoadImageToTitle();// 获取title的图片加载入iamge
	}

	private void LoadImageToTitle() {
		Glide.with(CardActivity.this)
				.load("http://img.coloranges.com/1701C/sRgnWelJ.jpg")
				.into(image_Title);
	}

	private void initView() {
		image_Title = (ImageView) findViewById(R.id.card_activity_image);
		TextView tv_name = (TextView) findViewById(R.id.fragment_card_name);
		TextView tv_belond = (TextView) findViewById(R.id.fragment_card_belone);
		TextView yue = (TextView) findViewById(R.id.fragment_card_money);
		TextView kazhuangtai = (TextView) findViewById(R.id.kazhuangtai);
		TextView dongjiezhuangtai = (TextView) findViewById(R.id.dongjiezhuangtai);
		TextView guashizhuangtai = (TextView) findViewById(R.id.guashizhuangtai);
		getTitlePersonData(tv_name, tv_belond);//设置标题栏的姓名的归属
		getMiddleData(yue, kazhuangtai, dongjiezhuangtai, guashizhuangtai);//设置中间部分的数据
	}


	private void getMiddleData(TextView yue, TextView kazhuangtai,
							   TextView dongjiezhuangtai, TextView guashizhuangtai) {
		Elements elementsByClass = doc.getElementsByClass("neiwen");
		String card_yue = elementsByClass.get(46).text().toString();
		card_yue = card_yue.substring(0, card_yue.indexOf("（"));
		String card_kazhuangtai = elementsByClass.get(42).text().toString();
		String card_dongjiezhuangtai = elementsByClass.get(44).text()
				.toString();
		String card_guashizhuangtai = elementsByClass.get(50).text().toString();
		yue.setText(card_yue);
		kazhuangtai.setText(card_kazhuangtai);
		dongjiezhuangtai.setText(card_dongjiezhuangtai);
		guashizhuangtai.setText(card_guashizhuangtai);
	}

	private void getTitlePersonData(TextView tv_name, TextView tv_belond) {
		Elements elementsByClass_neiwen = doc.getElementsByClass("neiwen");
		String neiwen_name = elementsByClass_neiwen.get(1).text().toString();
		String neiwen_belone = elementsByClass_neiwen.get(28).text().toString();
		tv_name.setText("姓名：" + neiwen_name);
		tv_belond.setText("所属：" + neiwen_belone);
	}
}
