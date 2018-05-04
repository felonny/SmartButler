package com.yuchen.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Switch;

import com.yuchen.smartbutler.R;
import com.yuchen.smartbutler.service.SmsService;
import com.yuchen.smartbutler.utils.ShareUtil;

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
        }
    }
}
