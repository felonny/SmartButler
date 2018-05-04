package com.yuchen.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuchen.smartbutler.R;
import com.yuchen.smartbutler.entity.VideoData;
import com.yuchen.smartbutler.utils.PicassonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.adapter
 * 文件名: VideoAdapter
 * Created by tangyuchen on 18/5/3.
 * 描述: TODO
 */

public class VideoAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater inflater;
    private boolean flag = false;

    private int width,height;
    private WindowManager wm;

    VideoData data = new VideoData();

    private List<VideoData> mList = new ArrayList<>();

    public VideoAdapter(Context mContext,List<VideoData> mList){
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

    public void updateView(List<VideoData> nowList)
    {
        this.mList = nowList;
        this.notifyDataSetChanged();//强制动态刷新数据进而调用getView方法
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.video_item,null);
            viewHolder.iv_videoImg = (ImageView) view.findViewById(R.id.iv_videoImg);
            viewHolder.tv_videoTitle = (TextView) view.findViewById(R.id.tv_videoTitle);
            viewHolder.tv_videoDesc = (TextView) view.findViewById(R.id.tv_videoDesc);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        data = mList.get(i);
        viewHolder.tv_videoTitle.setText(data.getTitle());
        viewHolder.tv_videoDesc.setText(data.getDesc());

        if(data.getImgUrl().equals("")){
            viewHolder.iv_videoImg.setVisibility(view.GONE);
            flag = true;
        }else{
            if(flag){
                viewHolder.iv_videoImg.setVisibility(view.VISIBLE);
                flag = false;
            }
            PicassonUtil.loadImageViewSize(mContext,data.getImgUrl(),width/3,150,viewHolder.iv_videoImg);

        }
        return view;
    }

    class ViewHolder{
        private ImageView iv_videoImg;
        private TextView tv_videoTitle;
        private TextView tv_videoDesc;
    }
}
