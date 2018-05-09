package com.yuchen.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuchen.smartbutler.R;
import com.yuchen.smartbutler.entity.CharListData;

import java.util.List;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.adapter
 * 文件名: CharListAdapter
 * Created by tangyuchen on 18/5/1.
 * 描述: 对话Adapter
 */

public class CharListAdapter extends BaseAdapter{

    public static final int VALUE_LEFT_TEXT = 1;

    public static final int VALUE_RIGHT_TEXT = 2;


    private Context mContext;
    private List<CharListData> mList;
    private LayoutInflater inflater;
    private CharListData data;

    public CharListAdapter(Context mContext, List<CharListData> mList){

        this.mContext = mContext;
        this.mList = mList;
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

        ViewHolderLeft viewHolderLeft = null;
        ViewHolderRight viewHolderRight = null;
        int type = getItemViewType(i);
        if(view == null){
            switch (type){
                case VALUE_LEFT_TEXT:
                    viewHolderLeft = new ViewHolderLeft();
                    view = inflater.inflate(R.layout.left_item,null);
                    viewHolderLeft.tv_left_text = (TextView) view.findViewById(R.id.tv_left_text);
                    view.setTag(viewHolderLeft);
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRight = new ViewHolderRight();
                    view = inflater.inflate(R.layout.right_item,null);
                    viewHolderRight.tv_right_text = (TextView) view.findViewById(R.id.tv_right_text);
                    view.setTag(viewHolderRight);
                    break;
            }
        }else{
            switch(type){
                case VALUE_LEFT_TEXT:
                    viewHolderLeft = (ViewHolderLeft) view.getTag();
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRight = (ViewHolderRight) view.getTag();
                    break;
            }

        }

        CharListData data = mList.get(i);
        switch (type){
            case VALUE_LEFT_TEXT:
                viewHolderLeft.tv_left_text.setText(data.getText());
                break;
            case VALUE_RIGHT_TEXT:
                viewHolderRight.tv_right_text.setText(data.getText());
                break;
        }

        return view;
    }


    //根据数据源的position来返回要显示的item
    @Override
    public int getItemViewType(int position) {
        CharListData data = mList.get(position);
        int type = data.getType();
        return type;
    }

    @Override
    public int getViewTypeCount() {
        return 3;//mList.size()+1
    }

    class ViewHolderLeft{
        private TextView tv_left_text;
    }

    class ViewHolderRight{
        private TextView tv_right_text;
    }
}
