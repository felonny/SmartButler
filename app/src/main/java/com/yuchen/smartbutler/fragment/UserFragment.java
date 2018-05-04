package com.yuchen.smartbutler.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yuchen.smartbutler.R;
import com.yuchen.smartbutler.entity.MyUser;
import com.yuchen.smartbutler.ui.CourierActivity;
import com.yuchen.smartbutler.ui.CustomDialog;
import com.yuchen.smartbutler.ui.LoginActivity;
import com.yuchen.smartbutler.ui.PhoneActivity;
import com.yuchen.smartbutler.utils.L;
import com.yuchen.smartbutler.utils.ShareUtil;
import com.yuchen.smartbutler.utils.StaticClass;
import com.yuchen.smartbutler.utils.UtilTools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

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
    private TextView tv_courier;
    //归属地查询
    private TextView tv_phone;
    private Button btn_update_ok;
    private Button btn_update_quit;

    private EditText et_username;
    private EditText et_usersex;
    private EditText et_userage;
    private EditText et_userdesc;

    private CircleImageView profile_image;
    private CustomDialog dialog;

    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancle;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int IMAGE_REQUEST_CODE = 101;
    private static final int RESULT_REQUEST_CODE = 102;

    private File tempFile;




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

        btn_update_quit = (Button) view.findViewById(R.id.btn_update_quit);
        btn_update_quit.setOnClickListener(this);

        profile_image = (CircleImageView) view.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);

        tv_courier = (TextView) view.findViewById(R.id.tv_courier);
        tv_courier.setOnClickListener(this);

        tv_phone = (TextView) view.findViewById(R.id.tv_phone);
        tv_phone.setOnClickListener(this);

        UtilTools.getImageToShare(profile_image,getActivity());


        //初始化dialog
        dialog = new CustomDialog(getActivity(), WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT,
                R.layout.dialog_photo,R.style.pop_anim_style,Gravity.BOTTOM) ;

        btn_camera = (Button) dialog.findViewById(R.id.btn_camera);
        btn_picture = (Button) dialog.findViewById(R.id.btn_picture);
        btn_cancle = (Button) dialog.findViewById(R.id.btn_cancle);
        
        btn_camera.setOnClickListener(this);
        btn_picture.setOnClickListener(this);
        btn_cancle.setOnClickListener(this);
        //屏幕外点击无效
        dialog.setCancelable(false);

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
                btn_update_quit.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_update_quit:
                btn_update_ok.setVisibility(View.GONE);
                btn_update_quit.setVisibility(View.GONE);
                setEnable(false);
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
                                btn_update_quit.setVisibility(View.GONE);
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
            case R.id.profile_image:
                dialog.show();
                break;
            case R.id.btn_cancle:
                dialog.dismiss();
                break;
            case R.id.btn_camera:
                toCamera();
                break;
            case R.id.btn_picture:
                toPicture();
                break;
            case R.id.tv_courier:
                startActivity(new Intent(getActivity(),CourierActivity.class));
                break;
            case R.id.tv_phone:
                startActivity(new Intent(getActivity(),PhoneActivity.class));
                break;
        }
    }


    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_REQUEST_CODE);
        dialog.dismiss();
    }


    //跳转相机
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用，可用就进行存储
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), StaticClass.PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent,CAMERA_REQUEST_CODE);
        dialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != getActivity().RESULT_CANCELED ){
            switch(requestCode){
                //相册数据
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                //相机数据
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(),StaticClass.PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    //有可能点击舍弃
                    if(data != null){
                        //拿到裁剪后的图片
                        setImageToView(data);
                        //既然已经设置了图片，原先的图片应该删除
                        if(tempFile != null){
                            tempFile.delete();
                        }
                    }
                    break;
            }
        }
    }

    private void startPhotoZoom(Uri uri){
        if(uri == null){
            L.e("Uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");

        //设置裁剪
        intent.putExtra("crop","true");
        //裁剪宽高
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //裁剪图片的质量
        intent.putExtra("outputX",320);
        intent.putExtra("outputY",320);
        //发送数据
        intent.putExtra("return-data",true);
        startActivityForResult(intent,RESULT_REQUEST_CODE);

    }

    private void setImageToView(Intent data){
        Bundle bundle = data.getExtras();
        if(bundle!= null){
            Bitmap bitmap =  bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);
        }
    }

    //获取焦点
    private void setEnable(boolean is){
        et_username.setEnabled(is);
        et_usersex.setEnabled(is);
        et_userage.setEnabled(is);
        et_userdesc.setEnabled(is);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UtilTools.putImageToShare(profile_image,getActivity());
    }
}
