package com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.Myprogressbar;
import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.bean.jwxscj_javabean;
import com.example.ronny_xie.gdcp.R;

public class jwxscj_adapter extends BaseAdapter {
	private ArrayList<jwxscj_javabean> data;
	private Context context;

	public jwxscj_adapter(ArrayList<jwxscj_javabean> data_bixiu,
			Context context) {
		this.data = data_bixiu;
		this.context = context;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(position<data.size()){
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(context, R.layout.adapter_jwxscj, null);
				holder.daima = (TextView) convertView
						.findViewById(R.id.jwxscj_daima);
				holder.mingcheng = (TextView) convertView
						.findViewById(R.id.jwxscj_mingcheng);
				holder.chengji = (TextView) convertView
						.findViewById(R.id.jwxscj_chengji);
				holder.xuefen = (TextView) convertView
						.findViewById(R.id.jwxscj_xuefen);
				holder.jidian = (TextView) convertView
						.findViewById(R.id.jwxscj_jidian);
				holder.fenshu = (Myprogressbar) convertView
						.findViewById(R.id.jwxscj_progressbar);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.daima.setText(data.get(position).getDaima());
			holder.mingcheng.setText(data.get(position).getMingcheng());
			holder.chengji.setText(data.get(position).getChengji() + " 分");
			holder.xuefen.setText("学分：" + data.get(position).getXuefen());
			holder.jidian.setText("绩点：" + data.get(position).getJidian());
			double socre = Double.valueOf(data.get(position).getChengji());
//			int socre = Integer.valueOf(data.get(position).getChengji()).intValue();
			holder.fenshu.setProgress((int)socre);
		}

		return convertView;
	}

	public static class ViewHolder {
		TextView daima;
		TextView mingcheng;
		TextView chengji;
		TextView xuefen;
		TextView jidian;
		Myprogressbar fenshu;
	}
}
