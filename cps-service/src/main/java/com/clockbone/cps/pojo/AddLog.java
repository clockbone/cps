package com.clockbone.cps.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by qinjun on 2016/11/24.
 */
public class AddLog implements Serializable{
    private String addId;

    private String ip;

    public String getAddId() {
        return addId;
    }

    public void setAddId(String addId) {
        this.addId = addId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }



}
