package com.yuchen.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.yuchen.smartbutler.R;
import com.yuchen.smartbutler.utils.L;
import com.yuchen.smartbutler.utils.StaticClass;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.ui
 * 文件名: PhoneActivity
 * Created by tangyuchen on 18/5/1.
 * 描述: 归属地查询
 */

public class PhoneActivity extends BaseActivity implements View.OnClickListener {
    //输入框
    private EditText et_number;
    //公司logo
    private ImageView iv_company;
    //查询结果
    private TextView tv_result;

    private Button btn_0,btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_del,btn_get;

    //标记为
    private boolean flag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        initView();
    }

    private void initView() {
        et_number = (EditText) findViewById(R.id.et_number);
        iv_company = (ImageView) findViewById(R.id.iv_company);

        tv_result = (TextView) findViewById(R.id.tv_result);

        btn_0 = (Button)findViewById(R.id.btn_0);
        btn_0.setOnClickListener(this);

        btn_1 = (Button)findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);

        btn_2 = (Button)findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);

        btn_3 = (Button)findViewById(R.id.btn_3);
        btn_3.setOnClickListener(this);

        btn_4 = (Button)findViewById(R.id.btn_4);
        btn_4.setOnClickListener(this);

        btn_5 = (Button)findViewById(R.id.btn_5);
        btn_5.setOnClickListener(this);

        btn_6 = (Button)findViewById(R.id.btn_6);
        btn_6.setOnClickListener(this);

        btn_7 = (Button)findViewById(R.id.btn_7);
        btn_7.setOnClickListener(this);

        btn_8 = (Button)findViewById(R.id.btn_8);
        btn_8.setOnClickListener(this);

        btn_9 = (Button)findViewById(R.id.btn_9);
        btn_9.setOnClickListener(this);

        btn_del = (Button)findViewById(R.id.btn_del);
        btn_del.setOnClickListener(this);

        btn_get = (Button)findViewById(R.id.btn_get);
        btn_get.setOnClickListener(this);

        btn_del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                et_number.setText("");
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        /**
         * 逻辑
         * 1.获取输入框内容
         * 2.判断是否为空
         * 3.网络请求
         * 4.解析json
         * 5.结果显示
         *
         * 键盘逻辑
         */

        //获取到输入框的内容
        String str = et_number.getText().toString();
        switch (view.getId()){
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
                if(flag){
                    str = "";
                    et_number.setText("");
                    flag = false;
                }
                et_number.setText(str+((Button)view).getText());
                //移动光标
                et_number.setSelection(str.length()+1);
                break;
            case R.id.btn_del:
                if(!TextUtils.isEmpty(str)&&str.length()>0){
                    //每次结尾减1
                    et_number.setText(str.substring(0,str.length()-1));
                    et_number.setSelection(str.length()-1);
                }
                break;
            case R.id.btn_get:
                if(!TextUtils.isEmpty(str)){
                    getPhone(str);
                }
                break;
        }

    }

    //获取归属地
    private void getPhone(String str) {
        String url = "http://apis.juhe.cn/mobile/get?phone="+str+"&key="+ StaticClass.PHONE_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //Toast.makeText(PhoneActivity.this,"结果："+t,Toast.LENGTH_SHORT).show();
                L.i("phone"+t);
                parsingJson(t);
            }
        });
    }

    /**
     * "province":"北京",
     "city":"",
     "areacode":"010",
     "zip":"100000",
     "company":"移动",
     "card":""
     * @param t
     */

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            String province = jsonResult.getString("province");
            String city= jsonResult.getString("city");
            String areacode = jsonResult.getString("areacode");
            String zip = jsonResult.getString("zip");
            String company = jsonResult.getString("company");
            String card = jsonResult.getString("card");
            StringBuilder result = new StringBuilder();

            result.append("归属地:"+province+city+"\n");
            result.append("区号:"+areacode+"\n");
            result.append("邮编:"+zip+"\n");
            result.append("运营商:"+company+"\n");
            result.append("类型:"+card+"\n");

            tv_result.setText(result.toString());
            //Toast.makeText(PhoneActivity.this,province+city,Toast.LENGTH_SHORT).show();

            switch (company){
                case "移动":
                    iv_company.setBackgroundResource(R.drawable.china_mobile);
                    break;
                case "联通":
                    iv_company.setBackgroundResource(R.drawable.china_unicom);
                    break;
                case "电信":
                    iv_company.setBackgroundResource(R.drawable.china_telecom);
                    break;
            }
            flag = true;

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
