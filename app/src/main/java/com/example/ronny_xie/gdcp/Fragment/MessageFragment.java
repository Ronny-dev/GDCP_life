package com.example.ronny_xie.gdcp.Fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
//import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.R;
import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeChatTarget;
import com.gotye.api.GotyeChatTargetType;
import com.gotye.api.GotyeCustomerService;
import com.gotye.api.GotyeDelegate;
import com.gotye.api.GotyeGroup;
import com.gotye.api.GotyeMedia;
import com.gotye.api.GotyeRoom;
import com.gotye.api.GotyeUser;
import com.example.ronny_xie.gdcp.util.ProgressDialogUtil;
import com.example.ronny_xie.gdcp.util.ToastUtil;
import com.example.ronny_xie.gdcp.view.SwipeMenu;
import com.example.ronny_xie.gdcp.view.SwipeMenuCreator;
import com.example.ronny_xie.gdcp.view.SwipeMenuItem;
import com.example.ronny_xie.gdcp.view.SwipeMenuListView;
import com.example.ronny_xie.gdcp.view.SwipeMenuListView.OnMenuItemClickListener;
import com.example.ronny_xie.gdcp.activity.ChatPage;
import com.example.ronny_xie.gdcp.activity.CreateGroupSelectUser;
import com.example.ronny_xie.gdcp.activity.NotifyListPage;
import com.example.ronny_xie.gdcp.activity.SearchPage;
import com.example.ronny_xie.gdcp.adapter.MessageListAdapter;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import static android.R.attr.action;


