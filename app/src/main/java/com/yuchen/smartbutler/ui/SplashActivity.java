package com.yuchen.smartbutler.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.yuchen.smartbutler.MainActivity;
import com.yuchen.smartbutler.R;
import com.yuchen.smartbutler.utils.ShareUtil;
import com.yuchen.smartbutler.utils.StaticClass;
import com.yuchen.smartbutler.utils.UtilTools;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.ui
 * 文件名: SplashActivity
 * Created by tangyuchen on 18/4/26.
 * 描述: TODO
 */

public class SplashActivity extends AppCompatActivity {

    /**
     * 1.延时2000ms
     * 2.判断程序是否是第一次运行
     * 3.自定义字体
     * 4.Activity全屏主题
     * @param savedInstanceState
     */

    private TextView tv_splash;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是否是第一次运行
                    if(isFirst()){
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                    }else{
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    }
                    break;
            }
        }
    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {

        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);

        tv_splash = (TextView) findViewById(R.id.tv_splash);

        //设置字体
        UtilTools.setFont(this,tv_splash);
    }

    private boolean isFirst() {
       boolean isFirst =  ShareUtil.getBoolean(this,StaticClass.SHARE_IS_FIRST,true);
       if(isFirst){
           ShareUtil.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
           //是第一次运行
           return true;
       }else{
           return false;
       }
    }

    //禁止返回键

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
