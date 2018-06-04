package com.oliveoa.pojo;

public class Goods {
    private String gName;
    private String gDescription;
    //private String gDecord;

    public Goods(String gName, String gDescription) {
        this.gName = gName;
        this.gDescription = gDescription;
        //this.gDecord = gDecord;
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

    /*public String getgDecord() {
        return gDecord;
    }

    public void setgDecord(String gDecord) {
        this.gDecord = gDecord;
    }*/
}
