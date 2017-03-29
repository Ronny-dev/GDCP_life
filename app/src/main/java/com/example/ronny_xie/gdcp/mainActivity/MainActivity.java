package com.example.ronny_xie.gdcp.mainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Handler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.loginActivity.MyApplication;
import com.example.ronny_xie.gdcp.loginActivity.WelcomePage;
import com.example.ronny_xie.gdcp.loginActivity.login;
import com.example.ronny_xie.gdcp.util.SharePreferenceUtil;
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
    private int currentPosition = 0;
    private BeepManager beep;
    private GotyeAPI api;
    private GotyeUser user;
    private TextView msgTip;
    private static final String TAG = "MainActivity";
    private String userName;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        api = GotyeAPI.getInstance();
        setContentView(R.layout.layout_main);
        api.addListener(mDelegate);
        user = api.getLoginUser();
        api.getUserDetail(user, true);
        beep = new BeepManager(MainActivity.this);
        beep.updatePrefs();
//        initMenu();
        initUserInfo();
        initNav();//初始化侧拉
        Log.i(TAG, "initUserName: " + user.getName() + "+++++" + user.getNickname());
        initViews();//初始化界面
        fragmentManager = getSupportFragmentManager();// getFragmentManager();
        setTabSelection(0);
        clearNotify();
    }

    private void initUserInfo() {
        SharedPreferences userSharePreference = SharePreferenceUtil.newSharePreference(getApplicationContext(), "username");
        userName = SharePreferenceUtil.getString("userName", userSharePreference);
        GotyeUser forModify = new GotyeUser(user.getName());
        forModify.setNickname(userName);
        forModify.setInfo("");
        forModify.setGender(user.getGender());
        String headPath = null;
        int code = api.reqModifyUserInfo(forModify, headPath);
        Log.i(TAG, "initUserInfo: "+userName);
        Log.i(TAG, "initUserInfo: " + user.getNickname());
        Log.i(TAG, "initUserInfo: " + user.getName());
        Log.d("initText", "" + code);
    }

    private void initNav() {
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawerlayout);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nv_main_navigation);
        //获取头部布局
        View headerView = navigationView.getHeaderView(0);
        ImageView nav_header_image = (ImageView) headerView.findViewById(R.id.nav_header_image);
        TextView nav_header_name = (TextView) headerView.findViewById(R.id.nav_header_name);
        TextView nav_header_sign = (TextView) headerView.findViewById(R.id.nav_header_sign);
        FrameLayout frameLayout = (FrameLayout) headerView.findViewById(R.id.nav_header_framelayout);
        ImageView nav_header_leave = (ImageView) headerView.findViewById(R.id.nav_leave_image);
        //点击nav的header部分跳转到设置
        frameLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabSelection(3);
                drawerLayout.closeDrawer(navigationView);
            }
        });
        //点击nav上部分退出按钮
        nav_header_leave.setOnClickListener(new OnClickListener() {
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
        if (user != api.getLoginUser()) {
            user = api.getLoginUser();
            setUserInfo(user, nav_header_name, nav_header_image);
            SharedPreferences share = getSharedPreferences("signal",
                    Activity.MODE_PRIVATE);
            String sign = share.getString(user.getName().toString(),
                    "还没给我设置签名噢~");
            nav_header_sign.setText(sign);
        }

        navigationView.setItemIconTintList(null);//设置图标颜为默认
        navigationView.getMenu().getItem(0).setChecked(true);//设置默认选中为第一个
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setCheckable(true);
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.nav_message:
                        nav_select(0);
                        break;
                    case R.id.nav_contacts:
                        nav_select(1);
                        break;
                    case R.id.nav_schedule:
                        nav_select(3);
                        break;
                    case R.id.nav_weather:
                        nav_select(4);
                        break;
                    case R.id.nav_computerroom:
                        nav_select(5);
                        break;
                    case R.id.nav_shop:
                        nav_select(6);
                        break;
                    case R.id.nav_jw2012:
                        nav_select(7);
                        break;
                    case R.id.nav_card:
                        nav_select(8);
                        break;
                }
                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });
    }


    boolean hasRequest = false;

    private void setUserInfo(GotyeUser user, TextView name, ImageView image) {
        if (user.getIcon() == null && !hasRequest) {
            hasRequest = true;
            api.getUserDetail(user, true);
        } else {
            Bitmap bm = BitmapUtil.getBitmap(user.getIcon().getPath());
            if (bm != null) {
                image.setImageBitmap(bm);
                ImageCache.getInstance().put(user.getName(), bm);
            } else {
                api.downloadMedia(user.getIcon());
            }
        }
        if (userName != null) {
            name.setText(userName);
        } else {
            name.setText(user.getName());
            ToastUtil.show(this, "获取用户名称失败");
        }
//        id.setText(user.getName());
    }

    Fragment fragment_list[] = {messageFragment, contactsFragment, settingFragment, fragment_schedule, fragment_weather,
            fragment_competerRoom, fragment_shop, fragment_jw, fragment_card};
    Fragment lastFragment;

    public void nav_select(int i) {
        transaction = fragmentManager.beginTransaction();
        if (fragment_list[i] == null) {
            if (i == 0) {
                fragment_list[i] = new MessageFragment();
            } else if (i == 1) {
                fragment_list[i] = new ContactsFragment();
            } else if (i == 2) {
                fragment_list[i] = new fragment_schedule();
            } else if (i == 3) {
                fragment_list[i] = new SettingFragment();
            } else if (i == 4) {
                fragment_list[i] = new fragment_weather();
            } else if (i == 5) {
                fragment_list[i] = new fragment_competerRoom();
            } else if (i == 6) {
                fragment_list[i] = new fragment_shop();
            } else if (i == 7) {
                fragment_list[i] = new fragment_jw();
            } else if (i == 8) {
                fragment_list[i] = new fragment_card();
            }
            transaction.add(R.id.content, fragment_list[i]);
        } else {
            transaction.show(fragment_list[i]);
        }
        hideFragments(transaction, i);
        transaction.commit();
        lastFragment = fragment_list[i];
    }

    @SuppressLint("NewApi")
    private void hideFragments(FragmentTransaction transaction, int i) {
        if (lastFragment != null) {
            if (lastFragment != fragment_list[i]) {
                transaction.hide(lastFragment);
            }
        }
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


    @SuppressLint("NewApi")
    private void setTabSelection(int index) {
        updateUnReadTip();
        currentPosition = index;
        transaction = fragmentManager.beginTransaction();
        hideFragments(transaction, index);
        switch (index) {
            case 0:
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                    transaction.add(R.id.content, messageFragment);
                    lastFragment = messageFragment;
                } else {
                    transaction.show(messageFragment);
                }
                break;
            case 1:
                if (contactsFragment == null) {
                    contactsFragment = new ContactsFragment();
                    transaction.add(R.id.content, contactsFragment);
                    lastFragment = messageFragment;
                } else {
                    transaction.show(contactsFragment);
                }
                break;
            case 2:
            default:
                if (settingFragment == null) {
                    settingFragment = new SettingFragment();
                    transaction.add(R.id.content, settingFragment);
                    lastFragment = messageFragment;
                } else {
                    transaction.show(settingFragment);
                }
                break;
        }
        transaction.commit();
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
        Log.i(TAG, "onActivityResult: " + requestCode + "aaa" + resultCode);
        // 选取图片的返回值
        if (requestCode == 1) {
            Log.i(TAG, "setImageToHeadView: 222222222");
            if (data != null) {
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
//                    String path = URIUtil.uriToPath(this, selectedImage);
//                    setPicture(path);
                    cropRawPhoto(data.getData());//直接裁剪图片
                }
            }
        }
        if (requestCode == 10086) {
            if (data != null) {
                setImageToHeadView(data);
//                String path = URIUtil.uriToPath(getApplicationContext(),data.getData());
                setPicture(getApplication().getFilesDir() + "/Ask/okkk.jpg");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        //把裁剪的数据填入里面

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 10086);
    }

    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            File nf = new File(getApplication().getFilesDir() + "/Ask");
            nf.mkdir();
            File f = new File(getApplication().getFilesDir() + "/Ask", "okkk.jpg");
            Log.i(TAG, "setImageToHeadView: 11111111" + f.getAbsolutePath().toString());
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(f);
                photo.compress(Bitmap.CompressFormat.JPEG, 90, out);
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
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
        @Override
        public void onModifyUserInfo(int code, GotyeUser user) {
            Log.i(TAG, "onModifyUserInfo: " + code);
            if (code == 0) {
                Log.i(TAG, "initUserInfo2: "+userName);
                Log.i(TAG, "initUserInfo2: " + user.getNickname());
                Log.i(TAG, "initUserInfo2: " + user.getName());
            } else {

            }
        }

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
}
