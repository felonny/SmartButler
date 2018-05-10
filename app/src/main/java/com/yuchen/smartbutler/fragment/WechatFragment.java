package com.yuchen.smartbutler.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.okhttp.OkHttpStack;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.http.RequestQueue;
import com.squareup.okhttp.OkHttpClient;
import com.yuchen.smartbutler.R;
import com.yuchen.smartbutler.adapter.WeChatAdapter;
import com.yuchen.smartbutler.entity.WeChatData;
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
 * 文件名: WechatFragment
 * Created by tangyuchen on 18/4/25.
 * 描述: TODO
 */

public class WechatFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView weChatListView;
    private List<WeChatData> mList = new ArrayList<>();

    //标题
    private List<String> mListTitle = new ArrayList<>();
    //地址
    private List<String> mListUrl = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat,null);
        findView(view);
        return view;
    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch(permsRequestCode){
//
//            case 200:
//                boolean networkAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
//                if(networkAccepted){
//                    //授权成功之后，调用系统相机进行拍照操作等
//
//
//                }else{
//                    //用户授权拒绝之后，友情提示一下就可以了
//                    Toast.makeText(getActivity(),"没有获取到您的权限",Toast.LENGTH_SHORT).show();
//                }
//
//                break;
//
//        }
//    }

    private void findView(View view) {
        weChatListView = (ListView) view.findViewById(R.id.weChat_mListView);
        RxVolley.setRequestQueue(RequestQueue.newRequestQueue(RxVolley.CACHE_FOLDER,
                new OkHttpStack(new OkHttpClient())));

        //解析接口
        String url = "http://v.juhe.cn/weixin/query?pno=&ps=&dtype=&key="+ StaticClass.WECHAT_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //Toast.makeText(getActivity(),t,Toast.LENGTH_SHORT).show();
                L.i(t);
                parsingJson(t);
            }
        });

        weChatListView.setOnItemClickListener(this);
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonlist = jsonResult.getJSONArray("list");

            for(int i = 0;i<jsonlist.length();i++){
                JSONObject json = (JSONObject) jsonlist.get(i);
                WeChatData data = new WeChatData();
                String title = json.getString("title");
                data.setTitle(json.getString("title"));
                String url = json.getString("url");
                data.setSource(json.getString("source"));
                data.setImgUrl(json.getString("firstImg"));
                mList.add(data);

                mListTitle.add(title);

                mListUrl.add(url);
            }
            WeChatAdapter adapter = new WeChatAdapter(getActivity(),mList);
            weChatListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        L.i("position"+i);
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        //intent 两种方法传值
//        Bundle bundle = new Bundle();
//        bundle.putString("key","value");
//        intent.putExtras(bundle);
        intent.putExtra("title",mListTitle.get(i));
        intent.putExtra("url",mListUrl.get(i));
        startActivity(intent);
    }
}
