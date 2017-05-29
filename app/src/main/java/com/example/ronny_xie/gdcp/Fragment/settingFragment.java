package com.example.ronny_xie.gdcp.Fragment;

import com.example.ronny_xie.gdcp.R;
import com.example.ronny_xie.gdcp.loginActivity.MyApplication;
import com.example.ronny_xie.gdcp.loginActivity.LoginPage;
import com.example.ronny_xie.gdcp.styleInActivity.styleActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
//import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.example.ronny_xie.gdcp.util.BitmapUtil;
import com.example.ronny_xie.gdcp.util.ImageCache;
import com.example.ronny_xie.gdcp.util.SharePreferenceUtil;
import com.example.ronny_xie.gdcp.util.ToastUtil;
import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeDelegate;
import com.gotye.api.GotyeMedia;
import com.gotye.api.GotyeStatusCode;
import com.gotye.api.GotyeUser;


@SuppressLint("NewApi")
public class settingFragment extends Fragment {
    private static final int REQUEST_PIC = 1;
    protected static final int CHANGE_MENU_BACKGROUND = 2;
    private GotyeUser user;
    private ImageView iconImageView;
    private EditText nickName;
    private EditText info;
    private GotyeAPI api;
    private EditText signal;
    private RelativeLayout menu_background_setting;
    private static final String TAG = "settingFragment";

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        api = GotyeAPI.getInstance();
        api.addListener(mdelegate);
        user = api.getLoginUser();
        api.getUserDetail(user, true);
        initView();
        changeMenuBackground();
        int state = api.isOnline();
        if (state != 1) {
            setErrorTip(0);
        } else {
            setErrorTip(1);
        }
        Log.i(TAG, "onActivityCreated: " + user.getNickname());
        Log.i(TAG, "onActivityCreated: info" + user.getInfo());
        Log.i(TAG, "onActivityCreated: name" + user.getName());
        Log.i(TAG, "onActivityCreated: id" + user.getId());
    }

    private void initView() {
        menu_background_setting = (RelativeLayout) getView().findViewById(R.id.menu_layout_setting);
        iconImageView = (ImageView) getView().findViewById(R.id.icon);
        nickName = (EditText) getView().findViewById(R.id.nick_name);
        info = (EditText) getView().findViewById(R.id.info_name);
        Button btn = (Button) getView().findViewById(R.id.logout_btn);
        signal = (EditText) getView().findViewById(R.id.signal_signal);
        SharedPreferences share = getActivity().getSharedPreferences(user.getName().toString(), Activity.MODE_PRIVATE);
        String sign = share.getString("sign", "还没给我设置签名噢~");
        signal.setText(sign);
        btn.setText("退出");

        nickName.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        nickName.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {

                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    String text = arg0.getText().toString();
                    if (!"".equals(text)) {
                        GotyeUser forModify = new GotyeUser(user.getName());
                        forModify.setNickname(text);
                        forModify.setInfo(info.getText().toString().trim());
                        forModify.setGender(user.getGender());
                        String headPath = null;
                        int code = api.reqModifyUserInfo(forModify, headPath);
                        Log.d("initText", "" + code);
                        ToastUtil.show(getActivity(), "设置成功");
                    }
                    return true;
                }
                return false;
            }
        });
        signal.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (signal.getText().toString().equals("主人还没给我设置个性签名噢~")) {
                    signal.setText("");
                }
            }
        });
        signal.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (signal.getText().toString().equals("")) {
                    signal.setText("还没给我设置签名噢~");
                    return false;
                } else {
                    SharedPreferences share = SharePreferenceUtil.newSharePreference(getActivity(), user.getName());
                    SharePreferenceUtil.saveString("sign", signal.getText().toString(), share);
                    ToastUtil.show(getActivity(), "设置成功");
                    return false;
                }
            }
        });
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                int status = api.isOnline();
                int code = api.logout();
                int x = code;
                Log.d("", "code" + code + "" + x);
                if (code == GotyeStatusCode.CodeNotLoginYet) {
                    Intent toLogin = new Intent(getActivity(), LoginPage.class);
                    getActivity().startActivity(toLogin);
                    getActivity().finish();
                }
            }
        });
        getView().findViewById(R.id.icon_layout).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        takePic();
                    }
                });

        iconImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                takePic();
            }
        });
        CheckBox receiveNewMsg = ((CheckBox) getView().findViewById(
                R.id.new_msg));
        receiveNewMsg.setChecked(MyApplication.isNewMsgNotify());
        receiveNewMsg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                // TODO Auto-generated method stub
                MyApplication.setNewMsgNotify(arg1, user.getName());
            }
        });
