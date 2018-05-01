package com.yuchen.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.yuchen.smartbutler.R;
import com.yuchen.smartbutler.adapter.CourierAdapter;
import com.yuchen.smartbutler.entity.CourierData;
import com.yuchen.smartbutler.utils.L;
import com.yuchen.smartbutler.utils.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.ui
 * 文件名: CourierActivity
 * Created by tangyuchen on 18/5/1.
 * 描述: TODO
 */

public class CourierActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_number;
    private Button btn_search;
    private ListView mListView;

    private List<CourierData> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        initView();
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_number = (EditText) findViewById(R.id.et_number);
        btn_search = (Button) findViewById(R.id.btn_search);
        mListView = (ListView) findViewById(R.id.mListView);

        btn_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_search:
                /**
                 * 1.获取输入框的内容
                 * 2.判断是否为空
                 * 3.拿到数据请求json
                 * 4.解析json
                 * 5.ListView适配器
                 * 6.实体类（item）
                 * 7.设置数据显示效果
                 */


                //1.获取输入框的内容
                String name = et_name.getText().toString().trim();
                String number = et_number.getText().toString().trim();

                //拼接Url
                //String url = "http://v.juhe.cn/exp/index?key="+ StaticClass.COURIER_KEY+"&com="+name+"&no="+number;
                String url = "http://v.juhe.cn/exp/index?%20com="+name+"&no="+number+"&dtype=son&key="+StaticClass.COURIER_KEY;
                //2.判断是否为空
                if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(number)){
                    //3.拿到数据请求json
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            //Toast.makeText(CourierActivity.this,t,Toast.LENGTH_SHORT).show();
                            L.i("Json"+t);
                            //4.解析json
                            parsingJson(t);
                        }
                    });

                }else{
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }

                //5.ListView适配器
                //6.实体类（item）
                //7.设置数据显示效果



                break;
        }

    }

    //解析数据
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject json = (JSONObject) jsonArray.get(i);
                CourierData data = new CourierData();
                data.setRemark(json.getString("remark"));
                data.setDatatime(json.getString("datetime"));
                data.setZone(json.getString("zone"));
                mList.add(data);
            }
            Collections.reverse(mList);
            CourierAdapter adapter = new CourierAdapter(this,mList);
            mListView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
