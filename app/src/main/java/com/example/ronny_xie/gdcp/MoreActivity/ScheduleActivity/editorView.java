package com.example.ronny_xie.gdcp.MoreActivity.ScheduleActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.MoreActivity.ScheduleActivity.db.DBService;
import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.util.SharePreferenceUtil;
import com.example.ronny_xie.gdcp.util.ToastUtil;
import com.gc.materialdesign.views.CheckBox;

import org.feezu.liuli.timeselector.TimeSelector;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by Ronny on 2017/5/2.
 */

public class editorView extends Activity {
    private RichEditor mEditor;
    private TextView mPreview;
    private LinearLayout linearLayout_menu1;
    private ImageButton fontButton;
    private ImageButton styleButton;
    private InputMethodManager imm;
    private ImageButton closeButton;
    private LinearLayout linearLayout_menu2;
    private ImageView bar_image_click;
    private TextView tv_title;
    private int INTENTJUMP = 0;
    private int item;
    private TimeSelector timeSelector;
    private String date;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_editor_view);
        initView();
        initSpinner();
        initEditorListener();//设置富文本监听
        initClickListener();
        initTimeClickManager();
        item = getIntent().getIntExtra("item", -1);
        if (item != -1) {
            INTENTJUMP = 1;
            Cursor cursor = new DBService(editorView.this).query(item);
            while (cursor.moveToNext()) {
                tv_title.setText(cursor.getString(0));
                String content = cursor.getString(1);
                if (!content.equals("null")) {
                    mEditor.setHtml(content);
                }
            }
        }
    }

    private void initSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.schedule_spinner);
        final SharedPreferences sp = SharePreferenceUtil.newSharePreference(editorView.this, "schedule_spinner");
        String data = sp.getString("spinner", "全部,临时,生活,工作,娱乐,新建分组");
        final List<String> sp_tem = Arrays.asList(data.split(","));
        final List arrayList = new ArrayList(sp_tem);
        final SpinnerAdapter adapter = new SpinnerAdapter(arrayList, editorView.this);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getCount() - 1) {

                    final View v = View.inflate(editorView.this, R.layout.schedule_spinner_more, null);
                    new AlertDialog.Builder(editorView.this).setTitle("分组").setIcon(
                            android.R.drawable.ic_dialog_info).setView(
                            v).setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText edt = (EditText) v.findViewById(R.id.schedule_spinner_edt);
                            String name = edt.getText().toString();
                            if (!TextUtils.isEmpty(name)) {
                                arrayList.add(parent.getCount() - 1, name);
                                String s = "";
                                for (int i = 0; i < arrayList.size() - 1; i++) {
                                    s += arrayList.get(i).toString() + ",";
                                }
                                s += arrayList.get(arrayList.size() - 1);
                                SharePreferenceUtil.saveString("spinner", s, sp);
                                ToastUtil.show(editorView.this, "添加成功");
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }).setNegativeButton("取消", null).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initTimeClickManager() {
        final TextView tv_time = (TextView) findViewById(R.id.schedule_time);
        tv_time.setText(format.format(new Date(System.currentTimeMillis())));
        LinearLayout schedule_timer = (LinearLayout) findViewById(R.id.schedule_timer);
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, 6);
        String preMonth = format.format(cal.getTime());
        String thisMonth = format.format(new Date(System.currentTimeMillis()));
        timeSelector = new TimeSelector(editorView.this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                com.gc.materialdesign.views.CheckBox checkBox = (CheckBox) findViewById(R.id.editor_time_checkbox);
                tv_time.setText(time);
                checkBox.setChecked(true);
            }
        }, thisMonth, preMonth);
        schedule_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSelector.show();
            }
        });
    }

    private void initEditorListener() {
        mEditor = (RichEditor) findViewById(R.id.editor);
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(22);
        mEditor.setEditorFontColor(Color.BLACK);
        mEditor.setPadding(10, 10, 10, 10);
        mEditor.setPlaceholder("Insert text here...");
        mPreview = (TextView) findViewById(R.id.preview);
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                mPreview.setText(text);
            }
        });
        findViewById(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mEditor.undo();
            }
        });

        findViewById(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mEditor.redo();
            }
        });

        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mEditor.setBold();
            }
        });

        findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mEditor.setItalic();
            }
        });

        findViewById(R.id.action_subscript).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mEditor.setSubscript();
            }
        });

        findViewById(R.id.action_superscript).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mEditor.setSuperscript();
            }
        });

        findViewById(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mEditor.setStrikeThrough();
            }
        });

        findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mEditor.setUnderline();
            }
        });

        findViewById(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mEditor.setHeading(1);
            }
        });

        findViewById(R.id.action_heading3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mEditor.setHeading(3);
            }
        });

        findViewById(R.id.action_heading6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mEditor.setHeading(6);
            }
        });
        findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mEditor.setIndent();
            }
        });

        findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mEditor.setOutdent();
            }
        });

        findViewById(R.id.action_align_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mEditor.setAlignLeft();
            }
        });

        findViewById(R.id.action_align_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mEditor.setAlignCenter();
            }
        });

        findViewById(R.id.action_align_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mEditor.setAlignRight();
            }
        });

        findViewById(R.id.action_insert_bullets).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mEditor.setBullets();
            }
        });

        findViewById(R.id.action_insert_numbers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mEditor.setNumbers();
            }
        });

        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
                        "dachshund");
            }
        });

        findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mEditor.insertLink("https://github.com/wasabeef", "wasabeef");
            }
        });
        findViewById(R.id.action_insert_checkbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mEditor.insertTodo();
            }
        });
    }

    private void hideAll() {
        hideKeyboard();
        linearLayout_menu1.setVisibility(View.GONE);
        linearLayout_menu2.setVisibility(View.GONE);
    }

    private void initClickListener() {
        fontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linearLayout_menu2.getVisibility() == View.VISIBLE) {
                    linearLayout_menu2.setVisibility(View.GONE);
                    if (linearLayout_menu1.getVisibility() == View.VISIBLE) {
                        hideKeyboard();
                        linearLayout_menu1.setVisibility(View.GONE);
                    } else {
                        hideKeyboard();
                        linearLayout_menu1.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (linearLayout_menu1.getVisibility() == View.VISIBLE) {
                        hideKeyboard();
                        linearLayout_menu1.setVisibility(View.GONE);
                    } else {
                        hideKeyboard();
                        linearLayout_menu1.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        styleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linearLayout_menu1.getVisibility() == View.VISIBLE) {
                    linearLayout_menu1.setVisibility(View.GONE);
                    if (linearLayout_menu2.getVisibility() == View.VISIBLE) {
                        hideKeyboard();
                        linearLayout_menu2.setVisibility(View.GONE);
                    } else {
                        hideKeyboard();
                        linearLayout_menu2.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (linearLayout_menu2.getVisibility() == View.VISIBLE) {
                        hideKeyboard();
                        linearLayout_menu2.setVisibility(View.GONE);
                    } else {
                        hideKeyboard();
                        linearLayout_menu2.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout_menu1.setVisibility(View.GONE);
                linearLayout_menu2.setVisibility(View.GONE);
            }
        });
        bar_image_click.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!tv_title.getText().equals("")) {
                    String title = tv_title.getText().toString();
                    String content = mEditor.getHtml();
                    date = format.format(new Date(System.currentTimeMillis()));
                    DBService db = new DBService(editorView.this);
                    if (INTENTJUMP == 0) {
                        db.insertRecord(title, content, date, 1);
                    } else if (INTENTJUMP == 1) {
                        db.updateRecord(item, title, content, date, null, false, false, 1);
                    }
                    ToastUtil.show(editorView.this, "插入成功");
                    schedule_fragment.schedule_adapter.notifyDataSetChanged();
                    finish();
                }
            }
        });
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.editor_schedule_title);
        bar_image_click = (ImageView) findViewById(R.id.right_menu1);
        bar_image_click.setBackgroundResource(R.drawable.chatto_voice_playing_f2);
        linearLayout_menu1 = (LinearLayout) findViewById(R.id.editor_menu1);
        linearLayout_menu2 = (LinearLayout) findViewById(R.id.editor_menu2);
        fontButton = (ImageButton) findViewById(R.id.editor_font_menu_bt);
        styleButton = (ImageButton) findViewById(R.id.editor_style_menu);
        closeButton = (ImageButton) findViewById(R.id.editor_close_menu);
    }

    public void hideKeyboard() {
        // 隐藏输入法
        imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        // 显示或者隐藏输入法
        imm.hideSoftInputFromWindow(mEditor.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
