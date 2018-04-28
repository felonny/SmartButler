package com.yuchen.smartbutler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yuchen.smartbutler.R;
import com.yuchen.smartbutler.entity.MyUser;
import com.yuchen.smartbutler.ui.LoginActivity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 项目名: SmartButler
 * 包名:  com.yuchen.smartbutler.fragment
 * 文件名: ButlerFragment
 * Created by tangyuchen on 18/4/25.
 * 描述: 用户中心
 */

public class UserFragment extends Fragment implements View.OnClickListener {

    private Button btn_quit_user;
    private TextView edit_user;
    private Button btn_update_ok;

    private EditText et_username;
    private EditText et_usersex;
    private EditText et_userage;
    private EditText et_userdesc;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user,null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        btn_quit_user = (Button) view.findViewById(R.id.btn_quit);
        btn_quit_user.setOnClickListener(this);

        edit_user = (TextView) view.findViewById(R.id.edit_user);
        edit_user.setOnClickListener(this);

        et_username = (EditText) view.findViewById(R.id.et_username);
        et_usersex = (EditText) view.findViewById(R.id.et_usersex);
        et_userage = (EditText) view.findViewById(R.id.et_userage);
        et_userdesc = (EditText) view.findViewById(R.id.et_userdesc);

        btn_update_ok = (Button) view.findViewById(R.id.btn_update_ok);
        btn_update_ok.setOnClickListener(this);

        //默认是不可点击输入的
        setEnable(false);


        //设置具体的值
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        et_username.setText(userInfo.getUsername());
        et_userage.setText(String.valueOf(userInfo.getAge()));
        et_usersex.setText(userInfo.isSex()?"男":"女");
        et_userdesc.setText(userInfo.getDesc());
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_quit:
                //退出登陆
                MyUser.logOut();   //清除缓存用户对象
                BmobUser currentUser = MyUser.getCurrentUser(); // 现在的currentUser是null了
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.edit_user:
                setEnable(true);
                btn_update_ok.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_update_ok:
                //1.拿到输入框的值
                String username = et_username.getText().toString().trim();
                String userage = et_userage.getText().toString().trim();
                String usersex = et_usersex.getText().toString().trim();
                String userdesc = et_userdesc.getText().toString().trim();
                //判断是否为空
                if(!TextUtils.isEmpty(username)&!TextUtils.isEmpty(userage)&
                !TextUtils.isEmpty(usersex)){
                    //更新属性
                    MyUser user = new MyUser();
                    user.setUsername(username);
                    user.setAge(Integer.parseInt(userage));
                    if(usersex.equals("男")){
                        user.setSex(true);
                    }else if(usersex.equals("女")){
                        user.setSex(false);
                    }
                    //简介
                    if(!TextUtils.isEmpty(userdesc)){
                        user.setDesc(userdesc);
                    }else{
                        user.setDesc("这个人很懒什么都没有留下");
                    }
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                setEnable(false);
                                btn_update_ok.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //获取焦点
    private void setEnable(boolean is){
        et_username.setEnabled(is);
        et_usersex.setEnabled(is);
        et_userage.setEnabled(is);
        et_userdesc.setEnabled(is);
    }
}
