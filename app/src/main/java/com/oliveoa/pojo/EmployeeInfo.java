package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class EmployeeInfo implements Parcelable {
    private String eid;
    private String dcid;
    private String pcid;
    private String id;
    private String name;
    private String sex;
    private String birth;
    private String tel;
    private String email;
    private String address;
    private int orderby;
    private String createtime;
    private String updatetime;

    public EmployeeInfo(String eid, String dcid, String pcid, String id, String name, String sex, String birth, String tel, String email, String address, int orderby, String createtime, String updatetime) {
        this.eid = eid;
        this.dcid = dcid;
        this.pcid = pcid;
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
        this.tel = tel;
        this.email = email;
        this.address = address;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public EmployeeInfo() {

    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getDcid() {
        return dcid;
    }

    public void setDcid(String dcid) {
        this.dcid = dcid;
    }

    public String getPcid() {
        return pcid;
    }

    public void setPcid(String pcid) {
        this.pcid = pcid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    @Override
    public String toString() {
        return "EmployeeInfo{" +
                "eid='" + eid + '\'' +
                ", dcid='" + dcid + '\'' +
                ", pcid='" + pcid + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birth='" + birth + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", orderby=" + orderby +
                ", createtime='" + createtime + '\'' +
                ", updatetime='" + updatetime + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(eid);
        parcel.writeString(dcid);
        parcel.writeString(pcid);
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(sex);
        parcel.writeString(birth);
        parcel.writeString(tel);
        parcel.writeString(email);
        parcel.writeString(address);
        parcel.writeInt(orderby);
        parcel.writeString(createtime);
        parcel.writeString(updatetime);
    }

    //创建带参Parcel构造器
    public EmployeeInfo(Parcel source){
        //这里read字段的顺序要与write的顺序一致
        eid=source.readString();
        dcid=source.readString();
        pcid=source.readString();
        id=source.readString();
        name=source.readString();
        sex=source.readString();
        birth=source.readString();
        tel=source.readString();
        email=source.readString();
        address=source.readString();
        orderby=source.readInt();
        createtime=source.readString();
        updatetime=source.readString();
    }

    //创建常量Creator，并实现该接口的两个方法
    public static final Parcelable.Creator<EmployeeInfo> CREATOR = new Creator<EmployeeInfo>(){
        @Override
        public EmployeeInfo createFromParcel(Parcel source) {
            return new  EmployeeInfo(source);
        }

        @Override
        public EmployeeInfo[] newArray(int size) {
            return new EmployeeInfo[size];
        }
    };
}
