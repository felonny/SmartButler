package com.yuchen.smartbutler.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuchen.smartbutler.R;

/**
 * 项目名: SmartButler
 * 包名:  com.yuchen.smartbutler.fragment
 * 文件名: ButlerFragment
 * Created by tangyuchen on 18/4/25.
 * 描述: TODO
 */

public class ButlerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_butler,null);
        return view;
    }
}
