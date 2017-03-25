package com.example.ronny_xie.gdcp.mainActivity;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.loginActivity.MyApplication;
import com.example.ronny_xie.gdcp.loginActivity.WelcomePage;
import com.example.ronny_xie.gdcp.loginActivity.login;
import com.example.ronny_xie.gdcp.view.CicrcularImageView;
import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeChatTargetType;
import com.gotye.api.GotyeDelegate;
import com.gotye.api.GotyeGroup;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeMessageStatus;
import com.gotye.api.GotyeNotify;
import com.gotye.api.GotyeStatusCode;
import com.gotye.api.GotyeUser;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.example.ronny_xie.gdcp.schedule.fragment_schedule;
import com.example.ronny_xie.gdcp.util.BeepManager;
import com.example.ronny_xie.gdcp.util.BitmapUtil;
import com.example.ronny_xie.gdcp.util.ImageCache;
import com.example.ronny_xie.gdcp.util.ToastUtil;
import com.example.ronny_xie.gdcp.util.URIUtil;
import com.example.ronny_xie.gdcp.util.menu_backgroundUtils;
import com.example.ronny_xie.gdcp.Fragment.ContactsFragment;
import com.example.ronny_xie.gdcp.Fragment.MessageFragment;
import com.example.ronny_xie.gdcp.Fragment.SettingFragment;
import com.example.ronny_xie.gdcp.Fragment.fragment_weather;
import com.example.ronny_xie.gdcp.Fragment.fragment_competerRoom;
import com.example.ronny_xie.gdcp.Fragment.fragment_jw;
import com.example.ronny_xie.gdcp.Fragment.fragment_card;
import com.example.ronny_xie.gdcp.shop.fragment_shop;

