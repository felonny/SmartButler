package com.yuchen.smartbutler.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.yuchen.smartbutler.R;
import com.yuchen.smartbutler.utils.L;
import com.yuchen.smartbutler.utils.StaticClass;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.service
 * 文件名: SmsService
 * Created by tangyuchen on 18/5/4.
 * 描述: TODO
 */

public class SmsService extends Service implements View.OnClickListener {

    private SmsReceiver smsReceiver;

    private String smsPhone;
    private String smsContent;

    private WindowManager wm;

    private View view;

    private TextView tv_phone;
    private TextView tv_content;
    private Button btn_sendsms;

    private WindowManager.LayoutParams layoutparams;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        L.i("init Service");
        //动态注册
        smsReceiver = new SmsReceiver();
        IntentFilter intent = new IntentFilter();
        //添加Action
        intent.addAction(StaticClass.SMS_ACTION);
        //设置权限
        intent.setPriority(Integer.MAX_VALUE);
        //注册
        registerReceiver(smsReceiver,intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.i("stop Service");
        //注销
        unregisterReceiver(smsReceiver);
    }



    //短信广播
    public class SmsReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(StaticClass.SMS_ACTION.equals(action)){
                L.i("来短信了");
                //获取短信返回
                Object[] objs = (Object[]) intent.getExtras().get("pdus");
                for(Object obj : objs){
                    SmsMessage sms = SmsMessage.createFromPdu((byte[])obj);
                    smsPhone = sms.getOriginatingAddress();
                    smsContent = sms.getMessageBody();
                    L.i(smsPhone+smsContent);
                    showWindow();
                }
            }
        }
    }

    private void showWindow() {
        //获取系统服务
        wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //获取布局参数
        layoutparams = new WindowManager.LayoutParams();
        //定义宽高
        layoutparams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutparams.height = WindowManager.LayoutParams.MATCH_PARENT;
        //定义标记
        layoutparams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        //定义格式,透明
        layoutparams.format = PixelFormat.TRANSLUCENT;
        //定义类型
        layoutparams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //加载布局
        view = View.inflate(getApplicationContext(), R.layout.window_item,null);

        tv_phone = (TextView) view.findViewById(R.id.tv_phone);
        tv_content = (TextView) view.findViewById(R.id.tv_content);

        btn_sendsms = (Button) view.findViewById(R.id.btn_sendsms);

        tv_phone.setText("发件人："+smsPhone);
        tv_content.setText(smsContent);

        btn_sendsms.setOnClickListener(this);

        //添加view到窗口
        wm.addView(view,layoutparams);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_sendsms:
                sendSms();
                //完成跳转以后消失窗口
                if(view.getParent()!=null){
                    wm.removeView(view);
                }
                break;
        }

    }

    //回复短信
    private void sendSms() {
        Uri uri =  Uri.parse("smsto:"+smsPhone);
        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
        //设置启动模式
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sms_body","");
        startActivity(intent);


    }
}
