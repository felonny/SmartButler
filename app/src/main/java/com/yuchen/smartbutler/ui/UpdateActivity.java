package com.yuchen.smartbutler.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.http.VolleyError;
import com.kymjs.rxvolley.toolbox.FileUtils;
import com.yuchen.smartbutler.R;
import com.yuchen.smartbutler.utils.L;

import java.io.File;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.ui
 * 文件名: UpdateActivity
 * Created by tangyuchen on 18/5/6.
 * 描述: TODO
 */

public class UpdateActivity extends BaseActivity{
    //正在下载
    public static final int HANDLER_LODING = 10001;
    //下载完成
    public static final int HANDLER_OK = 10002;
    //下载失败
    public static final int HANDLER_ON = 10003;
    private TextView tv_size;
    private NumberProgressBar number_progress;
    private String url;
    private String path;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case HANDLER_LODING:
                    //实时更新进度
                    Bundle bundle = msg.getData();
                    long transferredBytes = bundle.getLong("transferredBytes");
                    long totalSize = bundle.getLong("totalSize");
                    tv_size.setText(transferredBytes+"/"+totalSize);
                    number_progress.setProgress((int)(((float)transferredBytes/(float)totalSize)*100));
                    break;
                case HANDLER_OK:
                    tv_size.setText("下载成功");
                    //启动应用安装
                    startInsertApk();
                    break;
                case HANDLER_ON:
                    tv_size.setText("下载失败");
                    break;
            }
        }
    };

    //启动安装
    private void startInsertApk() {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setDataAndType(Uri.fromFile(new File(path)),"application/vnd.android.package-archive");
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        initView();
    }

    private void initView() {
        tv_size = (TextView) findViewById(R.id.tv_size);

        number_progress = (NumberProgressBar) findViewById(R.id.number_progress_bar);
        number_progress.setMax(100);

        path = FileUtils.getSDCardPath()+"/"+System.currentTimeMillis()+".apk";

        url = getIntent().getStringExtra("url");

        if(!TextUtils.isEmpty(url)){
            RxVolley.download(path, url, new ProgressListener() {
                @Override
                public void onProgress(long transferredBytes, long totalSize) {

                    L.i("transferredBytes:" + transferredBytes + "totalSize:" + totalSize);
                    Message msg = new Message();
                    msg.what = HANDLER_LODING;
                    Bundle bundle = new Bundle();
                    bundle.putLong("transferredBytes",transferredBytes);
                    bundle.putLong("totalSize",totalSize);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            }, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    L.e("成功");
                    handler.sendEmptyMessage(HANDLER_OK);
                }

                @Override
                public void onFailure(VolleyError error) {
                    L.e("失败");
                    handler.sendEmptyMessage(HANDLER_ON);
                }
            });
        }


    }
}