//		CheckBox noTipAllGroupMessage = ((CheckBox) getView().findViewById(
//				R.id.group_msg));
//		noTipAllGroupMessage.setChecked(MyApplication.isNotReceiveGroupMsg());
//		noTipAllGroupMessage
//				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//					@Override
//					public void onCheckedChanged(CompoundButton arg0,
//							boolean arg1) {
//						MyApplication.setNotReceiveGroupMsg(arg1,user.getName());
//					}
//				});

        SharedPreferences spf = getActivity().getSharedPreferences("fifter_cfg", Context.MODE_PRIVATE);
        boolean fifter = spf.getBoolean("fifter", false);
        CheckBox msgFifter = ((CheckBox) getView().findViewById(
                R.id.msg_filter));
        msgFifter.setChecked(fifter);
        msgFifter
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton arg0,
                                                 boolean arg1) {
                        SharedPreferences spf = getActivity().getSharedPreferences("fifter_cfg", Context.MODE_PRIVATE);
                        spf.edit().putBoolean("fifter", arg1).commit();
                    }
                });
        getView().findViewById(R.id.clear_cache).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        int code = api.clearCache();
                        Toast.makeText(getActivity(), "清理完成!",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        setUserInfo(user);
    }

    boolean hasRequest = false;

    private void setUserInfo(GotyeUser user) {
        System.out.println("This is setUserInfo");
        if (user.getIcon() == null && !hasRequest) {
            hasRequest = true;
            api.getUserDetail(user, true);
        } else {
            Bitmap bm = BitmapUtil.getBitmap(user.getIcon().getPath());
            if (bm != null) {
                iconImageView.setImageBitmap(bm);
                ImageCache.getInstance().put(user.getName(), bm);
            } else {
                api.downloadMedia(user.getIcon());
            }
        }
        nickName.setText(user.getNickname());
        info.setText(user.getName());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void takePic() {
//		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
//		intent.setType("image/*");
//		getActivity().startActivityForResult(intent, REQUEST_PIC);
        Intent intent;
        intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/jpeg");
        getActivity().startActivityForResult(intent, REQUEST_PIC);
    }

    public void hideKeyboard() {
        // 隐藏输入法
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getApplicationContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        // 显示或者隐藏输入法
        imm.hideSoftInputFromWindow(nickName.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onDestroy() {
//		api.removeListener(this);
        api.removeListener(mdelegate);
        super.onDestroy();
    }

    public void modifyUserIcon(String smallImagePath) {
        String name = nickName.getText().toString().trim();
        GotyeUser forModify = new GotyeUser(user.getName());
        forModify.setNickname(name);
        forModify.setInfo(user.getInfo());
        forModify.setGender(user.getGender());
        api.reqModifyUserInfo(forModify, smallImagePath);
    }

    private void setErrorTip(int code) {
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

    private GotyeDelegate mdelegate = new GotyeDelegate() {

        @Override
        public void onDownloadMedia(int code, GotyeMedia media) {
            System.out.println("onDownLoadMedia...................");
            if (media.getUrl() != null && media.getUrl().equals(user.getIcon().getUrl())) {
                Bitmap bm = BitmapUtil.getBitmap(media.getPath());
                if (bm != null) {
                    iconImageView.setImageBitmap(bm);
                }
            }
        }

        @Override
        public void onGetUserDetail(int code, GotyeUser user) {
            if (user != null && user.getName().equals(settingFragment.this.user.getName())) {
                setUserInfo(user);
            }
        }

        @Override
        public void onModifyUserInfo(int code, GotyeUser user) {
            Log.i(TAG, "onModifyUserInfo: " + code);
            if (code == 0) {
                setUserInfo(user);
                // ToastUtil.show(getActivity(), "修改成功!");
            } else {
                ToastUtil.show(getActivity(), "修改失败!");
            }
        }

        @Override
        public void onLogin(int code, GotyeUser currentLoginUser) {
            // TODO Auto-generated method stub
            setErrorTip(1);
        }

        @Override
        public void onLogout(int code) {
            if (code == 0) {
                return;
            }
            setErrorTip(0);
        }

        @Override
        public void onReconnecting(int code, GotyeUser currentLoginUser) {
            // TODO Auto-generated method stub
            setErrorTip(-1);
        }


    };

    private void changeMenuBackground() {
        menu_background_setting.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), styleActivity.class);
                startActivity(intent);
            }
        });
    }
}