//此页面为回话历史页面，由客户端自己实现
@SuppressLint("NewApi")
public class MessageFragment extends Fragment implements OnClickListener {
    private SwipeMenuListView listView;
    private MessageListAdapter adapter;
    private static final String TAG = "MessageFragment";
    public static final String fixName = "通知列表";
    private GotyeAPI api = GotyeAPI.getInstance();
    private FloatingActionMenu actionMenu;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_message_page, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GotyeAPI.getInstance().addListener(mDelegate);
        initView();
        initFABButton();
    }

    private void initFABButton() {
        ImageView icon = new ImageView(getActivity());
        final FloatingActionButton actionButton = new FloatingActionButton
                .Builder(getActivity()).setContentView(icon).build();

        SubActionButton.Builder itemBUilder = new SubActionButton.Builder(getActivity());

        ImageView itemIcon1 = new ImageView(getActivity());
        itemIcon1.setImageDrawable(getResources().getDrawable(R.drawable.fab_addfriend));
        SubActionButton button1 = itemBUilder.setContentView(itemIcon1).build();

        ImageView itemIcon2 = new ImageView(getActivity());
        SubActionButton button2 = itemBUilder.setContentView(itemIcon2).build();
        itemIcon2.setImageDrawable(getResources().getDrawable(R.drawable.fab_groupchat));

        ImageView itemIcon3 = new ImageView(getActivity());
        SubActionButton button3 = itemBUilder.setContentView(itemIcon3).build();
        itemIcon3.setImageDrawable(getResources().getDrawable(R.drawable.fab_moresearch));

        ImageView itemIcon4 = new ImageView(getActivity());
        SubActionButton button4 = itemBUilder.setContentView(itemIcon4).build();
        itemIcon4.setImageDrawable(getResources().getDrawable(R.drawable.fab_searchfriend));

        actionMenu = new FloatingActionMenu
                .Builder(getActivity())
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .addSubActionView(button4)
                .attachTo(actionButton).build();
        final Thread[] thread = {null};
        final boolean[] isThread_run = {false};
        actionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!actionMenu.isOpen()) {
                    actionMenu.open(true);
                } else {
                    actionMenu.close(true);
                    if (!isThread_run[0] && thread[0] != null) {
                        thread[0].stop();
                        return;
                    }
                }
                isThread_run[0] = true;
                thread[0] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (actionMenu.isOpen()) {
                                        actionMenu.close(true);
                                        isThread_run[0] = false;
                                    }
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread[0].start();
            }
        });
    }

    private void initView() {
        TextView title = (TextView) getView().findViewById(R.id.title);
        title.setText("消息");
        ImageView iv_add = (ImageView) getView().findViewById(R.id.right_menu2);
        iv_add.setBackgroundResource(R.drawable.add);
        iv_add.setOnClickListener(this);
        ImageView iv = (ImageView) getView().findViewById(R.id.right_menu);
        iv.setOnClickListener(this);
        iv.setBackgroundResource(R.drawable.tools_message);
        listView = (SwipeMenuListView) getView().findViewById(R.id.listview);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // Create different menus depending on the view type
                switch (menu.getViewType()) {
                    case 0:
                        createMenu1(menu);
                        break;
                    case 1:
                        createMenu2(menu);
                        break;
                }
            }
        };
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public boolean onMenuItemClick(int position, SwipeMenu menu,
                                           int index) {
                GotyeChatTarget target = adapter.getItem(position);
                api.deleteSession(target, false);
                updateList();
                return false;
            }
        });
        int state = api.isOnline();
        if (state != 1) {
            setErrorTip(0);
        } else {
            setErrorTip(1);
        }
        updateList();
        setListener();
    }

    private void createMenu1(SwipeMenu menu) {

    }

    private void createMenu2(SwipeMenu menu) {
        SwipeMenuItem item2 = new SwipeMenuItem(getActivity());
        item2.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
        item2.setWidth(dp2px(70));
        item2.setIcon(R.drawable.ic_action_discard);
        menu.addMenuItem(item2);
    }

    private void setListener() {
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                GotyeChatTarget target = (GotyeChatTarget) adapter
                        .getItem(arg2);
                if (target.getName().equals(fixName)) {
                    Intent i = new Intent(getActivity(), NotifyListPage.class);
                    startActivity(i);
                } else {
                    GotyeAPI.getInstance().markMessagesAsRead(target, true);
                    if (target.getType() == GotyeChatTargetType.GotyeChatTargetTypeUser) {
                        Intent toChat = new Intent(getActivity(),
                                ChatPage.class);
                        toChat.putExtra("user", (GotyeUser) target);
                        startActivity(toChat);
                        // updateList();
                    } else if (target.getType() == GotyeChatTargetType.GotyeChatTargetTypeRoom) {
                        Intent toChat = new Intent(getActivity(),
                                ChatPage.class);
                        toChat.putExtra("room", (GotyeRoom) target);
                        startActivity(toChat);

                    } else if (target.getType() == GotyeChatTargetType.GotyeChatTargetTypeGroup) {
                        Intent toChat = new Intent(getActivity(),
                                ChatPage.class);
                        toChat.putExtra("group", (GotyeGroup) target);
                        startActivity(toChat);
                    } else if (target.getType() == GotyeChatTargetType.GotyeChatTargetTypeCustomerService) {
                        Intent toChat = new Intent(getActivity(),
                                ChatPage.class);
                        toChat.putExtra("cserver",
                                (GotyeCustomerService) target);
                        startActivity(toChat);
                    }
                    refresh();
                }
            }
        });
    }

    private void updateList() {
        List<GotyeChatTarget> sessions = api.getSessionList();
        Log.d("onSession", "List--sessions" + sessions);

        GotyeChatTarget target = new GotyeUser(fixName);

        if (sessions == null) {
            sessions = new ArrayList<GotyeChatTarget>();
            sessions.add(target);
        } else {
            sessions.add(0, target);
        }
        if (adapter == null) {
            adapter = new MessageListAdapter(MessageFragment.this, sessions);
            listView.setAdapter(adapter);
        } else {
            adapter.setData(sessions);
        }
    }

    public void refresh() {
        updateList();
    }

    @Override
    public void onDestroy() {
        GotyeAPI.getInstance().removeListener(mDelegate);
        super.onDestroy();

    }

    private void setErrorTip(int code) {
        // code=api.getOnLineState();
        if (code == 1) {
            getView().findViewById(R.id.error_tip).setVisibility(View.GONE);
        } else {
            getView().findViewById(R.id.error_tip).setVisibility(View.VISIBLE);
            if (code == -1) {
                getView().findViewById(R.id.loading)
                        .setVisibility(View.VISIBLE);
                ((TextView) getView().findViewById(R.id.showText))
                        .setText("连接中...");
                getView().findViewById(R.id.error_tip_icon).setVisibility(
                        View.GONE);
            } else {
                getView().findViewById(R.id.loading).setVisibility(View.GONE);
                ((TextView) getView().findViewById(R.id.showText))
                        .setText("未连接");
                getView().findViewById(R.id.error_tip_icon).setVisibility(
                        View.VISIBLE);
            }
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private GotyeDelegate mDelegate = new GotyeDelegate() {

        @Override
        public void onDownloadMedia(int code, GotyeMedia media) {
            // TODO Auto-generated method stub
            if (getActivity().isTaskRoot()) {
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onLogout(int code) {
            setErrorTip(0);
        }

        @Override
        public void onLogin(int code, GotyeUser currentLoginUser) {
            setErrorTip(1);
        }

        @Override
        public void onReconnecting(int code, GotyeUser currentLoginUser) {
            setErrorTip(-1);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_menu:
                showTools(v);
                break;
            case R.id.tools_add:
                addUser();
                break;
            case R.id.tools_add_single:
                addSingelUser();
                break;
            case R.id.tools_group_chat:
                if (tools.isShowing()) {
                    tools.dismiss();
                    Intent toCreateGroup = new Intent(getActivity(),
                            CreateGroupSelectUser.class);
                    startActivity(toCreateGroup);
                }
                break;
            case R.id.right_menu2:
//			final PopupWindow popwindow3;
//			final View toolsLayout = LayoutInflater.from(getActivity()).inflate(R.layout.main_addfriend_popwindows, null);
//			popwindow3 = new PopupWindow(toolsLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
//			popwindow3.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//			popwindow3.setOutsideTouchable(true);
//			popwindow3.setFocusable(true);
//			popwindow3.setTouchable(true);
//			WindowManager manager = getActivity().getWindowManager();
//			int width = manager.getDefaultDisplay().getWidth();
//			popwindow3.setWidth(width);
//			popwindow3.showAtLocation(v, Gravity.CENTER, 0, 0);
//			popwindow3.setAnimationStyle(R.style.mypopwindow_anim_style);
//			backgroundAlpha(0.6f);
//			popwindow3.update();
//			Button btnText = (Button) toolsLayout.findViewById(R.id.fragment_schedule_button_commit);
//			btnText.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//
//					backgroundAlpha(1f);
//					popwindow3.dismiss();
//
//				}
//			});
//			popwindow3.setOnDismissListener(new PopupWindow.OnDismissListener() {
//				@Override
//				public void onDismiss() {
//					backgroundAlpha(1f);
//				}
//			});
                //Todo 详情查看网页，使用shareButton


                AlertDialog.Builder builder = new Builder(getActivity());
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setTitle("请输入对方ID");
                //创建一个EditText对象设置为对话框中显示的View对象
                final View vv = View.inflate(getActivity(), R.layout.chat_stanger, null);
                builder.setView(vv);
                //用户选好要选的选项后，点击确定按钮
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText tv = (EditText) vv.findViewById(R.id.chat_stanger_edittext);
                        chat_stanger_ID = tv.getText().toString().trim();
                        Intent tochat = new Intent(getActivity(),
                                ChatPage.class);
                        tochat.putExtra("user", new GotyeUser(chat_stanger_ID));
                        startActivity(tochat);
                    }
                });
                builder.show();
                break;
            default:
                break;
        }
    }

    private PopupWindow tools;

    private void showTools(View v) {
        View toolsLayout = LayoutInflater.from(getActivity()).inflate(
                R.layout.layout_tools, null);
        toolsLayout.findViewById(R.id.tools_add).setOnClickListener(this);
        toolsLayout.findViewById(R.id.tools_add_single)
                .setOnClickListener(this);
        toolsLayout.findViewById(R.id.tools_group_chat)
                .setOnClickListener(this);
        tools = new PopupWindow(toolsLayout, LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, true);
        tools.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tools.setOutsideTouchable(false);
        tools.showAsDropDown(v, 0, 20);
        tools.setAnimationStyle(R.style.mypopwindow_anim_style);
        tools.update();
    }

    private void addUser() {
        if (tools.isShowing()) {
            tools.dismiss();
            Intent toSreach = new Intent(getActivity(), SearchPage.class);
            toSreach.putExtra("search_type", 0);
            startActivity(toSreach);
        }
    }

    private void addSingelUser() {
        if (tools.isShowing()) {
            tools.dismiss();

            final EditText input = new EditText(getActivity());

            new AlertDialog.Builder(getActivity())
                    .setTitle("添加好友")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setView(input)
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    String name = input.getText().toString();
                                    if (!TextUtils.isEmpty(name)) {
                                        if (name.equals(currentLoginName)) {
                                            ToastUtil.show(getActivity(),
                                                    "不能添加自己");
                                            return;
                                        }
                                        ProgressDialogUtil.showProgress(
                                                getActivity(), "正在添加好友...");
                                        api.reqAddFriend(new GotyeUser(name));
                                        showAddFriendTip = true;
                                    }
                                }
                            }).setNegativeButton("取消", null).show();

        }
    }

    public String currentLoginName;
    private boolean showAddFriendTip = false;
    private String chat_stanger_ID;

    // 背景透明度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

}
