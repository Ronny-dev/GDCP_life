/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.tag.widget;

import android.content.ClipData;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.tag.adapter.AbsTipAdapter;
import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.tag.bean.SimpleTitleTip;
import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.tag.bean.Tip;
import com.example.ronny_xie.gdcp.R;


/**
 * A TileView displays a picture and name
 */
public class TipItemView extends RelativeLayout {
    private final static String TAG = TipItemView.class.getSimpleName();
    private static final ClipData EMPTY_CLIP_DATA = ClipData.newPlainText("", "");
    protected OnSelectedListener mListener;
    private Tip mIDragEntity;
    private TextView title;
    private int position;

    public TipItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //回调点击item事件
                if (mListener != null) {
                    mListener.onTileSelected(mIDragEntity,position, TipItemView.this);
                }
            }
        });
        title =(TextView)findViewById(R.id.tagview_title);
    }

    public Tip getDragEntity() {
        return mIDragEntity;
    }

    public void renderData(Tip entity) {
        mIDragEntity = entity;

        if (entity != null && entity != AbsTipAdapter.BLANK_ENTRY) {

            if(entity instanceof SimpleTitleTip) {
                title.setText(((SimpleTitleTip) mIDragEntity).getTip());

            }
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.INVISIBLE);
        }
    }

    public void setItemListener(int position, OnSelectedListener listener) {
        mListener = listener;
        this.position =position;
    }

    public interface OnSelectedListener {
        /**
         * Notification that the tile was selected; no specific action is dictated.
         */
        void onTileSelected(Tip entity, int position, View view);

    }

}
