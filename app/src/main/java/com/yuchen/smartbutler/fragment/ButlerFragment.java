package com.yuchen.smartbutler.fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.yuchen.smartbutler.R;
import com.yuchen.smartbutler.adapter.CharListAdapter;
import com.yuchen.smartbutler.entity.CharListData;
import com.yuchen.smartbutler.utils.L;
import com.yuchen.smartbutler.utils.RobotUtil;

import java.util.ArrayList;
import java.util.List;



/**
 * 项目名: SmartButler
 * 包名:  com.yuchen.smartbutler.fragment
 * 文件名: ButlerFragment
 * Created by tangyuchen on 18/4/25.
 * 描述: TODO
 */

public class ButlerFragment extends Fragment implements View.OnClickListener {

    private ListView chatListView;
    private Button btn_send;

    private EditText et_send;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            String text = (String) msg.obj;
            addLeftItem(text);
            L.w(text);
        }
    };

    private List<CharListData> mList = new ArrayList<>();

    CharListAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_butler,null);

        //强制竖屏
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        findView(view);

        return view;
    }


    private void findView(View view) {
        chatListView = (ListView) view.findViewById(R.id.mChatListView);
        btn_send = (Button) view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);

        et_send = (EditText) view.findViewById(R.id.et_text);

        adapter = new CharListAdapter(getActivity(),mList);
        chatListView.setAdapter(adapter);
        addLeftItem("你好，我是小管家");



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_send:
                /**
                 * 1.获取输入框的内容
                 * 2.判断是否为空
                 * 3.判断长度不能大于30
                 * 4.发送给机器人请求返回内容
                 * 5.清空输入框
                 * 6.添加你输入的内容到rightItem
                 * 7.拿到机器人的返回值后添加到leftItem
                 */
                //1.获取输入框的内容
                String text = et_send.getText().toString();
                //2.判断是否为空
                if(!TextUtils.isEmpty(text)){
                    //3.判断长度不能大于30
                    if(text.length()>30){
                        Toast.makeText(getActivity(),"发送长度超出限制",Toast.LENGTH_SHORT).show();
                    }else{
                        //4.清空输入框
                        et_send.setText("");
                        //5.添加你输入的内容到rightItem
                        addRightItem(text);
                        //6.发送给机器人请求返回内容
                        RobotUtil robotUtil = new RobotUtil(getActivity(),text,mHandler);
                        robotUtil.start();
                    }

                }else{
                    Toast.makeText(getActivity(),"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //添加左边文本
    public void addLeftItem(String text){
        CharListData data = new CharListData();
        data.setType(CharListAdapter.VALUE_LEFT_TEXT);
        data.setText(text);
        mList.add(data);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        chatListView.setSelection(chatListView.getBottom());
    }

    //添加右边文本
    public void addRightItem(String text){

        CharListData data = new CharListData();
        data.setType(CharListAdapter.VALUE_RIGHT_TEXT);
        data.setText(text);
        mList.add(data);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        chatListView.setSelection(chatListView.getBottom());
    }


}
