package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

@Entity
@Keep
public class DutyInfo implements Parcelable {
    private String pcid;
    private String ppid;
    private String name;
    private String dcid;
    private int limit;
    private int orderby;
    private String createtime;
    private String updatetime;

    public DutyInfo(String pcid, String ppid, String name, String dcid, int limit, int orderby, String createtime, String updatetime) {

        this.pcid = pcid;
        this.ppid = ppid;
        this.name = name;
        this.dcid = dcid;
        this.limit = limit;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    //创建带参Parcel构造器
    protected DutyInfo(Parcel in) {
        //这里read字段的顺序要与write的顺序一致

        pcid = in.readString();
        ppid = in.readString();
        name = in.readString();
        dcid = in.readString();
        limit = in.readInt();
        orderby = in.readInt();
        createtime = in.readString();
        updatetime = in.readString();
    }

    //创建常量Creator，并实现该接口的两个方法
    public static final Creator<DutyInfo> CREATOR = new Creator<DutyInfo>() {
        @Override
        public DutyInfo createFromParcel(Parcel in) {
            return new DutyInfo(in);
        }

        @Override
        public DutyInfo[] newArray(int size) {
            return new DutyInfo[size];
        }
    };

    public DutyInfo() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(pcid);
        parcel.writeString(ppid);
        parcel.writeString(name);
        parcel.writeString(dcid);
        parcel.writeInt(limit);
        parcel.writeInt(orderby);
        parcel.writeString(createtime);
        parcel.writeString(updatetime);
    }



    public String getPcid() {
        return pcid;
    }

    public void setPcid(String pcid) {
        this.pcid = pcid;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDcid() {
        return dcid;
    }

    public void setDcid(String dcid) {
        this.dcid = dcid;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOrderby() {
        return orderby;
    }

    public void setOrderby(int orderby) {
        this.orderby = orderby;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public static Creator<DutyInfo> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return "DutyInfo{" +
                ", pcid='" + pcid + '\'' +
                ", ppid='" + ppid + '\'' +
                ", name='" + name + '\'' +
                ", dcid='" + dcid + '\'' +
                ", limit=" + limit +
                ", orderby=" + orderby +
                ", createtime='" + createtime + '\'' +
                ", updatetime='" + updatetime + '\'' +
                '}';
    }
}
