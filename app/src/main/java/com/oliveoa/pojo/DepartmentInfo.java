package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

@Entity
@Keep
/* JSON 数据抽象为实体类 */
public class DepartmentInfo implements Parcelable {
    @Id(autoincrement = true)
    private  Long _id ;
    private String dcid;
    private String dpid;
    private String id;
    private String name;
    private String telephone;
    private String fax;
    private int orderby;
    private String createtime;
    private String updatetime;

    public DepartmentInfo(Long _id,String dcid, String dpid, String id, String name, String telephone, String fax, int orderby, String createtime, String updatetime) {
        this._id = _id;
        this.dcid = dcid;
        this.dpid = dpid;
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.fax = fax;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    //创建带参Parcel构造器
    protected DepartmentInfo(Parcel in) {
        //这里read字段的顺序要与write的顺序一致
        _id = in.readLong();
        dcid = in.readString();
        dpid = in.readString();
        id = in.readString();
        name = in.readString();
        telephone = in.readString();
        fax = in.readString();
        orderby = in.readInt();
        createtime = in.readString();
        updatetime = in.readString();
    }

    //创建常量Creator，并实现该接口的两个方法
    public static final Creator<DepartmentInfo> CREATOR = new Creator<DepartmentInfo>() {
        @Override
        public DepartmentInfo createFromParcel(Parcel in) {
            return new DepartmentInfo(in);
        }

        @Override
        public DepartmentInfo[] newArray(int size) {
            return new DepartmentInfo[size];
        }
    };

    public DepartmentInfo() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(_id);
        parcel.writeString(dcid);
        parcel.writeString(dpid);
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(telephone);
        parcel.writeString(fax);
        parcel.writeInt(orderby);
        parcel.writeString(createtime);
        parcel.writeString(updatetime);
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getDcid() {
        return dcid;
    }

    public void setDcid(String dcid) {
        this.dcid = dcid;
    }

    public String getDpid() {
        return dpid;
    }

    public void setDpid(String dpid) {
        this.dpid = dpid;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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

    public static Creator<DepartmentInfo> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return "DepartmentInfo{" +
                "_id=" + _id +
                ", dcid='" + dcid + '\'' +
                ", dpid='" + dpid + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", fax='" + fax + '\'' +
                ", orderby=" + orderby +
                ", createtime='" + createtime + '\'' +
                ", updatetime='" + updatetime + '\'' +
                '}';
    }
}
