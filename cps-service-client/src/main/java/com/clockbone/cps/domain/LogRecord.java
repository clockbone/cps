package com.clockbone.cps.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by qinjun on 2016/11/2.
 */
public class LogRecord implements Serializable {



    private String addId;

    private String userAccount;

    public String getAddId() {
        return addId;
    }

    public void setAddId(String addId) {
        this.addId = addId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    //记录类型，登录、充值
    private String recordType;

    private String ip;

    private Date createTime;


}
