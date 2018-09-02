package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Entity
@Keep
public class PropertiesInfo implements Parcelable {
    private String gid;
    private String name;
    private String describe;
    private String total;
    private String remaining;
    private String pcid;


    public PropertiesInfo() {
    }

    public PropertiesInfo(String gid, String name, String describe, String total, String remaining, String pcid) {
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
        return "PropertiesInfo{" +
                "gid='" + gid + '\'' +
                ", name='" + name + '\'' +
                ", describe='" + describe + '\'' +
                ", total='" + total + '\'' +
                ", remaining='" + remaining + '\'' +
                ", pcid='" + pcid + '\'' +
                '}';
    }

    //创建带参Parcel构造器
    protected PropertiesInfo(Parcel in) {
        //这里read字段的顺序要与write的顺序一致

        gid= in.readString();
        name = in.readString();
        describe = in.readString();
        total = in.readString();
        remaining = in.readString();
        pcid = in.readString();
    }

    //创建常量Creator，并实现该接口的两个方法
    public static final Parcelable.Creator<PropertiesInfo> CREATOR = new Creator<PropertiesInfo>() {
        @Override
        public PropertiesInfo createFromParcel(Parcel in) {
            return new PropertiesInfo(in);
        }

        @Override
        public PropertiesInfo[] newArray(int size) {
            return new PropertiesInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(gid);
        parcel.writeString(name);
        parcel.writeString(describe);
        parcel.writeString(total);
        parcel.writeString(remaining);
        parcel.writeString(pcid);
    }
    public static Creator<PropertiesInfo> getCREATOR() {
        return CREATOR;
    }

}
