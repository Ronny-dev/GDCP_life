package com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.tag.adapter;

import android.content.ClipData;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.tag.bean.Tip;
import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.tag.widget.DragDropGirdView;
import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.tag.widget.TipItemView;
import com.example.ronny_xie.gdcp.R;

import java.util.ArrayList;

/**
 * Created by Wenhuaijun on 2016/5/26 0026.
 */
public class DragTipAdapter extends AbsTipAdapter implements View.OnLongClickListener {

    private static final ClipData EMPTY_CLIP_DATA = ClipData.newPlainText("", "");
    private TipItemView.OnSelectedListener mListener;

    public DragTipAdapter(Context context, DragDropListener dragDropListener) {
        super(context, dragDropListener);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TipItemView view =null;
        if(convertView!=null&&convertView instanceof TipItemView){
            view =(TipItemView)convertView;
        }else{
            view = (TipItemView)View.inflate(mContext, R.layout.view_tag_item, null);
        }
        //设置点击监听
        view.setItemListener(position, mListener);
        //设置长按监听
        view.setOnLongClickListener(this);
        //绑定数据
        view.renderData(getItem(position));
        return view;
    }

    @Override
    protected Tip getDragEntity(View view) {
        return ((TipItemView)view).getDragEntity();
    }

    public void setItemSelectedListener(TipItemView.OnSelectedListener mListener){
        this.mListener =mListener;
    }

    @Override
    public boolean onLongClick(View v) {
        //开启编辑模式
        startEdittingStatus(v);
        return true;
    }

    public void refreshData(){
        notifyDataSetChanged();
        mDragDropListener.onDataSetChangedForResult(tips);
    }

    public ArrayList<Tip> getData(){
        return tips;
    }

    private  void startEdittingStatus(View v){
        v.startDrag(EMPTY_CLIP_DATA, new View.DragShadowBuilder(),
                DragDropGirdView.DRAG_FAVORITE_TILE, 0);
    }
}
