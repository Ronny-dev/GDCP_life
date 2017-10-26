package com.example.ronny_xie.gdcp.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.styleInActivity.Photos;
import com.example.ronny_xie.gdcp.styleInActivity.styleActivity;
import com.example.ronny_xie.gdcp.util.menu_backgroundUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/17.
 */

public class SearchView extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private View searchView;
    private ListView mLv;
    private EditText mEtSearch;
    private View ivDelete;
    private SearchListener mListener;
    private String searchText;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> exList;
    private String text = "";
    private styleActivity activity;
    private boolean isClickItem =false;

    public SearchView(Context context) {
        super(context);
        mContext = context;
        activity = (styleActivity) mContext;
        initViews();
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        activity = (styleActivity) mContext;
        initViews();
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        activity = (styleActivity) mContext;
        initViews();
    }

    private void initViews() {
        searchView = View.inflate(mContext, R.layout.search_layout, this);
        mEtSearch = (EditText) findViewById(R.id.et_search);
        ivDelete = findViewById(R.id.delete);
        ivDelete.setOnClickListener(this);
        findViewById(R.id.seartch).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        mLv = (ListView) findViewById(R.id.tips);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                text = exList.get(i);
//                ToastUtil.show(mContext, text);
                if (mListener != null && !TextUtils.isEmpty(text)) {
                    isClickItem =true;
                    mEtSearch.setText(text);
                    mListener.loadData(text);

                }
                ;
            }
        });
        mLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDeleteDial(i);

                return true;
            }
        });
        //SearchView获取光标执行的回调
        mEtSearch.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    initData();
                    mLv.setVisibility(VISIBLE);
                } else {
                    mLv.setVisibility(GONE);
                }
            }
        });
        mEtSearch.addTextChangedListener(new EditTextChangerListener());
        //摁回车键执行的回调
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                //点击回车键触发搜索
                searchText = mEtSearch.getText().toString().trim();
                if (!TextUtils.isEmpty(searchText) && mListener != null) {
                    mEtSearch.setText(searchText);
                    menu_backgroundUtils.setStringToCache(mContext, searchText);
                    mListener.loadData(searchText);
                }
                return false;
            }
        });
    }

    private void showDeleteDial(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setItems(new String[]{"删除"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (exList!=null&&exList.size()>0){
                    menu_backgroundUtils.deleteString(mContext,exList.get(position));
                    exList.remove(position);
                    if (arrayAdapter!=null){
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void initData() {
        //从搜索历史中取数据
        exList = menu_backgroundUtils.getArrayListToCache(mContext);
        if (exList != null && exList.size() > 0) {
            arrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, exList);
            mLv.setAdapter(arrayAdapter);
        }
    }



    //给外部提供一个访问ListView的方法，已便对控件进行设置
    public ListView getListView() {
        return mLv;
    }

    public EditText getEditText() {
        return mEtSearch;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                mEtSearch.setText("");
                activity.currentState = styleActivity.viewState.init;
                activity.changeView();

                break;
            case R.id.delete:
                mEtSearch.setText("");
                initData();
                ivDelete.setVisibility(GONE);
                activity.currentState = styleActivity.viewState.search;
                activity.changeView();
                initData();
                break;
            case R.id.seartch:
                if (mEtSearch != null) {
                    activity.hideKeyBoard();
                    String trim = mEtSearch.getText().toString().trim();
                    if (!TextUtils.isEmpty(trim)) {
                        mEtSearch.setText(trim);
                        menu_backgroundUtils.setStringToCache(mContext, trim);
                        if (mListener != null) {
                            mListener.loadData(trim);
                        }
                    }
                }
                break;
        }
    }

    class EditTextChangerListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String str = charSequence.toString();
            if (!TextUtils.isEmpty(str)&&!isClickItem) {
                exList = menu_backgroundUtils.getExcludeStringArray(mContext, str);
                exList = filterTitleData(str, exList);

                if (exList != null && exList.size() > 0) {
                    mLv.setVisibility(VISIBLE);
                    mLv.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, exList));
                } else {
                    mLv.setVisibility(GONE);
                }
                activity.currentState = styleActivity.viewState.search;
                activity.changeView();
                ivDelete.setVisibility(VISIBLE);
            } else if(!TextUtils.isEmpty(str)&&isClickItem){
                isClickItem =false;
                mLv.setVisibility(GONE);
                ivDelete.setVisibility(VISIBLE);
            }else {
                ivDelete.setVisibility(GONE);
                initData();
                mLv.setVisibility(VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private ArrayList<String> filterTitleData(String str, ArrayList<String> exList) {
        if (exList == null) {
            exList = new ArrayList<>();
        }
        ArrayList<Photos> titleData = activity.getTitleData();
        if (titleData != null && titleData.size() > 0) {
            for (Photos photo : titleData) {
                if (photo.getTitle().contains(str)) {
                    exList.add(photo.getTitle());
                }
            }
            return exList;
        }
        return exList;
    }

    public void setOnSearchListener(SearchListener listener) {
        mListener = listener;
    }

    public interface SearchListener {
        void loadData(String text);

    }
}
