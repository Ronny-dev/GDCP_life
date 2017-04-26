package com.example.ronny_xie.gdcp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.Fragment.cardFragment;
import com.example.ronny_xie.gdcp.loginActivity.ConnInterface;
import com.example.ronny_xie.gdcp.util.ProgressDialogUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import java.io.IOException;
import java.io.InputStream;



public class popwindox_card_psd extends PopupWindow implements View.OnClickListener {
    private HttpClient client;
    private TextView one, two, three, four, five, six, seven, eight, nine, zero, clean, close;
    private TextView[] t_arr = new TextView[6];
    private Context context;
    private View mPopView;
    private OnItemClickListener mListener;
    private ImageView layout;
    private static final String TAG = "popwindox_card_psd";
    private String password = "";

    public popwindox_card_psd(Context context, HttpClient client) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.client = client;
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
        clean.setOnClickListener(this);
        close.setOnClickListener(this);
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
        clean = (TextView) mPopView.findViewById(R.id.clean);
        close = (TextView) mPopView.findViewById(R.id.close);
        t_arr[0] = (TextView) mPopView.findViewById(R.id.popwindow_card_textview1);
        t_arr[1] = (TextView) mPopView.findViewById(R.id.popwindow_card_textview2);
        t_arr[2] = (TextView) mPopView.findViewById(R.id.popwindow_card_textview3);
        t_arr[3] = (TextView) mPopView.findViewById(R.id.popwindow_card_textview4);
        t_arr[4] = (TextView) mPopView.findViewById(R.id.popwindow_card_textview5);
        t_arr[5] = (TextView) mPopView.findViewById(R.id.popwindow_card_textview6);
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

    public ImageView getImageView() {
        return layout;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.setOnItemClick(v);
        }
        switch (v.getId()) {
            case R.id.one:
                password = password + "" + 0;
                check();
                Log.i(TAG, "clean:" + password);
                break;
            case R.id.two:
                password = password + "" + 1;
                check();
                Log.i(TAG, "clean:" + password);
                break;
            case R.id.three:
                password = password + "" + 2;
                check();
                Log.i(TAG, "clean:" + password);
                break;
            case R.id.four:
                password = password + "" + 3;
                check();
                Log.i(TAG, "clean:" + password);
                break;
            case R.id.five:
                password = password + "" + 4;
                check();
                Log.i(TAG, "clean:" + password);
                break;
            case R.id.six:
                password = password + "" + 5;
                check();
                Log.i(TAG, "clean:" + password);
                break;
            case R.id.seven:
                password = password + "" + 6;
                check();
                Log.i(TAG, "clean:" + password);
                break;
            case R.id.eight:
                password = password + "" + 7;
                check();
                Log.i(TAG, "clean:" + password);
                break;
            case R.id.nine:
                password = password + "" + 8;
                check();
                Log.i(TAG, "clean:" + password);
                break;
            case R.id.zero:
                password = password + "" + 9;
                check();
                Log.i(TAG, "clean:" + password);
                break;
            case R.id.clean:
                clean();
                break;
            case R.id.close:
                this.dismiss();
                break;
        }
    }

    private void clean() {
        if (password.length() > 0) {
            password = password.substring(0, password.length() - 1);
            t_arr[password.length()].setTextSize(0);
            Log.i(TAG, "clean: " + password);
        } else {
            this.dismiss();
        }
    }

    private void check() {
        int len = password.length();
        if (len <= 6) {
            t_arr[len - 1].setTextSize(30);
            if (password.length() == 6) {
                //Todo 这里要添加登录的逻辑，此处用弹出框代替
                ProgressDialogUtil.showProgress(context, "请稍后");
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SharedPreferences sp = context.getSharedPreferences("login_config", Context.MODE_PRIVATE);
                            String name = sp.getString("username", null);
                            HttpGet get = new HttpGet("http://ngrok.xiaojie.ngrok.cc/test/get?username=" + name + "&password=" + password);
                            HttpResponse response = client.execute(get);
                            if (response.getStatusLine().getStatusCode() == 200) {
                                InputStream return_data = response.getEntity().getContent();
                                byte[] in_b = ConnInterface.StreamToByte(return_data);
                                String data = new String(in_b);
                                int message_send = Integer.parseInt(data);
                                cardFragment.handler.sendEmptyMessage(message_send);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        }

    }

}