package com.example.ronny_xie.gdcp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ronny_xie.gdcp.R;


public class popwindox_card_psd extends PopupWindow implements View.OnClickListener {

    private TextView one, two, three,four,five,six,seven,eight,nine,zero;
    private View mPopView;
    private OnItemClickListener mListener;
    private ImageView layout;

    public popwindox_card_psd(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
        setPopupWindow();
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        //绑定布局
        mPopView = inflater.inflate(R.layout.popwindow_card_psw, null);
        layout = (ImageView) mPopView.findViewById(R.id.fragment_card_image_password);
//        layout.setBackground(drawable);
//        Glide.with(context).load("http://ngrok_xiaojie.ngrok.cc/test/Card").into(layout);
        one = (TextView) mPopView.findViewById(R.id.one);
        two = (TextView) mPopView.findViewById(R.id.two);
        three = (TextView) mPopView.findViewById(R.id.three);
        four = (TextView) mPopView.findViewById(R.id.four);
        five = (TextView) mPopView.findViewById(R.id.five);
        six = (TextView) mPopView.findViewById(R.id.six);
        seven = (TextView) mPopView.findViewById(R.id.seven);
        eight = (TextView) mPopView.findViewById(R.id.eight);
        nine = (TextView) mPopView.findViewById(R.id.nine);
        zero = (TextView) mPopView.findViewById(R.id.zero);

    }

    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        this.setOutsideTouchable(true);
        this.setTouchable(true);
        this.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        mPopView.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = mPopView.findViewById(R.id.popwindow_card_relativelayout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    /**
     * 定义一个接口，公布出去 在Activity中操作按钮的单击事件
     */
    public interface OnItemClickListener {
        void setOnItemClick(View v);
    }
    public ImageView getImageView(){
        return layout;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (mListener != null) {
            mListener.setOnItemClick(v);
        }
    }

}