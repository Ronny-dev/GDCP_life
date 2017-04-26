package com.example.ronny_xie.gdcp.util;

import android.app.Activity;

import com.example.ronny_xie.gdcp.R;
import com.tapadoo.alerter.Alerter;

/**
 * Created by ronny_xie on 2017/3/29.
 */

public class AlerterUtil {
    public static void noTitleAlertrrr(Activity context, String content, int icon) {
        Alerter.create(context).setText(content).setIcon(icon).setBackgroundColor(R.color.app_color).setDuration(1000).show();
    }
    public static void TitleAlerter(Activity context,String title, String content,int icon) {
        Alerter.create(context).setTitle(title).setText(content).setIcon(icon).setBackgroundColor(R.color.app_color).setDuration(1000).show();
    }

}