public class MainActivity extends FragmentActivity {
    private MessageFragment messageFragment;
    private ContactsFragment contactsFragment;
    private SettingFragment settingFragment;
    private fragment_weather fragment_weather;
    private fragment_schedule fragment_schedule;
    private fragment_competerRoom fragment_competerRoom;
    private fragment_jw fragment_jw;
    private fragment_card fragment_card;
    private fragment_shop fragment_shop;
    private FragmentManager fragmentManager;
    public static SlidingMenu menu;
    private int currentPosition = 0;
    private BeepManager beep;
    private GotyeAPI api;
    private GotyeUser user;
    private TextView msgTip;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        api = GotyeAPI.getInstance();
        setContentView(R.layout.layout_main);
        api.addListener(mDelegate);
        beep = new BeepManager(MainActivity.this);
        beep.updatePrefs();
//        initMenu();
        initViews();
        fragmentManager = getSupportFragmentManager();// getFragmentManager();
        setTabSelection(0);
        clearNotify();
    }

    //操作menu菜单
    private void initMenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.LEFT);
        menu.setBehindOffsetRes(R.dimen.setMenu_MainWidth);
        menu.setShadowDrawable(R.drawable.ic_launcher);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.RIGHT);
        // 为侧滑菜单设置布局
        menu.setMenu(R.layout.menu_main);
        // 读取背景地址
        ImageView backgroundImage = (ImageView) findViewById(R.id.menu_background_image);
        menu_backgroundUtils.getMenuBackground(getApplicationContext(),backgroundImage);
        // ---------------------------------
        TextView menu_exit = (TextView) findViewById(R.id.menu_text_exit);
        TextView menu_setting = (TextView) findViewById(R.id.menu_text_set);
        menu_name = (TextView) findViewById(R.id.menu_text);
        menu_id = (TextView) findViewById(R.id.menu_id);
        menu_image = (CicrcularImageView) findViewById(R.id.menu_image);
        menu_signal = (TextView) findViewById(R.id.menu_text_sign);
        RelativeLayout menu_rl = (RelativeLayout) findViewById(R.id.menu_rl);
        menu_rl.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setTabSelection(0);
                menu.showContent();
            }
        });
        user = api.getLoginUser();
        menu.setOnOpenListener(new OnOpenListener() {

            @Override
            public void onOpen() {
                // 每次打开侧拉栏，初始化读取一次用户信息
                if (user != api.getLoginUser()) {
                    user = api.getLoginUser();
                    setUserInfo(user);
                    SharedPreferences share = getSharedPreferences("signal",
                            Activity.MODE_PRIVATE);
                    String sign = share.getString(user.getName().toString(),
                            "还没给我设置签名噢~");
                    menu_signal.setText(sign);
                }
            }
        });
        menu_exit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                int status = api.isOnline();
                int code = api.logout();
                int x = code;
                Log.d("", "code" + code + "" + x);
                if (code == GotyeStatusCode.CodeNotLoginYet) {
                    Intent intent1 = new Intent(getApplicationContext(),
                            login.class);
                    startActivity(intent1);
                    finish();
                }
            }
        });
        menu_setting.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setTabSelection(2);
                menu.showContent();
            }
        });
    }

    //操作menu的个人信息
    boolean hasRequest = false;

    private void setUserInfo(GotyeUser user) {
        if (user.getIcon() == null && !hasRequest) {
            hasRequest = true;
            api.getUserDetail(user, true);
        } else {
            Bitmap bm = BitmapUtil.getBitmap(user.getIcon().getPath());
            if (bm != null) {
                menu_image.setImageBitmap(bm);
                ImageCache.getInstance().put(user.getName(), bm);
            } else {
                api.downloadMedia(user.getIcon());
            }
        }
        menu_name.setText(user.getNickname());
        menu_id.setText(user.getName());
    }

    public void btn_1(View v) {
        transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (fragment_weather == null) {
            fragment_weather = new fragment_weather();
            transaction.add(R.id.content, fragment_weather);
        } else {
            transaction.show(fragment_weather);
        }
        transaction.commit();
        menu.showContent();
    }

    public void btn_2(View v) {
        System.out.println("点击了第二");
        transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (fragment_competerRoom == null) {
            fragment_competerRoom = new fragment_competerRoom();
            transaction.add(R.id.content, fragment_competerRoom);
        } else {
            transaction.show(fragment_competerRoom);
        }
        transaction.commit();
        menu.showContent();
    }

    public void btn_3(View v) {
        // intent启动
        // Todo 位置信息除去
    }

    public void btn_4(View v) {
        transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (fragment_shop == null) {
            fragment_shop = new fragment_shop();
            transaction.add(R.id.content, fragment_shop);
        } else {
            transaction.show(fragment_shop);
        }
        transaction.commit();
        menu.showContent();
    }

    public void btn_5(View v) {
        System.out.println("点击了");
        transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (fragment_jw == null) {
            fragment_jw = new fragment_jw();
            transaction.add(R.id.content, fragment_jw);
        } else {
            transaction.show(fragment_jw);
        }
        transaction.commit();
        menu.showContent();
    }

    public void btn_6(View v) {
        System.out.println("点击了");
        transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (fragment_card == null) {
            fragment_card = new fragment_card();
            transaction.add(R.id.content, fragment_card);
        } else {
            transaction.show(fragment_card);
        }
        transaction.commit();
        menu.showContent();
    }

    public void btn_7(View v) {
        transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (fragment_schedule == null) {
            fragment_schedule = new fragment_schedule();
            transaction.add(R.id.content, fragment_schedule);
        } else {
            transaction.show(fragment_schedule);
        }
        transaction.commit();
        menu.showContent();
    }

    private boolean returnNotify = false;

    @Override
    protected void onResume() {
        super.onResume();
        returnNotify = false;
        mainRefresh();
    }

    @Override
    protected void onPause() {
        returnNotify = true;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        api.removeListener(mDelegate);
        super.onDestroy();
    }

    private void initViews() {
        msgTip = (TextView) findViewById(R.id.new_msg_tip);
    }

    public void btn_friend(View v) {
        setTabSelection(1);
        menu.showContent();
    }

    public void btn_chat(View v) {
        setTabSelection(0);
        menu.showContent();
    }

    @SuppressLint("NewApi")
    private void setTabSelection(int index) {
        updateUnReadTip();
        currentPosition = index;
        transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                    transaction.add(R.id.content, messageFragment);
                } else {
                    transaction.show(messageFragment);
                }
                break;
            case 1:
                if (contactsFragment == null) {
                    contactsFragment = new ContactsFragment();
                    transaction.add(R.id.content, contactsFragment);
                } else {
                    transaction.show(contactsFragment);
                }
                break;
            case 2:
            default:
                if (settingFragment == null) {
                    settingFragment = new SettingFragment();
                    transaction.add(R.id.content, settingFragment);
                } else {
                    transaction.show(settingFragment);
                }
                break;
        }
        transaction.commit();
    }

    @SuppressLint("NewApi")
    private void hideFragments(FragmentTransaction transaction) {
        if (messageFragment != null) {
            transaction.hide(messageFragment);
        }
        if (contactsFragment != null) {
            transaction.hide(contactsFragment);
        }
        if (settingFragment != null) {
            transaction.hide(settingFragment);
        }
        if (fragment_weather != null) {
            transaction.hide(fragment_weather);
        }
        if (fragment_competerRoom != null) {
            transaction.hide(fragment_competerRoom);
        }
        if (fragment_shop != null) {
            transaction.hide(fragment_shop);
        }
        if (fragment_jw != null) {
            transaction.hide(fragment_jw);
        }
        if (fragment_card != null) {
            transaction.hide(fragment_card);
        }
        if (fragment_schedule != null) {
            transaction.hide(fragment_schedule);
        }
    }

    // 更新提醒
    public void updateUnReadTip() {
        //Todo
        int unreadCount = api.getTotalUnreadMessageCount();
        int unreadNotifyCount = api.getUnreadNotifyCount();
        unreadCount += unreadNotifyCount;
//        msgTip.setVisibility(View.VISIBLE);
        if (unreadCount > 0 && unreadCount < 100) {
//            msgTip.setText(String.valueOf(unreadCount));
        } else if (unreadCount >= 100) {
            msgTip.setText("99");
        } else {
//            msgTip.setVisibility(View.GONE);
        }

    }

    // 页面刷新
    private void mainRefresh() {
        updateUnReadTip();
        messageFragment.refresh();
        if (contactsFragment != null) {
            contactsFragment.refresh();
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            int tab = intent.getIntExtra("tab", -1);
            if (tab == 1) {
                contactsFragment.refresh();
            }
            int notify = intent.getIntExtra("notify", 0);
            if (notify == 1) {
                clearNotify();
            }

            int selection_index = intent.getIntExtra("selection_index", -1);
            if (selection_index == 1) {
                setTabSelection(1);
            }
        }

    }

    // 清理推送通知
    private void clearNotify() {
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
    }

    //图片返回值操作
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 选取图片的返回值
        if (resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
                    String path = URIUtil.uriToPath(this, selectedImage);
                    setPicture(path);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //设置图片
    private void setPicture(String path) {
        String smallImagePath = path;
        smallImagePath = BitmapUtil.check(smallImagePath);

        Bitmap smaillBit = BitmapUtil.getSmallBitmap(smallImagePath, 50, 50);
        String smallPath = BitmapUtil.saveBitmapFile(smaillBit);
        settingFragment.modifyUserIcon(smallPath);
    }

    //Gotye内部操作
    private GotyeDelegate mDelegate = new GotyeDelegate() {

        // 此处处理账号在另外设备登陆造成的被动下线
        @Override
        public void onLogout(int code) {
            // FragmentTransaction t=fragmentManager.beginTransaction();
            // t.remove(messageFragment);
            // t.commit();
            ImageCache.getInstance().clear();

            if (code == GotyeStatusCode.CodeForceLogout) {
                Toast.makeText(MainActivity.this, "您的账号在另外一台设备上登录了！",
                        Toast.LENGTH_SHORT).show();
                MyApplication.clearHasLogin(MainActivity.this);
                Intent intent = new Intent(getBaseContext(), WelcomePage.class);
                startActivity(intent);
                finish();
            } else if (code == GotyeStatusCode.CodeNetworkDisConnected) {
            } else {
                Toast.makeText(MainActivity.this, "退出登陆！", Toast.LENGTH_SHORT)
                        .show();
                MyApplication.clearHasLogin(MainActivity.this);
                Intent i = new Intent(MainActivity.this, WelcomePage.class);
                startActivity(i);
                finish();
            }

        }

        // 收到消息（此处只是单纯的更新聊天历史界面，不涉及聊天消息处理，当然你也可以处理，若你非要那样做）
        @Override
        public void onReceiveMessage(GotyeMessage message) {
            if (returnNotify) {
                return;
            }
            messageFragment.refresh();
            if (message.getStatus() == GotyeMessageStatus.GotyeMessageStatusUnread) {
                updateUnReadTip();

                if (!MyApplication.isNewMsgNotify()) {
                    return;
                }
                if (message.getReceiver().getType() == GotyeChatTargetType.GotyeChatTargetTypeGroup) {
                    if (MyApplication.isNotReceiveGroupMsg()) {
                        return;
                    }
                    if (MyApplication.isGroupDontdisturb(((GotyeGroup) message
                            .getReceiver()).getGroupID())) {
                        return;
                    }
                }
                beep.playBeepSoundAndVibrate();
            }
        }

        // 自己发送的信息统一在此处理
        @Override
        public void onSendMessage(int code, GotyeMessage message) {
            if (returnNotify) {
                return;
            }
            messageFragment.refresh();
        }

        // 收到群邀请信息
        @Override
        public void onReceiveNotify(GotyeNotify notify) {
            if (returnNotify) {
                return;
            }
            messageFragment.refresh();
            updateUnReadTip();
            if (!MyApplication.isNotReceiveGroupMsg()) {
                beep.playBeepSoundAndVibrate();
            }
        }

        @Override
        public void onRemoveFriend(int code, GotyeUser user) {
            if (returnNotify) {
                return;
            }
            api.deleteSession(user, false);
            messageFragment.refresh();
            contactsFragment.refresh();
        }

        @Override
        public void onAddFriend(int code, GotyeUser user) {
            if (returnNotify) {
                return;
            }
            if (currentPosition == 1) {
                contactsFragment.refresh();
            }
        }

        @Override
        public void onGetMessageList(int code, List<GotyeMessage> list) {
            // if(list != null && list.size() > 0){
            mainRefresh();
            // }
        }
    };
    private FragmentTransaction transaction;
    private TextView menu_name;
    private CicrcularImageView menu_image;
    private TextView menu_id;
    private TextView menu_signal;
    private boolean toclose = false;

    //返回键操作
    @Override
    public void onBackPressed() {
        if (menu.isMenuShowing()) {
            if (toclose) {
                super.onBackPressed();
            } else {
                ToastUtil.show(getApplicationContext(), "再次按下返回退出程序");
                toclose = true;
            }
        } else {
            ToastUtil.show(getApplicationContext(), "再次按下返回退出程序");
            menu.showMenu();
            toclose = true;
        }
    }
}
