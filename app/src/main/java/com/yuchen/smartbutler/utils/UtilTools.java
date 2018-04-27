package com.yuchen.smartbutler.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * 项目名: SmartButler
 * 包名:  com.yuchen.smartbutler.utils
 * 文件名: UtilTools
 * Created by tangyuchen on 18/4/25.
 * 描述: TODO
 */

public class UtilTools {

    //设置字体
    public static void setFont(Context mContext, TextView textView){
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(),"fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }
}
