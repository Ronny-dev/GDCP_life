package com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by cg on 2015/10/28.
 */
public class TitleImageViewPageAdapter extends PagerAdapter {
	private List<ImageView> list_view;

	public TitleImageViewPageAdapter(List<ImageView> list_view2) {
		this.list_view = list_view2;
	}

	@Override
	public int getCount() {
		return 1001;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(list_view.get(position % list_view.size()));
		return list_view.get(position % list_view.size());
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(list_view.get(position % list_view.size()));
	}
}