package com.yuchen.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yuchen.smartbutler.R;
import com.yuchen.smartbutler.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.ui
 * 文件名: RegisteredActivity
 * Created by tangyuchen on 18/4/27.
 * 描述: 注册界面
 */

public class RegisteredActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_user;
    private EditText et_age;
    private EditText et_desc;
    private RadioGroup mRadioGroup;
    private EditText et_pass;
    private EditText et_repass;
    private EditText et_email;
    private Button btn_register;

    private boolean grander = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);

        initView();
    }

    private void initView() {
        et_user = (EditText) findViewById(R.id.et_user);
        et_age = (EditText) findViewById(R.id.et_age);
        et_desc = (EditText) findViewById(R.id.et_desc);
        et_email = (EditText) findViewById(R.id.et_email);
        et_pass = (EditText) findViewById(R.id.et_password);
        et_repass = (EditText) findViewById(R.id.et_repwd);
        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
        btn_register = (Button) findViewById(R.id.btn_reg);

        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_reg:
                String name = et_user.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();
                String repass = et_repass.getText().toString().trim();
                String email = et_email.getText().toString().trim();

                //判断是否为空
                if(!TextUtils.isEmpty(name) & !TextUtils.isEmpty(age)
                        & !TextUtils.isEmpty(pass)
                        & !TextUtils.isEmpty(repass)
                        & !TextUtils.isEmpty(email)
                        ){
                    //判断两次输入的密码是否一致
                    if(pass.equals(repass)){
                        //判断性别
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                                if(i==R.id.rb_boy){
                                    grander = true;
                                }else if(i == R.id.rb_girl){
                                    grander = false;
                                }
                            }
                        });

                        if(TextUtils.isEmpty(desc)){
                            desc = "这个人很懒，什么都没留下...";
                        }

                        //注册
                        MyUser user = new MyUser();
                        user.setUsername(name);
                        user.setPassword(pass);
                        user.setAge(Integer.parseInt(age));
                        user.setDesc(desc);
                        user.setSex(grander);
                        user.setEmail(email);
                        user.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if(e==null){
                                    Toast.makeText(RegisteredActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(RegisteredActivity.this,"注册失败："+e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
