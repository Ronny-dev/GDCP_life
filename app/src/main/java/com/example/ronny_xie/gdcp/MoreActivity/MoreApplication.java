package com.example.ronny_xie.gdcp.MoreActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.ronny_xie.gdcp.MoreActivity.ScheduleActivity.fragment_schedule;
import com.example.ronny_xie.gdcp.MoreActivity.ShopActivity.fragment_shop;
import com.example.ronny_xie.gdcp.MoreActivity.WeatherActivity.fragment_weather;
import com.example.ronny_xie.gdcp.R;

/**
 * Created by ronny_xie on 2017/4/11.
 */

public class MoreApplication extends Activity implements View.OnClickListener {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_activity);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.more_activity_collapsing);
        collapsingToolbarLayout.setTitle("nihao");
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleColor(Color.rgb(205, 133, 63));
        initViews();
    }

    private void initViews() {
        RelativeLayout layoutSchedule = (RelativeLayout) findViewById(R.id.layoutschedule);
        RelativeLayout layoutWeather = (RelativeLayout) findViewById(R.id.layoutweather);
        RelativeLayout layoutShow = (RelativeLayout) findViewById(R.id.layoutshop);
        layoutSchedule.setOnClickListener(this);
        layoutWeather.setOnClickListener(this);
        layoutShow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutschedule:
                intent = new Intent(getApplicationContext(), fragment_schedule.class);
                startActivity(intent);
                break;
            case R.id.layoutweather:
                intent = new Intent(getApplicationContext(), fragment_weather.class);
                startActivity(intent);
                break;
            case R.id.layoutshop:
                intent = new Intent(getApplicationContext(),fragment_shop.class);
                startActivity(intent);
                break;
        }
    }
}
