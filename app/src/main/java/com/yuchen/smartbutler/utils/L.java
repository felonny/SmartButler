package com.yuchen.smartbutler.utils;

import android.util.Log;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.utils
 * 文件名: L
 * Created by tangyuchen on 18/4/26.
 * 描述: TODO
 */

public class L {
    //开关
    public static final boolean DEBUG = true;
    //TAG
    public static final String TAG = "Smartbutler";

    //五个等级
    public static void d(String text){
        if(DEBUG){
            Log.d(TAG,text);
        }
    }

    public static void i(String text){
        if(DEBUG){
            Log.i(TAG,text);
        }
    }

    public static void w(String text){
        if(DEBUG){
            Log.w(TAG,text);
        }
    }

    public static void e(String text){
        if(DEBUG){
            Log.e(TAG,text);
        }
    }

}
