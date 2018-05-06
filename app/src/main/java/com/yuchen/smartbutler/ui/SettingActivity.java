package com.yuchen.smartbutler.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.yuchen.smartbutler.R;
import com.yuchen.smartbutler.service.SmsService;
import com.yuchen.smartbutler.utils.L;
import com.yuchen.smartbutler.utils.ShareUtil;
import com.yuchen.smartbutler.utils.StaticClass;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名: SmartButler
 * 包名:  com.yuchen.smartbutler.ui
 * 文件名: SettingActivity
 * Created by tangyuchen on 18/4/25.
 * 描述: 设置
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    //语音播报
    private Switch sw_speak;
    private Switch sw_message;

    private LinearLayout ll_update;
    private TextView tv_version;

    private String versionName;
    private int versionCode;

    private String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        sw_speak = (Switch) findViewById(R.id.sw_speak);
        sw_speak.setOnClickListener(this);
        boolean isSpeak = ShareUtil.getBoolean(this,"isSpeak",false);
        sw_speak.setChecked(isSpeak);

        sw_message = (Switch) findViewById(R.id.sw_message);
        sw_message.setOnClickListener(this);
        boolean isMessage = ShareUtil.getBoolean(this,"isMessage",false);
        sw_speak.setChecked(isMessage);

        ll_update = (LinearLayout) findViewById(R.id.ll_update);
        ll_update.setOnClickListener(this);

        tv_version = (TextView) findViewById(R.id.tv_version);

        try {
            getVersionNameCode();
            tv_version.setText("检测版本："+versionName);
        } catch (PackageManager.NameNotFoundException e) {
            tv_version.setText("检测版本");
        }

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.sw_speak:
                //切换相反
                sw_speak.setSelected(!sw_speak.isSelected());
                //保存状态
                ShareUtil.putBoolean(this,"isSpeak",sw_speak.isChecked());
                break;
            case R.id.sw_message:
                sw_message.setSelected(!sw_message.isSelected());
                ShareUtil.putBoolean(this,"isMessage",sw_message.isChecked());

                if(sw_message.isChecked()){
                   startService(new Intent(this,SmsService.class));
                }else{
                    stopService(new Intent(this,SmsService.class));
                }
                break;
            case R.id.ll_update:
                /**
                 * 步骤
                 * 1.请求服务器的配置文件
                 * 2.比较
                 * 3.dialog提示
                 * 4.跳转更新界面并把url传进去
                 */
                RxVolley.get(StaticClass.CONFIG_URL, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        L.i("json:"+t);
                        parsingJson(t);
                    }
                });
                break;
        }
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            int code = jsonObject.getInt("versionCode");
            url = jsonObject.getString("url");
            L.i("code:"+code);
            if(code>versionCode){
                String content = jsonObject.getString("content");
                showUpdateDialog(content);
            }else{
                Toast.makeText(this,"当前是最新版本",Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //弹出升级提示
    private void showUpdateDialog(String content) {
        new AlertDialog.Builder(this)
                .setTitle("有新版本了！")
                .setMessage(content)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(SettingActivity.this,UpdateActivity.class);
                        intent.putExtra("url",url);
                        startActivity(intent);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //可以什么也不做，会执行dismiss
            }
        }).show();
    }

    //获取版本号/Code
    private void getVersionNameCode() throws PackageManager.NameNotFoundException{
        PackageManager pm = getPackageManager();
        PackageInfo info = pm.getPackageInfo(getPackageName(),0);
        versionName = info.versionName;
        versionCode = info.versionCode;
    }
}
