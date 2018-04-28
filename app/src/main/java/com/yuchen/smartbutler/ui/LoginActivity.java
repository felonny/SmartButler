package com.yuchen.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yuchen.smartbutler.MainActivity;
import com.yuchen.smartbutler.R;
import com.yuchen.smartbutler.entity.MyUser;
import com.yuchen.smartbutler.utils.ShareUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.ui
 * 文件名: LoginActivity
 * Created by tangyuchen on 18/4/27.
 * 描述: 登录界面
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_registered;
    private Button btn_login;
    private EditText et_name;
    private EditText et_password;
    private CheckBox keep_password;
    private TextView tv_forget;

    private CustomDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {

        et_name = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_pass);
        keep_password = (CheckBox) findViewById(R.id.keep_password);
        tv_forget = (TextView) findViewById(R.id.tv_forget);

        btn_registered = (Button) findViewById(R.id.btn_registered);
        btn_login = (Button) findViewById(R.id.btn_signin);
        btn_registered.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_forget.setOnClickListener(this);

        dialog = new CustomDialog(this,150,150,R.layout.dialog_loading,R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style) ;
        //屏幕外点击无效
        dialog.setCancelable(false);

        //设置选中状态
        boolean isCheck = ShareUtil.getBoolean(this,"keeppass",false);
        keep_password.setChecked(isCheck);
        if(isCheck){
            et_name.setText(ShareUtil.getString(this,"name",""));
            et_password.setText(ShareUtil.getString(this,"password",""));
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_registered:
                startActivity(new Intent(this,RegisteredActivity.class));
                break;
            case R.id.tv_forget:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            case R.id.btn_signin:
                //获取输入框的值
                String name = et_name.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                if(!TextUtils.isEmpty(name)& !TextUtils.isEmpty(password)){
                    //dialog弹起来
                    dialog.show();
                    //登录
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            //dialog消失
                            dialog.dismiss();
                            //判断结果
                            if(e == null){
                                //判断邮箱是否验证
                                if(user.getEmailVerified()){
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    ShareUtil.putString(getApplicationContext(),"token",user.getSessionToken());
                                    ShareUtil.putString(getApplicationContext(),"userid",user.getObjectId());
                                    finish();
                                }else{
                                    Toast.makeText(LoginActivity.this,"请前往邮箱验证",Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(LoginActivity.this,"登录失败:"+e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //保存状态
        ShareUtil.putBoolean(this,"keeppass",keep_password.isChecked());

        //是否记住密码
        if(keep_password.isChecked()){
            //记住用户名和密码
            ShareUtil.putString(this,"name",et_name.getText().toString().trim());
            ShareUtil.putString(this,"password",et_password.getText().toString().trim());
        }else{
            ShareUtil.deleShare(this,"name");
            ShareUtil.deleShare(this,"password");
        }
    }
}
