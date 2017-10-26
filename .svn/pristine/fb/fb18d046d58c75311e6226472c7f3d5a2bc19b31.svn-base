package com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.adapter.jw_main_adapter;
import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.bean.NewsItem;
import com.example.ronny_xie.gdcp.R;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.jsoup.nodes.Document;

import java.util.ArrayList;

/**
 * Created by WYF on 2017/7/17.
 */

public class NewsItemFragment extends Fragment implements ChangeNewsItemContent {

    private View view;
    private ListView listView;
    private int sign;
    private Document doc;
    private ArrayList<NewsItem> newsItems;
    private SpringView springView;
    private LoadGDCPData loadGDCPData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_news_item, container, false);
        loadGDCPData = new LoadGDCPData(getActivity());
        initView();
        onClickListener();
        new MyAsyncTask(getContext(), listView, this).execute(sign);
        return view;
    }

    @Override
    public void onChangeItemContent(ArrayList<NewsItem> nis) {
        this.newsItems = nis;
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listView.setAdapter(new jw_main_adapter(newsItems, getActivity()));
                }
            });
        }
    }

    @Override
    public void addSliderLayout() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listView.setAdapter(new jw_main_adapter(newsItems, getActivity()));
                }
            });
        }
    }

    private void initView() {
        springView = (SpringView) view.findViewById(R.id.spring_view);
        listView = (ListView) view.findViewById(R.id.jw_listView);
        newsItems = new ArrayList<>();
        springView.setType(SpringView.Type.FOLLOW);
        springView.setHeader(new DefaultHeader(getContext()));
        springView.setFooter(new DefaultFooter(getContext()));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new MyAsyncTask(getContext(), listView, NewsItemFragment.this).execute(sign);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                    }
                }, 1500);
            }

            @Override
            public void onLoadmore() {

            }
        });
    }

    private void onClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MywebView.class);
                intent.putExtra("url", newsItems.get(position - 1).getUrl());
                startActivity(intent);
            }
        });
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

}
