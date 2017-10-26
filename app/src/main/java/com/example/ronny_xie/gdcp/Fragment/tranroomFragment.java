package com.example.ronny_xie.gdcp.Fragment;

import com.example.ronny_xie.gdcp.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.example.ronny_xie.gdcp.Fragment.WeatherActivity.WeatherJavaBean.fragment02_AsyncTask;
import com.gc.materialdesign.widgets.ProgressDialog;


import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

@SuppressLint("ValidFragment")
public class tranroomFragment extends Fragment {
    private String[][] contents;
    private String week;
    private String ban[] = new String[3];
    private Spinner mSp_Week;
    private Button mBtn_serach;
    private GridView mGridView;

    private List<String> dataList;
    private ArrayAdapter<String> spinnerAdapter;
    private AbsGridAdapter secondAdapter;
    private fragment02_AsyncTask fa;
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;

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
        initSpinner();
        mGridView = (GridView) getActivity().findViewById(R.id.courceDetail);
        mBtn_serach = (Button) getActivity().findViewById(R.id.btn_search);
        secondAdapter.setContent(contents, 8, 5);
        if (secondAdapter != null && mGridView != null) {
            mGridView.setAdapter(secondAdapter);
        }
        mBtn_serach.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner1.toString().equals("")) {
                    Toast.makeText(getActivity(), "请输入要查询的班级", Toast.LENGTH_SHORT).show();
                    return;
                }
                week = mSp_Week.getSelectedItem().toString().substring(0,2);
                ban[0] = spinner1.getSelectedItem().toString();
                ban[1] = spinner2.getSelectedItem().toString();
                ban[2] = spinner3.getSelectedItem().toString();
                ProgressDialog dialog = new ProgressDialog(getActivity(), "正在加载，请稍候...");
                dialog.show();
                 fa = new fragment02_AsyncTask(week, ban, dialog) {
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
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSp_Week.setAdapter(spinnerAdapter);
    }

    private void initSpinner() {
        mSp_Week = (Spinner) getActivity().findViewById(R.id.switchWeek);
        spinner1 = (Spinner) getActivity().findViewById(R.id.fragment_comperter_spinner1);
        spinner2 = (Spinner) getActivity().findViewById(R.id.fragment_comperter_spinner2);
        spinner3 = (Spinner) getActivity().findViewById(R.id.fragment_comperter_spinner3);
    }


}
