package com.example.ronny_xie.gdcp.Fragment;

import com.example.ronny_xie.gdcp.mainActivity.MainActivity;

import com.example.ronny_xie.gdcp.AsynkTask.fragment02_AsyncTask;
import com.example.ronny_xie.gdcp.R;
import com.slidingmenu.lib.SlidingMenu;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class fragment_competerRoom extends Fragment {
    private String week;
    private String ban;
    private TextView tv;
    private EditText ed_week;
    private Spinner sp;
    public static SlidingMenu menua;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_competerroom, null);
        return v;
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        tv = (TextView) getActivity().findViewById(R.id.fragment02_tv);
        ed_week = (EditText) getActivity().findViewById(R.id.fragment02_et_week);
        sp = (Spinner) getActivity().findViewById(R.id.spinner2);
        Button bt = (Button) getActivity().findViewById(R.id.search);
        ImageView bt_back = (ImageView) getActivity().findViewById(R.id.back2);
        bt_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                menua.showMenu();
            }
        });
        bt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                tv.setText("");
                ban = sp.getSelectedItem().toString();
                if (Integer.parseInt(ed_week.getText().toString().trim()) - 10 < 0) {
                    week = 0 + ed_week.getText().toString().trim();
                } else {
                    week = ed_week.getText().toString().trim();
                }
                if (week.equals("")) {
                    Toast.makeText(getActivity(), "请输入要查询的周次", Toast.LENGTH_SHORT).show();
                    return;
                }
                fragment02_AsyncTask fa = new fragment02_AsyncTask(tv, week, ban, getActivity());
                fa.execute();
                Toast.makeText(getActivity(), "查询中..请稍后", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
