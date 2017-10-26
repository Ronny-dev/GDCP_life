package com.example.ronny_xie.gdcp.Fragment;

import com.example.ronny_xie.gdcp.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ronny_xie.gdcp.Fragment.adapter.AbsGridAdapter;
import com.example.ronny_xie.gdcp.MoreActivity.WeatherActivity.WeatherJavaBean.fragment02_AsyncTask;


import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class tranroomFragment extends Fragment {
    private String[][] contents;
    private String week;
    private String ban;
    private EditText mEdt_class;
    private Spinner mSp_Week;
    private Button mBtn_serach;
    private GridView mGridView;

    private List<String> dataList;
    private ArrayAdapter<String> spinnerAdapter;
    private AbsGridAdapter secondAdapter;
    private fragment02_AsyncTask fa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_competerroom, null);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        contents = new String[8][5];
        secondAdapter = new AbsGridAdapter(getActivity());
        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                contents[j][i] = "";
            }
        }

        dataList = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            if(i<10){
            dataList.add("0"+String.valueOf(i)+"周");
            }else{
            dataList.add(String.valueOf(i)+"周");
            }
        }

    }

    private void initView() {
        mGridView = (GridView) getActivity().findViewById(R.id.courceDetail);
        mEdt_class = (EditText) getActivity().findViewById(R.id.edt_class);
        mSp_Week = (Spinner) getActivity().findViewById(R.id.switchWeek);
        mBtn_serach = (Button) getActivity().findViewById(R.id.btn_search);
        secondAdapter.setContent(contents, 8, 5);
        if (secondAdapter != null && mGridView != null) {
            mGridView.setAdapter(secondAdapter);
        }
        mBtn_serach.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ban = mEdt_class.getText().toString();
                if (ban.equals("")) {
                    Toast.makeText(getActivity(), "请输入要查询的班级", Toast.LENGTH_SHORT).show();
                    return;
                }
                week = mSp_Week.getSelectedItem().toString().substring(0,2);
                 fa = new fragment02_AsyncTask(week, ban) {
                     @Override
                     protected void onPreExecute() {
                         fa.initContents();
                     }

                     @Override
                    protected void onPostExecute(String[][] result) {
                        super.onPostExecute(result);
                        if (result != null) {
                        /*    for (int i = 0; i < 5; i++) {
                                for (int j = 0; j < 8; j++) {
                                    Log.e("JJY", "onPostExecute: " + contents[j][i]);
                                }
                            }*/
                            secondAdapter.setContent(result, 8, 5);
                            secondAdapter.notifyDataSetChanged();
                        }


                    }
                };
                fa.execute();
                Toast.makeText(getActivity(), "查询中..请稍后", Toast.LENGTH_SHORT).show();
            }
        });
        spinnerAdapter = new ArrayAdapter<String>(getActivity().getApplication(), R.layout.spinner_dropdown, dataList);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        mSp_Week.setAdapter(spinnerAdapter);
    }


}
