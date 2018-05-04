package com.yuchen.smartbutler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.yuchen.smartbutler.R;
import com.yuchen.smartbutler.adapter.VideoAdapter;
import com.yuchen.smartbutler.entity.VideoData;
import com.yuchen.smartbutler.entity.WeChatData;
import com.yuchen.smartbutler.ui.VideoActivity;
import com.yuchen.smartbutler.ui.WebViewActivity;
import com.yuchen.smartbutler.utils.L;
import com.yuchen.smartbutler.utils.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名: SmartButler
 * 包名:  com.yuchen.smartbutler.fragment
 * 文件名: ButlerFragment
 * Created by tangyuchen on 18/4/25.
 * 描述: TODO
 */

public class GirlFragment extends Fragment implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    private ListView girl_listView;

    private List<VideoData> mList = new ArrayList<>();

    //标题
    private List<String> mListTitle = new ArrayList<>();
    //地址
    private List<String> mListUrl = new ArrayList<>();

    private String url = StaticClass.VIDEO_URL;;

    VideoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_girl,null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        girl_listView = (ListView) view.findViewById(R.id.girl_mListView);

        getData(url);
        girl_listView.setOnItemClickListener(this);
        girl_listView.setOnScrollListener(this);
    }

    private void getData(String url) {
        L.e(url);
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //super.onSuccess(t);
                parsingJson(t);
                L.i(t);
            }
        });
    }

    private void parsingJson(String t) {

        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonList = jsonObject.getJSONArray("issueList");
            url = jsonObject.getString("nextPageUrl");
            for(int j = 0;j<jsonList.length();j++){
                JSONObject onlyObject = jsonList.getJSONObject(j);
                JSONArray itemList = onlyObject.getJSONArray("itemList");
                for(int i = 0;i<itemList.length();i++){
                    JSONObject videoObject = itemList.getJSONObject(i);
                    if(videoObject.get("type").equals("video")){
                        VideoData videoData = new VideoData();
                        JSONObject data = videoObject.optJSONObject("data");
                        String title = (String) data.get("title");
                        String desc = (String) data.get("description");
                        JSONObject cover = data.getJSONObject("cover");
                        String imgUrl = (String) cover.get("feed");
                        String videoUrl = (String) data.get("playUrl");
                        L.i(title+desc+imgUrl+videoUrl);
                        videoData.setTitle(title);
                        videoData.setDesc(desc);
                        videoData.setImgUrl(imgUrl);
                        videoData.setVideoUrl(videoUrl);
                        mListTitle.add(title);
                        mListUrl.add(videoUrl);
                        mList.add(videoData);
                    }
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //实现刷新功能
        if(adapter == null){
            adapter = new VideoAdapter(getActivity(),mList);
            girl_listView.setAdapter(adapter);
        }else{
            adapter.updateView(mList);
        }


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent intent = new Intent(getActivity(), VideoActivity.class);
        //intent.putExtra("title",mListTitle.get(i));
        intent.putExtra("url",mListUrl.get(i));
        startActivity(intent);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    //当滚动到底部是加载新的数据
    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

        if ((i + i1) == i2) {
            View lastVisibleItemView = girl_listView.getChildAt(girl_listView.getChildCount() - 1);
            if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == girl_listView.getHeight()) {
                getData(url);
            }

        }

    }
}
