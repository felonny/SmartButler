package com.yuchen.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.yuchen.smartbutler.R;
import com.yuchen.smartbutler.utils.L;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.ui
 * 文件名: WebViewActivity
 * Created by tangyuchen on 18/5/2.
 * 描述: TODO
 */

public class WebViewActivity extends BaseActivity {

    private ProgressBar mProgress;
    private WebView mWebview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initView();
    }

    private void initView() {

        mProgress = (ProgressBar) findViewById(R.id.mProgress);
        mWebview = (WebView) findViewById(R.id.mWebView);

        Intent intent  = getIntent();
        String title = intent.getStringExtra("title");
        final String url = intent.getStringExtra("url");
        L.i("url" + url);

        //设置标题
        getSupportActionBar().setTitle(title);

        //进行网页加载逻辑

        //支持js
        mWebview.getSettings().setJavaScriptEnabled(true);
        //支持缩放
        mWebview.getSettings().setSupportZoom(true);
        mWebview.getSettings().setBuiltInZoomControls(true);
        //接口回调
        mWebview.setWebChromeClient(new WebViewClient());
        //加载网页
        mWebview.loadUrl(url);

        //本地显示
        mWebview.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    public class WebViewClient extends WebChromeClient{
        //进度变化监听

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress == 100){
                mProgress.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
