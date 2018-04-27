package com.yuchen.smartbutler.application;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;
import com.yuchen.smartbutler.utils.StaticClass;

import cn.bmob.v3.Bmob;

/**
 * 项目名: SmartButler
 * 包名:  com.yuchen.smartbutler.applicartion
 * 文件名: BaseApplication
 * Created by tangyuchen on 18/4/25.
 * 描述: TODO
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化Bugly,true指的是测试模式
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APPID, true);
        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APPID);
    }
}
