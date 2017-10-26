package com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.tag;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.tag.adapter.AbsTipAdapter;
import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.tag.adapter.AddTipAdapter;
import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.tag.adapter.DragTipAdapter;
import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.tag.bean.Tip;
import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.tag.widget.DragDropGirdView;
import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.tag.widget.TipItemView;
import com.example.ronny_xie.gdcp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wenhuaijun on 2016/5/27 0027.
 */
public class EasyTipDragView extends RelativeLayout implements AbsTipAdapter.DragDropListener {
    private DragDropGirdView dragDropGirdView;
    private GridView addGridView;
    private ImageView closeImg;
    private TextView completeTv;
    private AddTipAdapter addTipAdapter;
    private DragTipAdapter dragTipAdapter;
    private OnDataChangeResultCallback dataResultCallback;
    private OnCompleteCallback completeCallback;
    private ArrayList<Tip> lists;
    private boolean isOpen = false;
    private TextView dragFinishTv;

    public EasyTipDragView(Context context) {
        super(context);
        initView();
    }

    public EasyTipDragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public EasyTipDragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EasyTipDragView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        if (isInEditMode()) {
            return;
        }
        close();
        dragTipAdapter = new DragTipAdapter(getContext(), this);
        addTipAdapter = new AddTipAdapter();
        //加载view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_easytagdrag, this);
        dragDropGirdView = (DragDropGirdView) view.findViewById(R.id.tagdrag_view);
        dragDropGirdView.getDragDropController().addOnDragDropListener(dragTipAdapter);

        dragDropGirdView.setDragShadowOverlay((ImageView) view.findViewById(R.id.tile_drag_shadow_overlay));
        dragDropGirdView.setAdapter(dragTipAdapter);
        dragTipAdapter.setItemSelectedListener(new TipItemView.OnSelectedListener() {
            @Override
            public void onTileSelected(Tip entity, int position, View view) {
                addTipAdapter.getData().add(dragTipAdapter.getData().get(position));
                addTipAdapter.refreshData();
                dragTipAdapter.getData().remove(position);
                dragTipAdapter.refreshData();
            }
        });
        addGridView = (GridView) view.findViewById(R.id.add_gridview);
        addGridView.setAdapter(addTipAdapter);
        addGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dragTipAdapter.getData().add(addTipAdapter.getData().get(position));
                dragTipAdapter.refreshData();
                addTipAdapter.getData().remove(position);
                addTipAdapter.refreshData();
            }
        });
    }

    @Override
    public DragDropGirdView getDragDropGirdView() {
        return dragDropGirdView;
    }

    @Override
    public void onDataSetChangedForResult(ArrayList<Tip> lists) {
        this.lists = lists;
        if (dataResultCallback != null) {
            dataResultCallback.onDataChangeResult(lists);
        }
    }

    public void setDragData(List<Tip> tips) {
        dragTipAdapter.setData(tips);
    }

    public void setAddData(List<Tip> tips) {
        lists = new ArrayList<>(tips);
        addTipAdapter.setData(tips);
    }

    public void setOnChangeListener(OnDataChangeResultCallback dataResultCallback) {
        this.dataResultCallback = dataResultCallback;
    }

    public void setOnCompleteListener(OnCompleteCallback callback) {
        this.completeCallback = callback;
    }

    public void setOnItemClickListener(TipItemView.OnSelectedListener selectedListener) {
        dragTipAdapter.setItemSelectedListener(selectedListener);
    }

    public void close() {
        setVisibility(View.GONE);
        isOpen = false;
    }

    public void open() {
        setVisibility(View.VISIBLE);
        isOpen = true;
    }

    //每次由于拖动排序,添加或者删除item时会回调
    public interface OnDataChangeResultCallback {
        void onDataChangeResult(ArrayList<Tip> tips);
    }

    //在最后点击"完成"关闭EasyTipDragView时回调
    public interface OnCompleteCallback {
        void onComplete(ArrayList<Tip> tips);
    }

    public boolean isOpen() {
        return isOpen;
    }

}
