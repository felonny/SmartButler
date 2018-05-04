package com.yuchen.smartbutler.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static com.yuchen.smartbutler.R.id.action_mode_close_button;
import static com.yuchen.smartbutler.R.id.profile_image;

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

    public static void putImageToShare(ImageView imageView,Context mContext){
        //保存图片
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //1.将Bitmap压缩成字节数组
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,byStream);
        //2.利用Base64将我们的字节数组输出流换成String
        byte[] byteArray = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byteArray,Base64.DEFAULT));
        //3.将String保存到sharepreference
        ShareUtil.putString(mContext,"image_title",imgString);
    }

    //读取图片
    public static void getImageToShare(ImageView imageView,Context mContext){
        //1.拿到string
        String imageString = ShareUtil.getString(mContext,"image_title","");
        if(!imageString.equals("")){
            //2.利用Base64将String转化成我们的字节数组输出流
            byte [] byteArray = Base64.decode(imageString,Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
            //生成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            //设置到profile_image中
            imageView.setImageBitmap(bitmap);
        }

    }
}
