package com.oliveoa.pojo;

public class Goods {
    private String gName;
    private String gDescription;
    private String gRecord;


    public String getgRecord() {
        return gRecord;
    }

    public void setgRecord(String gRecord) {
        this.gRecord = gRecord;
    }

    public Goods(String gName, String gDescription,String gRecord) {
        this.gName = gName;
        this.gDescription = gDescription;
        this.gRecord = gRecord;
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

}
