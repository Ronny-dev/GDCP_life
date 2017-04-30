package com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.page;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.bumptech.glide.Glide;
import com.example.ronny_xie.gdcp.Fragment.jwFragment;
import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.MywebView;
import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.adapter.TitleImageViewPageAdapter;
import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.adapter.jw_main_adapter;
import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.MoreActivity.MoreApplication;
import com.example.ronny_xie.gdcp.loginActivity.ConnInterface;
import com.example.ronny_xie.gdcp.loginActivity.WelcomePage;
import com.example.ronny_xie.gdcp.util.ProgressDialogUtil;
import com.example.ronny_xie.gdcp.util.ToastUtil;
import com.example.ronny_xie.gdcp.view.ListViewForScrollView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class jw_main_page extends Activity {
    private Document doc;
    private com.gc.materialdesign.views.ButtonRectangle ib2;
    private com.gc.materialdesign.views.ButtonRectangle ib3;
    private com.gc.materialdesign.views.ButtonRectangle ib4;
    private ArrayList<String> imageurl;
    private ArrayList<String> imageclickurl;
    private boolean isRun = true;
    private ViewPager list_pager;
    private List<ImageView> list_view;
    private TitleImageViewPageAdapter adpter;
    private Handler handler;
    private ArrayList<String> xw_title;
    private ArrayList<String> xw_titleUrl;
    private ArrayList<String> jj_title;
    private ArrayList<String> jj_titleUrl;
    private ListViewForScrollView listview1;
    private TextView tv1;
    private TextView tv2;
    private ListViewForScrollView listview2;
    private HttpClient httpclient;
    public static List<String> values;
    public static String[] user;
    private ImageView image_back;
    private static final String TAG = "jw_main_page";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1000) {// title切换图片
                    list_pager.setCurrentItem(msg.arg1);
                } else if (msg.what == 2) {// 设置新闻的适配器
                    listview1.setAdapter(new jw_main_adapter(xw_title,
                            jw_main_page.this));
                    tv1.setVisibility(View.VISIBLE);
                } else if (msg.what == 3) {// 设置新闻聚焦的适配器
                    listview2.setAdapter(new jw_main_adapter(jj_title,
                            jw_main_page.this));
                    tv2.setVisibility(View.VISIBLE);
                }
                super.handleMessage(msg);
            }
        };
        ProgressDialogUtil.showProgress(this, "请稍后...");
        setContentView(R.layout.jw2012main);
        super.onCreate(savedInstanceState);
        initView();// 初始化控件
        initGDCPhtmldata();// 获取广交官网上的数据
        initClickView();// 获取点击的view
        onClickListener();// 监听点击事件
    }

    // 每一个listview项的点击跳转网页的事件侦听
    private void onClickListener() {
        listview1.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(jw_main_page.this, MywebView.class);
                intent.putExtra("url", xw_titleUrl.get(position));
                startActivity(intent);
            }
        });
        listview2.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(jw_main_page.this, MywebView.class);
                intent.putExtra("url", jj_titleUrl.get(position));
                startActivity(intent);
            }
        });
        image_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // title的图片切换
    private void scyleImageChange() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (isRun) {
                    for (int i = 0; i < 500; i++) {
                        try {
                            Message msg = Message.obtain();
                            msg.what = 1000;
                            msg.arg1 = i;
                            handler.sendMessage(msg);
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        thread.start();
    }

    // 拿到标题特殊的图片和点击地址
    private void initImage() {
        Element elementById2 = doc.getElementById("header");
        Elements elementsByTag2 = elementById2.getElementsByTag("img");
        String src = elementsByTag2.get(0).attr("src");
        Elements elementsByTag = elementById2.getElementsByTag("a");
        String href = elementsByTag.get(0).attr("href");
        imageurl.add(src + "\"");
        imageclickurl.add(href + "\"");
        // 拿到script里面的图片和点击地址
        Element elementById = doc.getElementById("header");
        Elements TagScript = elementById.getElementsByTag("script").eq(1);
        for (Element element : TagScript) {
            String[] data = element.data().toString().split("var");
            String[] dataTem = new String[2];
            dataTem = getdata(data[1]);
            imageurl.add(dataTem[0]);
            dataTem = getdata(dataTem[1]);
            imageclickurl.add(dataTem[0]);
            dataTem = getdata(dataTem[1]);
            imageurl.add(dataTem[0]);
            dataTem = getdata(dataTem[1]);
            imageclickurl.add(dataTem[0]);
            dataTem = getdata(dataTem[1]);
            imageurl.add(dataTem[0]);
            dataTem = getdata(dataTem[1]);
            imageclickurl.add(dataTem[0]);
            dataTem = getdata(dataTem[1]);
            imageurl.add(dataTem[0]);
            dataTem = getdata(dataTem[1]);
            imageclickurl.add(dataTem[0]);
            dataTem = getdata(dataTem[1]);
            imageurl.add(dataTem[0]);
            dataTem = getdata(dataTem[1]);
            imageclickurl.add(dataTem[0]);
        }
        System.out.println("加载完毕");
    }

    // 拿到交院首页的新闻html和处理图片信息
    private void initGDCPhtmldata() {
        Thread thread = new Thread(new Runnable() {
            URL url;

            @Override
            public void run() {
                try {
                    url = new URL("http://a1.gdcp.cn/index.shtml");
                    doc = Jsoup.parse(url, 3000);
                    initData(doc);// 拿到首页的新闻内容
                    ProgressDialogUtil.dismiss();
                    initImage();// 获取title的图片信息
                    // 首页的title加载图片
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            list_view = new ArrayList<ImageView>();
                            for (int i = 0; i < 5; i++) {
                                ImageView view = new ImageView(
                                        getApplicationContext());
                                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                String substring = imageurl.get(i).substring(0,
                                        imageurl.get(i).length() - 1);
                                Glide.with(jw_main_page.this)
                                        .load("http://a1.gdcp.cn" + substring)
                                        .into(view);
                                list_view.add(view);
                            }
                            adpter = new TitleImageViewPageAdapter(list_view);
                            list_pager.setAdapter(adpter);
                            int currentItem = 1000 / 2;
                            list_pager.setCurrentItem(currentItem);
                        }
                    });
                } catch (Exception e) {
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            ToastUtil.show(jw_main_page.this, "出现未知错误，请重新认证登录");
                            finish();
                        }
                    });
                    e.printStackTrace();
                }
                scyleImageChange();// 设置一个线程，用于循环title图片的切换
            }
        });
        thread.start();
    }

    private void initView() {
        httpclient = ConnInterface.getHttpclient();
        values = jwFragment.values;
        user = WelcomePage.getUser(jw_main_page.this);
        image_back = (ImageView) findViewById(R.id.jw_main_back);
        list_pager = (ViewPager) findViewById(R.id.list_pager);
        tv1 = (TextView) findViewById(R.id.jw_textView1);
        tv2 = (TextView) findViewById(R.id.jw_textView2);
        listview1 = (ListViewForScrollView) findViewById(R.id.jw_listView1);
        listview2 = (ListViewForScrollView) findViewById(R.id.jw_listView2);
        xw_title = new ArrayList<String>();
        xw_titleUrl = new ArrayList<String>();
        jj_title = new ArrayList<String>();
        jj_titleUrl = new ArrayList<String>();
        imageclickurl = new ArrayList<String>();
        imageurl = new ArrayList<String>();

    }

    // 获取广交官网首页的网页新闻信息
    private void initData(final Document doc) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Elements elementsByClass = doc.getElementsByClass("jyxw_wz");
                Element element = elementsByClass.get(0);
                Elements elementsByTag = element.getElementsByTag("td");
                for (Element elementtd : elementsByTag) {
                    if (elementtd.text() != null) {
                        if (!elementtd.text().toString().equals("")) {
                            xw_title.add(elementtd.text().toString().trim());
                        }
                    }
                    if (elementtd.getElementsByTag("a") != null) {
                        if (!elementtd.getElementsByTag("a").attr("href")
                                .equals("")) {
                            xw_titleUrl.add(elementtd.getElementsByTag("a")
                                    .attr("href"));
                        }
                    }
                }
                handler.sendEmptyMessage(2);
                // --------------------------拿到交院新闻里面的标题和地址
                Elements elementsByClass2 = doc.getElementsByClass("mtjj_wz");
                Element element2 = elementsByClass2.get(0);
                Elements elementsByTag2 = element2.getElementsByTag("td");
                for (Element elementtd : elementsByTag2) {
                    if (elementtd.text() != null) {
                        if (!elementtd.text().toString().equals("")) {
                            jj_title.add(elementtd.text().toString().trim());
                        }
                    }
                    if (elementtd.getElementsByTag("a") != null) {
                        if (!elementtd.getElementsByTag("a").attr("href")
                                .equals("")) {
                            jj_titleUrl.add(elementtd.getElementsByTag("a")
                                    .attr("href"));
                        }
                    }
                }
                handler.sendEmptyMessage(3);
            }
        });
        thread.start();
    }

    // tab点击事件侦听
    private void initClickView() {
        ib2 = (com.gc.materialdesign.views.ButtonRectangle) findViewById(R.id.jw_imagebutton2);
        ib2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                // 拿到httpclient配置的信息
                ProgressDialogUtil.showProgress(jw_main_page.this, "请稍后...");
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // 连接教务系统，拿到spinner
                        values = ConnInterface.getClickXSCJfromHTML(httpclient,
                                values, user[0], "谢");
                        handler.post(new Runnable() {


                            @Override
                            public void run() {
                                show_jwxs_PopWindow(v,1);
                                initSpinnerData();
                                ProgressDialogUtil.dismiss();
                                tools_jwxs.update();
                            }
                        });
                    }
                });
                thread.start();
            }
        });
        ib3 = (com.gc.materialdesign.views.ButtonRectangle) findViewById(R.id.jw_imagebutton3);
        ib3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                ProgressDialogUtil.showProgress(jw_main_page.this, "请稍后...");

                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        values = ConnInterface.getClickXSCJfromHTML(httpclient, values, user[0], "谢");
                        Log.i(TAG, "run: "+xscjSpinner);
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                show_jwxs_PopWindow(v,2);
                                initSpinnerData();
                                ProgressDialogUtil.dismiss();
                                tools_jwxs.update();
                            }
                        });
                    }
                });
                thread.start();
            }
        });
        ib4 = (com.gc.materialdesign.views.ButtonRectangle) findViewById(R.id.jw_imagebutton4);
        ib4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(jw_main_page.this, jwxsxx_page.class);
                Intent intent = new Intent(jw_main_page.this, MoreApplication.class);
                startActivity(intent);
            }
        });
    }

    // 拿到script里面的网址
    public String[] getdata(String data) {
        String[] arr = new String[2];
        String sub_attr_last = data.substring(data.indexOf("attr"));
        String getData = sub_attr_last.substring(sub_attr_last.indexOf("/"),
                sub_attr_last.indexOf(")"));
        String sub_fenhao_last = sub_attr_last.substring(sub_attr_last
                .indexOf(";"));
        arr[0] = getData;
        arr[1] = sub_fenhao_last;
        return arr;
    }

    private String xscjSpinner;
    private Spinner sp1_jwxs;
    private Spinner sp2_jwxs;

    // 初始化spinner1
    private void initSpinnerData() {
        ArrayList<String> loadSpinner1Data = LoadSpinnerData();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(jw_main_page.this,
                android.R.layout.simple_spinner_item, loadSpinner1Data);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1_jwxs.setAdapter(adapter1);
        ArrayList<String> Sp2_arr = new ArrayList<String>();
        Sp2_arr.add("1");
        Sp2_arr.add("2");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(jw_main_page.this,
                android.R.layout.simple_spinner_item, Sp2_arr);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2_jwxs.setAdapter(adapter2);
    }

    // 读取Spinner的数据
    private ArrayList<String> LoadSpinnerData() {
        ArrayList<String> Sp1_arr = new ArrayList<String>();
        Document doc = Jsoup.parse(values.get(2));
        Element elementById = doc.getElementById("ddlXN");
        Elements elementsByTag = elementById.getElementsByTag("option");
        for (int i = 1; i < elementsByTag.size(); i++) {
            String a = elementsByTag.get(i).text().toString();
            Sp1_arr.add(a);
        }
        return Sp1_arr;
    }

    // popwindow内容
    private PopupWindow tools_jwxs;

    private void show_jwxs_PopWindow(View v, final int tem) {
        View toolsLayout = LayoutInflater.from(jw_main_page.this).inflate(R.layout.jwxw_activity_popwindow, null);
        tools_jwxs = new PopupWindow(toolsLayout, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        sp1_jwxs = (Spinner) toolsLayout.findViewById(R.id.xsxw_sp1);
        sp2_jwxs = (Spinner) toolsLayout.findViewById(R.id.xsxw_sp2);
        Button btn_pop = (Button) toolsLayout.findViewById(R.id.jwxw_popwindow_button);
        btn_pop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent;
                if(tem == 2){
                    intent = new Intent(jw_main_page.this, jwxskb_page.class);
                }else{
                    intent = new Intent(jw_main_page.this, jwxscj_page.class);
                }
                if(sp1_jwxs.getSelectedItem().toString().equals("")||sp2_jwxs.getSelectedItem().toString().equals("")){
                    ToastUtil.show(getApplicationContext(),"请选择...");
                    return;
                }
                intent.putExtra("xueqi", sp1_jwxs.getSelectedItem()
                        .toString());
                intent.putExtra("xuenian", sp2_jwxs.getSelectedItem()
                        .toString());
                startActivity(intent);
                tools_jwxs.dismiss();
            }
        });
        tools_jwxs.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tools_jwxs.setOutsideTouchable(true);
        tools_jwxs.setFocusable(true);
        tools_jwxs.setTouchable(true);
        tools_jwxs.showAsDropDown(v, 0, 20);
        tools_jwxs.setAnimationStyle(R.style.mypopwindow_anim_style);
        backgroundAlpha(0.4f);
        tools_jwxs.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }


    // 背景透明度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getWindow().setAttributes(lp);
    }

    @Override
    protected void onPause() {
        isRun = false;
        // finish();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // super.onPause();
    }

    @Override
    protected void onStop() {
        isRun = false;
        super.onStop();
    }

    @Override
    protected void onResume() {
        isRun = true;
        super.onResume();
    }
}
