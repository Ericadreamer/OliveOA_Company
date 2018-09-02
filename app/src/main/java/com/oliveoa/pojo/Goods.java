package com.oliveoa.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;


public class Goods {
    private String gName;
    private String gDescription;
    private String ggid;

    public Goods() {
    }

    public Goods(String gName, String gDescription, String ggid) {
        this.gName = gName;
        this.gDescription = gDescription;
        this.ggid = ggid;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getgDescription() {
        return gDescription;
    }

    public void setgDescription(String gDescription) {
        this.gDescription = gDescription;
    }

    public String getGgid() {
        return ggid;
    }

    public void setGgid(String ggid) {
        this.ggid = ggid;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "gName='" + gName + '\'' +
                ", gDescription='" + gDescription + '\'' +
                ", ggid='" + ggid + '\'' +
                '}';
    }
}

