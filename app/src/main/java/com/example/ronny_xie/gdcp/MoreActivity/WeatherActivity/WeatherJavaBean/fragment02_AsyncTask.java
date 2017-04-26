package com.example.ronny_xie.gdcp.MoreActivity.WeatherActivity.WeatherJavaBean;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

public class fragment02_AsyncTask extends AsyncTask<Object, Object, Object> {
	private Context context;
	private TextView tv;
	private String week;
	private String ban;
	private Document doc;
	private ArrayList<String> data = new ArrayList<String>();
	private String title = "";
	private String name;

	public fragment02_AsyncTask(TextView tv, String week, String ban,
								Context context) {
		this.tv = tv;
		this.context = context;
		this.week = week;
		this.ban = ban;
	}

	@Override
	protected Object doInBackground(Object... arg0) {
		name = ban;
		String a = "http://www2.gdcp.cn/lin/sjsx/2016-2/11.html";
		String b = "http://www2.gdcp.cn/lin/sjsx/2016-2/11%20%E9%A1%B5%202.html";
		String c = "http://www2.gdcp.cn/lin/sjsx/2016-2/11%20%E9%A1%B5%203.html";
		a = a.replace("11", week);
		b = b.replace("11", week);
		c = c.replace("11", week);
		try {
			doc = Jsoup.connect(a).get();
			title = doc.title();
			System.out.println(title);
			Elements no_1 = doc.getElementsByTag("table");
			for (Element link : no_1) {
				String linkText = link.text().trim();
				linkText = linkText.replace("徐燃柏", "");
				linkText = linkText.replace("陈长彬", "");
				data.add(linkText);
				// System.out.println(linkText);
			}
			// ------------------------
			doc = Jsoup.connect(b).get();
			title = doc.title();
			System.out.println(title);
			Elements no_2 = doc.getElementsByTag("table");
			for (Element link : no_2) {
				String linkText = link.text().trim();
				linkText = linkText.replace("徐燃柏", "");
				linkText = linkText.replace("陈长彬", "");
				data.add(linkText);
				// System.out.println(linkText);
			}
			// ------------------------
			doc = Jsoup.connect(c).get();
			title = doc.title();
			System.out.println(title);
			Elements no_3 = doc.getElementsByTag("table");
			for (Element link : no_3) {
				String linkText = link.text().trim();
				linkText = linkText.replace("徐燃柏", "");
				linkText = linkText.replace("陈长彬", "");
				data.add(linkText);
				// System.out.println(linkText);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String tem : data) {
			if (tem.contains(name) || tem.contains("星期")
					|| tem.contains("1～2 （8：10～9：45）")
					|| tem.contains("3～4 （10：10～11：45）")
					|| tem.contains("5～6 （14：15～15：55）")
					|| tem.contains("7～8 （16：10～17：40）") || tem.contains("中午")) {
				System.out.println(tem);
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		String dataOutPut1;
		for (String tem : data) {
			if (tem.contains(name) || tem.contains("星期")
					|| tem.contains("1～2 （8：10～9：45）")
					|| tem.contains("3～4 （10：10～11：45）")
					|| tem.contains("5～6 （14：15～15：55）")
					|| tem.contains("7～8 （16：10～17：40）") || tem.contains("中午")) {
				if (tem.contains("星期")) {
					tv.append("\n");
				}
				dataOutPut1 = tem + "\n";
				tv.append(dataOutPut1);
			}
		}
		if (tv.getText().toString().equals("")) {
			Toast.makeText(context, "请输入正确的周次（老师可能会把历史删除哦~）",
					Toast.LENGTH_SHORT).show();
		}
	}

}
