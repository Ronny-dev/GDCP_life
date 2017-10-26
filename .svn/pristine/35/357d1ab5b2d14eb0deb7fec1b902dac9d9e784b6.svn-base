package com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.page;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.percent.PercentFrameLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ronny_xie.gdcp.Fragment.jwFragment;
import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.util.ToastUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class jw_choice_page extends AppCompatActivity implements View.OnClickListener {

    private CardView cvNews;
    private CardView cvCJ;
    private CardView cvKc;
    private CardView cvMore;
    private PercentFrameLayout flChoice;
    private AnimatorSet animSet;
    private AnimationSet set;
    private Animation alphaAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jw_choice);
        initView();
    }

    private void initView() {

        findViewById(R.id.jw_new).setOnClickListener(this);
        findViewById(R.id.jw_xscj).setOnClickListener(this);
        findViewById(R.id.jw_xskc).setOnClickListener(this);
        findViewById(R.id.jw_more).setOnClickListener(this);
    }

  /*  public Animation rotateAnimation() {
        Animation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setFillAfter(true);
        return rotateAnimation;
    }

    public Animation alphaAnimation() {
        Animation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);
        return alphaAnimation;

    }

    public Animation scaleAnimation() {
        Animation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(2000);
        return scaleAnimation;
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.jw_new:
              /*  CardView[] cards = {cvNews, cvCJ, cvKc, cvMore};
                startAnimation(cards, jw_main_page.class);*/
              startActivity(new Intent(this,jw_main_page.class));
                break;
            case R.id.jw_xscj:
                CardView[] cards1 = {cvCJ, cvNews, cvKc, cvMore};
              //  startAnimation(cards1, jwxscj_page.class);
              //  startActivity(new Intent(this,));
                show_jwxs_PopWindow(view,1);
                initSpinnerData();
                break;
            case R.id.jw_xskc:
                CardView[] cards2 = {cvKc, cvNews, cvCJ, cvMore};
               // startAnimation(cards2, jwxskb_page.class);
                show_jwxs_PopWindow(view,2);
                initSpinnerData();
                break;
            case R.id.jw_more:
                Toast.makeText(this, "敬请期待", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private String xscjSpinner;
    private Spinner sp1_jwxs;
    private Spinner sp2_jwxs;
    private void initSpinnerData() {
      //  ArrayList<String> loadSpinner1Data = LoadSpinnerData();
        ArrayList<String> Sp1_arr = new ArrayList<String>();
        Sp1_arr.add("2013-2014");
        Sp1_arr.add("2014-2015");
        Sp1_arr.add("2015-2016");
        Sp1_arr.add("2016-2017");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(jw_choice_page.this,
                android.R.layout.simple_spinner_item, Sp1_arr);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1_jwxs.setAdapter(adapter1);
        ArrayList<String> Sp2_arr = new ArrayList<String>();
        Sp2_arr.add("1");
        Sp2_arr.add("2");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(jw_choice_page.this,
                android.R.layout.simple_spinner_item, Sp2_arr);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2_jwxs.setAdapter(adapter2);
    }
   /* private ArrayList<String> LoadSpinnerData() {
        ArrayList<String> Sp1_arr = new ArrayList<String>();
        Document doc = Jsoup.parse(jwFragment.values.get(2));
        Element elementById = doc.getElementById("ddlXN");
        Elements elementsByTag = elementById.getElementsByTag("option");
        for (int i = 1; i < elementsByTag.size(); i++) {
            String a = elementsByTag.get(i).text().toString();
            Sp1_arr.add(a);
        }
        return Sp1_arr;
    }*/
    private PopupWindow tools_jwxs;

    private void show_jwxs_PopWindow(View v, final int tem) {
        View toolsLayout = LayoutInflater.from(jw_choice_page.this).inflate(R.layout.popwindow_jwxw, null);
        tools_jwxs = new PopupWindow(toolsLayout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        sp1_jwxs = (Spinner) toolsLayout.findViewById(R.id.xsxw_sp1);
        sp2_jwxs = (Spinner) toolsLayout.findViewById(R.id.xsxw_sp2);
        Button btn_pop = (Button) toolsLayout.findViewById(R.id.jwxw_popwindow_button);
        btn_pop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent;
                if(tem == 2){
                    intent = new Intent(jw_choice_page.this, jwxskb_page.class);
                }else{
                    intent = new Intent(jw_choice_page.this, jwxscj_page.class);
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
        tools_jwxs.showAtLocation(v, Gravity.CENTER,0,0);
        tools_jwxs.setAnimationStyle(R.style.mypopwindow_anim_style);
        backgroundAlpha(0.4f);
        tools_jwxs.setOnDismissListener(new PopupWindow.OnDismissListener() {

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
   /* private void startAnimation(CardView[] cardViews, final Class clazz) {

        set = new AnimationSet(true);
        Animation rotateAnimation = rotateAnimation();
        Animation scaleAnimation = scaleAnimation();

        set.addAnimation(rotateAnimation);
        set.addAnimation(scaleAnimation);
        set.setDuration(2000);
        cardViews[0].startAnimation(set);
        set.setFillAfter(true);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                alphaAnimation = alphaAnimation();
                alphaAnimation.setDuration(1000);
                alphaAnimation.setFillAfter(true);
                flChoice.startAnimation(alphaAnimation);
                alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(jw_choice_page.this, clazz);
                        startActivityForResult(intent, 10001);


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }*/


}
