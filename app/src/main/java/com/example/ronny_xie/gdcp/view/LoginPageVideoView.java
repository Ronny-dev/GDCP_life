package com.example.ronny_xie.gdcp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.VideoView;

/**
 * Created by Ronny on 2017/5/29.
 */

public class LoginPageVideoView extends VideoView {
    public LoginPageVideoView(Context context) {
        super(context);
    }

    public LoginPageVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoginPageVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        setMeasuredDimension(width, height);
    }
}
