package com.example.ronny_xie.gdcp.shop;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class shopConnList {
	private Document doc;
	private String title = "";
	public static ArrayList<shopBean> shopList;
	public static ArrayList<String> bitmap_List;

	public shopConnList() {
		shopList = new ArrayList<shopBean>();
		bitmap_List = new ArrayList<String>();
	}

	public void show(String data) {
		try {
			doc = Jsoup.connect(data).get();
			title = doc.title();
			System.out.println(title);
			Elements sh = doc.getElementsByClass("postbox");
			for (int i = 0; i < sh.size(); i++) {
				shopBean newBean = new shopBean();
				Elements elements = sh.get(i).getElementsByClass("image-frame");
				for (Element link : elements) {
					String pic_url = link.attr("abs:src");
					newBean.setPic_url(pic_url);
				}
				Elements elements2 = sh.get(i).select("h2");
				for (Element link : elements2) {
					String url = elements2.get(0).select("a").attr("abs:href");
					String title = link.text();
					newBean.setUrl(url);
					newBean.setTitle(title);
				}
				Elements elements3 = sh.get(i).getElementsByClass("detail");
				for (Element link : elements3) {
					String desc = link.text().trim();
					newBean.setDesc(desc);
				}
				Elements elements4 = sh.get(i).getElementsByClass(
						"pinglun_left");
				for (Element link : elements4) {
					String time = link.text().trim();
					newBean.setTime(time);
				}
				shopList.add(newBean);
				System.out.println(shopList.size());
			}

			for (int i = 0; i < shopList.size(); i++) {
				bitmap_List.add(shopList.get(i).getPic_url());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
