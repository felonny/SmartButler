package com.yuchen.smartbutler.entity;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.entity
 * 文件名: CourierData
 * Created by tangyuchen on 18/5/1.
 * 描述: TODO
 */

public class CourierData {

    //时间
    private String datatime;
    //状态
    private String remark;
    //城市
    private String zone;

    public String getDatatime() {
        return datatime;
    }

    public String getRemark() {
        return remark;
    }

    public String getZone() {
        return zone;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return "CourierData{"+
                "datetime='"+datatime+'\''+
                ",remark='"+remark+'\''+
                ",zone='"+zone+'\''+
                '}';
    }
}
