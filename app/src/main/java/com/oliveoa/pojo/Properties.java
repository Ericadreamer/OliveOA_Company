package com.oliveoa.pojo;

public class Properties {
    private String gid;
    private String name;
    private String describe;
    private String total;
    private String remaining;
    private String pcid;

    public Properties() {
    }

    public Properties(String gid, String name, String describe, String total, String remaining, String pcid) {
        this.gid = gid;
        this.name = name;
        this.describe = describe;
        this.total = total;
        this.remaining = remaining;
        this.pcid = pcid;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getRemaining() {
        return remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }

    public String getPcid() {
        return pcid;
    }

    public void setPcid(String pcid) {
        this.pcid = pcid;
    }

    @Override
    public String toString() {
        return "Properties{" +
                "gid='" + gid + '\'' +
                ", name='" + name + '\'' +
                ", describe='" + describe + '\'' +
                ", total='" + total + '\'' +
                ", remaining='" + remaining + '\'' +
                ", pcid='" + pcid + '\'' +
                '}';
    }


}
