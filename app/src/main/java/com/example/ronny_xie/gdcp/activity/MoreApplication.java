package com.example.ronny_xie.gdcp.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;

import com.example.ronny_xie.gdcp.R;

/**
 * Created by ronny_xie on 2017/4/11.
 */

public class MoreApplication extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_activity);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.more_activity_collapsing);
        collapsingToolbarLayout.setTitle("nihao");
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleColor(Color.rgb(205,133,63));
    }
}
