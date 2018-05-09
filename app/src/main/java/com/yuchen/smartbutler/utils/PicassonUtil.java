package com.yuchen.smartbutler.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.yuchen.smartbutler.R;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.utils
 * 文件名: PicassonUtil
 * Created by tangyuchen on 18/5/2.
 * 描述: Picasso
 */

public class PicassonUtil {

    //默认加载图片
    public static void loadImaheView(Context mContext, String url,ImageView imageView){
        Picasso.with(mContext).load(url).into(imageView);
    }

    //默认加载图片指定大小
    public static void loadImageViewSize(Context mContext, String url,int width,int height,ImageView imageView){
        Picasso.with(mContext)
                .load(url)
                .resize(width, height)
                .centerCrop()
                .into(imageView);

    }
    //加载默认图片指定大小
    public static void loadImageViewHolderSize(Context mContext, String url,int width,int height,int loadImg,int errorImg,ImageView imageView){
        Picasso.with(mContext)
                .load(url)
                .resize(width,height)
                .centerCrop()
                .placeholder(loadImg)
                .error(errorImg)
                .into(imageView);
    }
    //加载有默认图片
    public static void loadImagViewHolder(Context mContext, String url,int loadImg,int errorImg,ImageView imageView){
        Picasso.with(mContext)
                .load(url)
                .placeholder(loadImg)
                .error(errorImg)
                .into(imageView);
    }
    //裁剪图片
    public static void loadImageViewCrop(Context mContext, String url,ImageView imageView){
        Picasso.with(mContext).load(url).transform(new CropSquareTransformation()).into(imageView);
    }

    //按比例裁剪图片，矩形
    public static class CropSquareTransformation implements Transformation {
        @Override public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override public String key() {
            return "yuchen"; }
    }
}
