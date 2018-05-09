package com.yuchen.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yuchen.smartbutler.R;
import com.yuchen.smartbutler.entity.MyUser;
import com.yuchen.smartbutler.utils.L;
import com.yuchen.smartbutler.utils.ShareUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.ui
 * 文件名: ForgetPasswordActivity
 * Created by tangyuchen on 18/4/28.
 * 描述: 忘记密码，修改密码
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_forget_password;
    private EditText et_email;

    private EditText et_old;
    private EditText et_new;
    private EditText et_new_pass;
    private Button btn_modify;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initView();
    }

    private void initView() {
        btn_forget_password = (Button) findViewById(R.id.bt_forget);
        btn_forget_password.setOnClickListener(this);
        et_email = (EditText) findViewById(R.id.et_email);

        et_old = (EditText) findViewById(R.id.old_password);
        et_new = (EditText) findViewById(R.id.new_password);
        et_new_pass = (EditText) findViewById(R.id.new_re_password);
        btn_modify = (Button) findViewById(R.id.bt_modify);
        btn_modify.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bt_forget:
                //1.获取输入框的邮箱
                final String email = et_email.getText().toString().trim();
                //2.判断邮箱是否为空
                if(!TextUtils.isEmpty(email)){
                    //判读是否是一个邮箱（正则）
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                Toast.makeText(ForgetPasswordActivity.this,"密码已经发送至邮箱："+email,Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(ForgetPasswordActivity.this,"邮箱发送失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(this,"输入邮箱不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_modify:
                String old = et_old.getText().toString().trim();
                String news  = et_new.getText().toString().trim();
                String new_password = et_new_pass.getText().toString().trim();
                //判断是否为空
                if(!TextUtils.isEmpty(old)&!TextUtils.isEmpty(news)&!TextUtils.isEmpty(new_password)){
                    //判断两次密码是否一致
                    if(news.equals(new_password)){
                        //重置密码
                        MyUser user = new MyUser();
                        user.setObjectId(ShareUtil.getString(getApplicationContext(),"userid",""));
                        user.setSessionToken(ShareUtil.getString(getApplicationContext(),"token",""));
                        L.i(ShareUtil.getString(getApplicationContext(),"token",""));
                        L.i(ShareUtil.getString(getApplicationContext(),"userid",""));
                        user.updateCurrentUserPassword(old, news,new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e == null){
                                    Toast.makeText(ForgetPasswordActivity.this,"重置密码成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(ForgetPasswordActivity.this,"重置密码失败"+e.toString(),Toast.LENGTH_SHORT).show();
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
