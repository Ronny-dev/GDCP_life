package com.example.ronny_xie.gdcp.styleInActivity;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.util.ToastUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/18.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Photos> photoses;
    private MyItemClickListener mItemClickListener;

    public RecycleViewAdapter(final Context context, final ArrayList<Photos> photoses) {
        this.context = context;
        this.photoses = photoses;
//        setOnItemClickListener(new MyItemClickListener() {
//            @Override
//            public void onItemClickListener(View view, int postion) {
//                ToastUtil.show(context,photoses.get(postion).getUrl());
//            }
//        });
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(View.inflate(context, R.layout.style_recycleview_item, null),mItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.cardView.setRadius(8);
        holder.tvTitle.setText(photoses.get(position).getTitle());
        Glide.with(context).load(photoses.get(position).getUrl()).into(holder.mIv);
    }
    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }
    @Override
    public int getItemCount() {
        return photoses == null ? 0 : photoses.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mIv;
        private TextView tvTitle;
        private final CardView cardView;
        private MyItemClickListener listener;

        public MyViewHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            mIv = (ImageView) itemView.findViewById(R.id.iv_rv);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            cardView = (CardView) itemView.findViewById(R.id.card);
            itemView.setOnClickListener(this);
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemClickListener(view, getPosition());
            }
        }
    }

    interface MyItemClickListener {
        void onItemClickListener(View view, int postion);
    }
}
