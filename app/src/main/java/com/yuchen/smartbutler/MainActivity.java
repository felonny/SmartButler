package com.yuchen.smartbutler;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tencent.bugly.crashreport.CrashReport;
import com.yuchen.smartbutler.fragment.ButlerFragment;
import com.yuchen.smartbutler.fragment.GirlFragment;
import com.yuchen.smartbutler.fragment.UserFragment;
import com.yuchen.smartbutler.fragment.WechatFragment;
import com.yuchen.smartbutler.ui.SettingActivity;
import com.yuchen.smartbutler.utils.L;
import com.yuchen.smartbutler.utils.ShareUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //TabLayout
    private TabLayout mTabLayout;
    //ViewPager
    private ViewPager mViewPager;
    //Title
    private List<String> mTitle;
    //Fragment
    private List<Fragment> mFragment;
    //悬浮窗
    private FloatingActionButton mFloatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //去掉阴影
        getSupportActionBar().setElevation(0);

        initData();
        initView();


    }

    //初始化数据
    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add(this.getResources().getString(R.string.title_1));
        mTitle.add(this.getResources().getString(R.string.title_2));
        mTitle.add(this.getResources().getString(R.string.title_3));
        mTitle.add(this.getResources().getString(R.string.title_4));

        mFragment = new ArrayList<>();
        mFragment.add(new ButlerFragment());
        mFragment.add(new WechatFragment());
        mFragment.add(new GirlFragment());
        mFragment.add(new UserFragment());
    }

    //初始化View
    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_setting);

        mFloatingActionButton.setOnClickListener(this);

        //默认情况
        mFloatingActionButton.setVisibility(View.GONE);

        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());

        //Viewpager的滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("TAG","position:"+position);
                if(position == 0){
                    mFloatingActionButton.setVisibility(View.GONE);
                }else{
                    mFloatingActionButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            //返回选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            //返回fragment的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }

            //设置标题

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });

        //绑定
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }
}
