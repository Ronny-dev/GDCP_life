package com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.tag;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.page.jw_main_page;
import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.tag.bean.SimpleTitleTip;
import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.tag.bean.Tip;
import com.example.ronny_xie.gdcp.R;

import java.util.ArrayList;

/**
 * Created by WYF on 2017/7/28.
 */

public class SelectTabActivity extends Activity {

    private ArrayList<String> tag_arr;
    private EasyTipDragView easyTipDragView;
    private static final String DRAG_DATA = "DRAG_DATA";
    private static final String ADD_DATA = "ADD_DATA";
    private ImageView back;
    private ArrayList<Tip> tips;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_tab);
        super.onCreate(savedInstanceState);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTagData(tips);
                startActivity(new Intent(SelectTabActivity.this, jw_main_page.class));
                finish();
            }
        });
        initTagArr();
        initEasyTipDragView();
    }

    @Override
    public void onBackPressed() {
        saveTagData(tips);
        startActivity(new Intent(SelectTabActivity.this, jw_main_page.class));
        super.onBackPressed();
    }

    private void initEasyTipDragView() {
        easyTipDragView = (EasyTipDragView) findViewById(R.id.easyTipDragView);
        //设置已包含的标签数据
        tips = (ArrayList)TipDataModel.getDragTips(getTagData(DRAG_DATA));
        easyTipDragView.setDragData(tips);
        //设置可以添加的标签数据
        easyTipDragView.setAddData(TipDataModel.getAddTips(getTagData(ADD_DATA)));
        //设置更改数据的回调
        easyTipDragView.setOnChangeListener(new EasyTipDragView.OnDataChangeResultCallback() {
            @Override
            public void onDataChangeResult(ArrayList<Tip> tips) {
                SelectTabActivity.this.tips = tips;
            }
        });
        easyTipDragView.open();
    }

    private void saveTagData(ArrayList<Tip> tips) {
        SharedPreferences.Editor sp = getSharedPreferences("info", Context.MODE_PRIVATE).edit();
        StringBuffer drag_arr = new StringBuffer();
        StringBuffer add_arr = new StringBuffer();
        for (Tip tip : tips) {
            String tag = ((SimpleTitleTip) tip).getTip();
            drag_arr.append(tag).append("#Z&#");
        }
        for (String s : tag_arr) {
            if (!drag_arr.toString().contains(s)) {
                add_arr.append(s).append("#Z&#");
            }
        }
        sp.putString(DRAG_DATA, drag_arr.toString());
        sp.putString(ADD_DATA, add_arr.toString());
        sp.commit();
    }

    //获取已添加标签数据
    private ArrayList<String> getTagData(String key) {
        SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        String data = sp.getString(key, "");
        ArrayList<String> list = new ArrayList<>();
        if ("".equals(data)) {
            return list;
        }
        String[] drag_data_arr = data.split("#Z&#");
        for (String s : drag_data_arr) {
            list.add(s);
        }
        return list;
    }

    private void initTagArr() {
        tag_arr = new ArrayList<>();
        tag_arr.add("土木");
        tag_arr.add("汽车");
        tag_arr.add("运输");
        tag_arr.add("信息");
        tag_arr.add("轨道");
        tag_arr.add("海事");
        tag_arr.add("商贸");
        tag_arr.add("电子");
        tag_arr.add("机电");
    }
}
