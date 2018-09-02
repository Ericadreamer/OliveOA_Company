package com.oliveoa.pojo;

public class Item {

    private int iId;
    private String iName;
    private String iEid;

    public Item() {
    }

    public Item(int iId, String iName, String iEid) {
        this.iId = iId;
        this.iName = iName;
        this.iEid = iEid;
    }

    public int getiId() {
        return iId;
    }

    public void setiId(int iId) {
        this.iId = iId;
    }

    public String getiName() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }

    public String getiEid() {
        return iEid;
    }

    public void setiEid(String iEid) {
        this.iEid = iEid;
    }

    @Override
    public String toString() {
        return "Item{" +
                "iId=" + iId +
                ", iName='" + iName + '\'' +
                ", iEid='" + iEid + '\'' +
                '}';
    }
}
