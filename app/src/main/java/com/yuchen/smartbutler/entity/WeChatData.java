package com.yuchen.smartbutler.entity;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.entity
 * 文件名: WeChatData
 * Created by tangyuchen on 18/5/2.
 * 描述: 微信精选数据类
 */

public class WeChatData {

    //标题
    private String title;
    //来源
    private String source;
    //图片Url
    private String ImgUrl;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }


}
