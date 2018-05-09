package com.yuchen.smartbutler.entity;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.entity
 * 文件名: CharListData
 * Created by tangyuchen on 18/5/1.
 * 描述: 对话列表的实体
 */

public class CharListData {
    //区分左右
    private int type;
    //文本
    private String text;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
