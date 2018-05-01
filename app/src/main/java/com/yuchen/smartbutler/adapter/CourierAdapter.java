package com.yuchen.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuchen.smartbutler.R;
import com.yuchen.smartbutler.entity.CourierData;

import java.util.List;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.adapter
 * 文件名: CourierAdapter
 * Created by tangyuchen on 18/5/1.
 * 描述: 快递查询
 */

public class CourierAdapter extends BaseAdapter{

    private Context mContext;
    private List<CourierData> mList;

    private CourierData mCourierData;

    //布局加载器
    private LayoutInflater inflater;

    public CourierAdapter(Context mContext,List<CourierData> mList){
        this.mContext = mContext;
        this.mList = mList;
        //获取系统服务
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        //第一次加载
        if(viewHolder == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.layout_courier_item,null);
            viewHolder.tv_remark = (TextView) view.findViewById(R.id.tv_remark);
            viewHolder.tv_zone = (TextView) view.findViewById(R.id.tv_zone);
            viewHolder.tv_datatime = (TextView) view.findViewById(R.id.tv_datatime);
            //设置缓存
            view.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        //设置数据源
        mCourierData = mList.get(i);
        viewHolder.tv_remark.setText(mCourierData.getRemark());
        viewHolder.tv_zone.setText(mCourierData.getZone());
        viewHolder.tv_datatime.setText(mCourierData.getDatatime());

        return view;
    }

    class ViewHolder{
        private TextView tv_remark;
        private TextView tv_zone;
        private TextView tv_datatime;

    }
}
