package com.yuchen.smartbutler.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yuchen.smartbutler.R;
import com.yuchen.smartbutler.entity.WeChatData;
import com.yuchen.smartbutler.utils.L;
import com.yuchen.smartbutler.utils.PicassonUtil;

import java.util.List;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.adapter
 * 文件名: WeChatAdapter
 * Created by tangyuchen on 18/5/2.
 * 描述: 微信精选
 */

public class WeChatAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<WeChatData> mList;
    private WeChatData data;

    private boolean flag = false;

    private int width,height;
    private WindowManager wm;

    public WeChatAdapter(Context mContext,List<WeChatData> mList){
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if(view == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.wechat_item,null);
            viewHolder.iv_img = (ImageView) view.findViewById(R.id.iv_img);
            viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_title);
            viewHolder.tv_source = (TextView) view.findViewById(R.id.tv_source);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        data = mList.get(i);
        viewHolder.tv_title.setText(data.getTitle());
        viewHolder.tv_source.setText(data.getSource());


        if(data.getImgUrl().equals("")){
            viewHolder.iv_img.setVisibility(view.GONE);
            flag = true;
        }else{
            if(flag){
                viewHolder.iv_img.setVisibility(view.VISIBLE);
                flag = false;
            }
            PicassonUtil.loadImageViewSize(mContext,data.getImgUrl(),width/3,150,viewHolder.iv_img);

        }
        return view;
    }

    class ViewHolder{
        private ImageView iv_img;
        private TextView tv_title;
        private TextView tv_source;
    }
}
