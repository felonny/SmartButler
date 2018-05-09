package com.yuchen.smartbutler.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.utils
 * 文件名: RobotUtil
 * Created by tangyuchen on 18/5/3.
 * 描述: TODO
 */

public class RobotUtil extends Thread {

    private Context mContext;
    private String mInfo;
    private String text = "";


    public Handler mHandler;

    public RobotUtil(Context mContext,String mInfo,Handler mHandler){
        this.mContext = mContext;
        this.mInfo = mInfo;
        this.mHandler = mHandler;
    }




    @Override
    public void run() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String json = "{'reqType':0,'perception': {'inputText': {'text': '"+mInfo+"'}," +
                "'selfInfo': {'location': {'city': '北京','province': '北京','street': '信息路'}}}," +
                "'userInfo': {'apiKey': '"+StaticClass.TULING_KEY+"','userId': '257082'}}";
        JSONObject obj = null;
        try {
            obj = new JSONObject(json);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        //json为String类型的json数据
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(obj));
        //创建一个请求对象
        String format = "http://openapi.tuling123.com/openapi/api/v2";

        Request request = new Request.Builder()
                .url(format)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(string);
                        JSONArray jsonResult = jsonObject.getJSONArray("results");
                        //JSONArray arrayResult = jsonResult.getJSONArray("");
                        for(int i = 0;i<jsonResult.length();i++){
                            JSONObject json = jsonResult.getJSONObject(i);
                            JSONObject jsonValues = json.getJSONObject("values");
                            if(jsonValues.keys().hasNext()){
                                String value = jsonValues.keys().next();
                                text += jsonValues.getString(value);
                            }

                            //L.e(text);

                        }
                        Message msg = new Message();
                        msg.obj = text;
                        mHandler.sendMessage(msg);

                    } catch (JSONException e) {

                    }
                }

        });
    }
    }

