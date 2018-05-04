package com.yuchen.smartbutler.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yuchen.smartbutler.R;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.ui
 * 文件名: testActivity
 * Created by tangyuchen on 18/5/4.
 * 描述: TODO
 */

public class testActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user);
    }
}
